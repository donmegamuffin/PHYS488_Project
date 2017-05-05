/*
*	PHYS488 Project - Particle moving through detector and detection
*	Author: Zachary Humphreys
*	Date:	15/04/2017
*	NOTE: To use the code, please reconfigure main as necessary. 
*	Also, in this particular version, many of the fprintf functions have been commented out
*	for the purpose of specialising certain spreadsheets. For full functionality, please uncomment them
*/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "Source.h"

/*Global variables*/
#define MAGFIELD 1.35
#define RINGRAD	7
#define NPLANES 9
#define PLANESPACING 0.09
#define MUONLIFETIME 2.19698e-6
#define NMUONS 1

/*Data related*/
#define BUFFER	31
#define LOOPLIM 100

/*Define data structures*/
typedef struct vector_st { double x;  double y; double z; } VECTOR;	
typedef struct particle_st { VECTOR xyz; VECTOR P; int q; float mass; double lifetime; } PTCL;	
typedef struct material_st { double atomicNum; double atomicMass; double density; } MAT;

/*Function prototypes*/
/*==============================CIRCLE FIT CHAOS ====================================*/
#define LEN(array) ((int) (sizeof(array))/(sizeof(array[0][0])))
/*Base tier*/
double a_ij(double i, double j);	
double aSQ_ij(double i, double j);	
double aSqig_ijk(double i, double j, double k);	
/*Getting silly tier*/
double w_ijk(double xi, double xj, double xk, double yi, double yj, double yk);	
double wSQ_ijk(double xi, double xj, double xk, double yi, double yj, double yk);	
/*Dear-god-please-have-mercy-wat tier*/
double a_A(double *(data));	
double b_A(double*(data));	
double r_A(double *(data), double a_A, double b_A);	
void cf_aoi_console(double *data);
double cf_yto0(double *data);
/*==============================CIRCLE FIT CHAOS ====================================*/

/*==============================MCS/Energy Loss =====================================*/
/*-------------------MCS----------------------*/
double mcs_X_0(MAT mat);
double mcs_Tht0(MAT mat, PTCL ptcl, double mat_Thickness);
double mcs_ThtT(MAT mat, PTCL ptcl, double mat_Thickness);
/*-------------------Energy Loss--------------*/
double el_getEnergyLoss(MAT mat, PTCL ptcl);
/*==============================MCS/Energy Loss =====================================*/


/*Vector Maths*/
VECTOR vrot2D(VECTOR dir, double rads);	
VECTOR vunit(VECTOR in);	
VECTOR vscale(VECTOR in, double k);	
VECTOR vadd(VECTOR in1, VECTOR in2);	
VECTOR vsubtract(VECTOR in1, VECTOR in2); 	
double vlength(VECTOR in);	
double vdot(VECTOR in1, VECTOR in2);	

/*Standard maths*/
int nCr(int n, int r);	
double nearGauss(double mean, double sigma);

/*Program related*/
VECTOR rotOrigin(PTCL in);	
void decay(PTCL in_ptcl, double *data, MAT mat);	
void drawRing(void);	

/*Debug stuff*/
void rawStatOut(PTCL muon);	

/*File related*/
FILE *fp;
char name[] = { "filltest.csv" };


/*>>>>>>>>>>>>>*/
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
int main(void)
{
	double* fillable;
	fp = fopen(name, "w");
	PTCL muon = { {7,0,0},{0,500,0},1,0.511,MUONLIFETIME };
	MAT Silicon = { 14,28.084,2.329 };
	MAT lead = { 82,207.2,11.34 };
	MAT iron = { 26,55.845,7.874 };
	MAT mylar = { 4.5454, 8.734, 1.38 };
	MAT mylar2 = { 100, 192, 1.38 };
	MAT aluminium = { 13,26.981,2.70 };
	
	
	/*Seed rand*/
	srand(time(NULL));
	
	/*Test the MCS*/
	/*printf("Iron X0 = %.6f m\n", mcs_X_0(iron));
	printf("For a material thickness of 1cm:\n");
	printf("thetaT = %.5f for p = %.3f MeV.\n", mcs_ThtT(iron, muon, 0.01), vlength(muon.P));*/
	/*fprintf(fp,"Iron X0 = %.6f m\n", mcs_X_0(iron));
	fprintf(fp,"For a material thickness of 1m:\n");
	fprintf(fp,"thetaT = %.5f for p = %.3f MeV.\n", mcs_ThtT(iron, muon, 1), vlength(muon.P));*/
	
	/*Generate low momentum data through material*/
	/*fprintf(fp, "-------MYLAR---------\n------\n------\n------");*/
	fprintf(fp, "P,0_X,a,b,ca,cb,CFX\n");
	for (float i=0; i < 2000; i+=1)
	{
		srand(time(NULL)*i);
		muon.P.x = 0; 
		muon.P.y = i*1.595;
		fillable = malloc(NPLANES * 2 * sizeof(double));
		fprintf(fp, "%.10G,%.10G,",vlength(muon.P),muon.xyz.x);
		VECTOR muon_ro = rotOrigin(muon);
		fprintf(fp,"%.10G,%.10G,", muon_ro.x, muon_ro.y);
		decay(muon, fillable, mylar);
		cf_aoi_console(fillable);
		printf("CF: Particle originates from (%.3G;0)\n\n", cf_yto0(fillable));
		fprintf(fp, "%.10G\n", cf_yto0(fillable));
		free(fillable);
	}
	
	
	/*Close program and file*/
	fclose(fp);
	return EXIT_SUCCESS;
}
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
/*>>>>>>>>>>>>>*/

/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡Functions≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
/*==================================MATHS============================================*/
/*---------------------------------VECTOR--------------------------------------------*/
//FUNCTIONING
VECTOR vrot2D(VECTOR fwd, double rads)
{
	VECTOR outvec = { 0,0,0 };
	double rot[2][2] = { { cos(rads),sin(rads) },{ -sin(rads),cos(rads) } };
	outvec.x = ((rot[0][0] * fwd.x) + (rot[0][1] * fwd.y));
	outvec.y = ((rot[1][0] * fwd.x) + (rot[1][1] * fwd.y));
	outvec.z = fwd.z;
	return outvec;
}	
//UNKNOWN
VECTOR vunit(VECTOR in)
{
	double normFac = sqrt(pow(in.x, 2) + pow(in.y, 2) + pow(in.z, 2));
	VECTOR out = { in.x / normFac, in.y / normFac, in.z / normFac };
	return out;
}
//FUNCTIONING
VECTOR vscale(VECTOR in, double k)
{
	VECTOR out = { in.x*k,in.y*k,in.z*k };
	return out;
}
//FUNCTIONING
VECTOR vadd(VECTOR in1, VECTOR in2)
{
	VECTOR out = { in1.x + in2.x, in1.y + in2.y ,in1.z + in2.z };
	return out;
}
//FUNCTIONING
VECTOR vsubtract(VECTOR in1, VECTOR in2)
{
	VECTOR out = { (in1.x - in2.x),(in1.y - in2.y),(in1.z - in2.z) };
	return out;
}
//FUNCTIONING
double vlength(VECTOR in)
{
	return (sqrt(pow(in.x, 2) + pow(in.y, 2) + pow(in.z, 2)));
}
//FUNCTIONING
double vdot(VECTOR in1, VECTOR in2)
{
	return ((in1.x*in2.x)+ (in1.y*in2.y) + (in1.z*in2.z));
}
/*---------------------------------StandardMaths-------------------------------------*/
int nCr(int n, int r)
{
	int dif = n - r;
	int out = 1;
	int denom = 1;
	while (n>dif)
	{
		out = out*n;
		n--;
	}
	while (r>0)
	{
		denom = denom*r;
		r--;
	}
	return out / denom;
}

double nearGauss(double mean, double sigma)
{
	double sum = 0.;
	for (int n = 0; n < 15; n++) {
		sum = sum + ((double)rand() / (double)RAND_MAX);
	}
	return (mean + sigma*(sum - 7.5));
}

/*===================================SIMULATION======================================*/
//FUNCTIONING
VECTOR rotOrigin(PTCL in)
{
	VECTOR origin = vrot2D(in.P, -1.571);	//90deg clockwise rot TODO cleanup constants
	origin = vscale(origin, (1 / (300*MAGFIELD)));	//Scale correctly
	origin = vadd(origin, in.xyz);
	return origin;
}

//FUNCTIONING
void decay(PTCL in,double *data, MAT mat)
{	/*Get the origin to rotate the particle around*/
	VECTOR origin = rotOrigin(in);
	double R = vlength(vsubtract(origin,in.xyz));
	
	/*Iterator*/
	int n = 0;

	/*Array to fill and associated and pointers*/
	double *ax,*ay;
	ax = data + (n * 2);
	ay = ax + 1;

	/*Print to file and console*/
	if(in.xyz.x>origin.x)
	{
		while (in.xyz.y <= (origin.y + R) && n < NPLANES)
		{
			in.xyz.x = sqrt(pow(R, 2) - pow(in.xyz.y - origin.y, 2)) + origin.x;
			printf("%.3G,%.3G\n", in.xyz.x, in.xyz.y);
			//fprintf(fp, "%.10G,%.10G\n", in.xyz.x, in.xyz.y);
			*ax = in.xyz.x;
			*ay = in.xyz.y;
			in.xyz.y += PLANESPACING;
			n++;
			ax = data + (n * 2);
			ay = ax + 1;
			/*MCS*/
			in.P = vrot2D(vscale(vsubtract(origin, in.xyz), (300*MAGFIELD)), 1.571);
			in.P = vrot2D(in.P, nearGauss(0, 3 * mcs_ThtT(mat, in, PLANESPACING)*3.141 / 180));
			origin = rotOrigin(in);
		}
	}
	else if (in.xyz.x<origin.x)	
	{
		while (in.xyz.y >= (origin.y - R) && n < NPLANES)
		{
			in.xyz.x = -sqrt(pow(R, 2) - pow(in.xyz.y - origin.y, 2)) + origin.x;
			printf("%.3G,%.3G\n", in.xyz.x, in.xyz.y);
			//fprintf(fp, "%.10G,%.10G\n", in.xyz.x, in.xyz.y);
			*ax = in.xyz.x;
			*ay = in.xyz.y;
			in.xyz.y -= PLANESPACING;
			n++;
			ax = data + (n * 2);
			ay = ax + 1;
			/*MCS*/
			in.P = vrot2D(vscale(vsubtract(origin, in.xyz), (300 * MAGFIELD)), 1.571);
			in.P = vrot2D(in.P, nearGauss(0,3 * mcs_ThtT(mat, in, PLANESPACING)*3.141 / 180));
			origin = rotOrigin(in);
		}
	}
}

//FUNCTIONING
void drawRing(void)
{
	VECTOR pos;
	for (double rad = 0; rad < 6.283; rad += 0.01745)
	{
		pos.x = (RINGRAD *cos(rad));	
		pos.y = (RINGRAD *sin(rad));
		fprintf(fp, "%.10G,%.10G\n", pos.x, pos.y);
	}
}

/*Debug stuff*/
void rawStatOut(PTCL muon)
{
	/*Debug stuff*/

	printf("Muon xyz:\n x,y,z\n %.3f,%.3f,%.3f\n Muon P:\n x,y,z\n %.3f,%.3f,%.3f\n", muon.xyz.x, muon.xyz.y, muon.xyz.z, muon.P.x, muon.P.y, muon.P.z);
	fprintf(fp, "Muon xyz:\n x,y,z\n %.10G,%.10G,%.10G\nMuon P:\n x,y,z\n %.10G,%.10G,%.10G\nMuon P(relative):\n x,y,z\n %.10G,%.10G,%.10G\n", muon.xyz.x, muon.xyz.y, muon.xyz.z, muon.P.x, muon.P.y, muon.P.z,
		muon.P.x + muon.xyz.x, muon.P.y + muon.xyz.y, muon.P.z + muon.xyz.z);
	VECTOR muon_ro = rotOrigin(muon);
	printf("Muon rot_ori:\n x,y,z\n%.3f,%.3f,%.3f\n", muon_ro.x, muon_ro.y, muon_ro.z);
	fprintf(fp, "Muon rot_ori:\n x,y,z\n%.10G,%.10G,%.10G\n", muon_ro.x, muon_ro.y, muon_ro.z);
	double R = vlength(vsubtract(muon_ro, muon.xyz));
	printf("Muon-ori distance:\n%.3f\n", R);
	fprintf(fp, "Muon-ori distance:\n%.10G\n", R);
	VECTOR mP_Unit = vunit(muon.P);
	printf("Muon P unit:\n%.3f,%.3f,%.3f\n", mP_Unit.x, mP_Unit.y, mP_Unit.z);
	fprintf(fp, "Muon P unit:\n%.10G,%.10G,%.10G\n", mP_Unit.x, mP_Unit.y, mP_Unit.z);
	double angle = acos(vdot(muon.P, muon_ro) / (vlength(muon.P)*vlength(muon_ro)));
	printf("p-ori angle:\n%.3f\n", angle);
	fprintf(fp, "p-ori angle:\n%.10G\n", angle);

}



/*==============================CIRCLE FIT CHAOS ====================================*/
/*																					 */
/*				B E W A R E:	D O O M		A W A I T S		T H E E					 */
/*																					 */
/*===================================================================================*/

/*---------------------------------------------METHODS------------------------------------------*/
double a_ij(double i, double j)
{
	return (i - j);
}

double aSQ_ij(double i, double j)
{
	return (pow(i, 2) - pow(j, 2));
}

double aSqig_ijk(double i, double j, double k)
{
	return (i - j)*(j - k)*(k - i);
}

double w_ijk(double xi, double xj, double xk, double yi, double yj, double yk)
{
	return (xi*(yj - yk)) + (xj*(yk - yi)) + (xk*(yi - yj));
}

double wSQ_ijk(double xi, double xj, double xk, double yi, double yj, double yk)
{
	return (pow(xi, 2)*(yj - yk)) + (pow(xj, 2)*(yk - yi)) + (pow(xk, 2)*(yi - yj));
}

double a_A(double *(data))
{
	/*Pointers to all the variables*/
	double *ix, *iy, *jx, *jy, *kx, *ky;
	
	/*Calculate length of the 2D array*/
	int dat_end = sizeof(data)+1;

	/*Initialise the out value*/
	double out = 0;
	/*Check that there's enough data*/
	if(dat_end>3)
	{ 
		/*Iterate around all of the values*/
		for (int i=0; i < (dat_end - 2); i++)
		{
			ix = (data + (i * 2));
			iy = ix + 1;
			for (int j = i + 1; j < (dat_end - 1); j++)
			{
				jx = (data + (j * 2));
				jy = jx + 1;
				for (int k = j + 1; k < dat_end; k++)
				{
					kx = (data + (k * 2));
					ky = kx + 1;
					out += ((wSQ_ijk(*ix, *jx, *kx, *iy, *jy, *ky) - aSqig_ijk(*iy, *jy, *ky)) / (w_ijk(*ix, *jx, *kx, *iy, *jy, *ky)));
				}
			}
		}
		return (out / (2 * nCr(dat_end,3)));
	}
	/*Fail and return 0 otherwise*/
	else
	{
		fprintf(stderr, "Insufficient datapoints");
		return EXIT_FAILURE;
	}
}

double b_A(double*(data))	//TODO
{
	/*Pointers to all the variables*/
	double *ix, *iy, *jx, *jy, *kx, *ky;

	/*Calculate length of the 2D array*/
	int dat_end = sizeof(data) + 1;

	/*Initialise the out value*/
	double out = 0;
	/*Check that there's enough data*/
	if (dat_end>3)
	{
		/*Iterate around all of the values*/
		for (int i = 0; i < (dat_end - 2); i++)
		{
			ix = (data + (i * 2));
			iy = ix + 1;
			for (int j = i + 1; j < (dat_end - 1); j++)
			{
				jx = (data + (j * 2));
				jy = jx + 1;
				for (int k = j + 1; k < dat_end; k++)
				{
					kx = (data + (k * 2));
					ky = kx + 1;
					out += ((wSQ_ijk(*iy, *jy, *ky, *ix, *jx, *kx) - aSqig_ijk(*ix, *jx, *kx)) / (w_ijk(*iy, *jy, *ky, *ix, *jx, *kx)));
				}
			}
		}
		return (out / (2 * nCr(dat_end, 3)));
	}
	/*Fail and return 0 otherwise*/
	else
	{
		fprintf(stderr, "Insufficient datapoints");
		return EXIT_FAILURE;
	}
}

double r_A(double *(data), double a_A, double b_A)
{
	double *ix, *iy;
	int data_end = sizeof(data) + 1;
	double out = 0;
	for (int i = 0; i < data_end; i++) 
	{
		ix = (data + (i * 2));
		iy = ix + 1;
		out += (sqrt(pow((*ix-a_A),2) + pow((*iy - b_A), 2)));
	}
	return out/data_end;
}

void cf_aoi_console(double *data)
{
	double aA = a_A(data);
	double bA = b_A(data);
	printf("Equation of circle: %.3G = sqrt((x+%.3G)+(y+%.3G))\n", r_A(data, aA, bA), aA, bA);
	//fprintf(fp,"Equation of circle: %.10G = sqrt((x+%.10G)+(y+%.10G))\n", r_A(data, aA, bA), aA, bA);
	fprintf(fp, "%.10G,%.10G,", aA, bA);
}

double cf_yto0(double *data)
{
	double a = a_A(data);
	double b = b_A(data);
	double r = r_A(data,a,b);
	return (sqrt(pow(r, 2) - pow((-b), 2)) + a);
}

/*======================================MATERIAL STUFF ==================================*/
/*--------------------------------------MCS----------------------------------------------*/
double mcs_X_0(MAT mat)
{
	return ((7.164 * mat.atomicMass) / (mat.density * mat.atomicNum* (mat.atomicNum + 1) * log(287 / sqrt(mat.atomicNum))));
}

double mcs_Tht0(MAT mat, PTCL ptcl, double mat_Thickness)
{
	//Calculates x/X_0
	double mat_ThickByRadLength = (double)(mat_Thickness / mcs_X_0(mat));
	double ptcl_beta = (vlength(ptcl.P) / sqrt((pow(vlength(ptcl.P), 2) + pow(ptcl.mass, 2))));
	double Theta0 = (13.6 / (ptcl_beta * vlength(ptcl.P))) * ptcl.q * sqrt(mat_ThickByRadLength) *(1 + (0.038*log(mat_ThickByRadLength)));
	return Theta0;
}

double mcs_ThtT(MAT mat, PTCL ptcl, double mat_Thickness)
{
	double thetaT =  sqrt(2)*mcs_Tht0(mat, ptcl, mat_Thickness);
	return thetaT;
}
/*------------------------------------------Energy Loss-------------------------------------*/
double el_getEnergyLoss(MAT mat, PTCL ptcl)  //TODO Pseudo-code it
{
	double const K = 0.307075;
	double const I = 0.0000135;
	double const electron_mass = 0.511;
	double ptcl_Beta = (vlength(ptcl.P) / sqrt((pow(vlength(ptcl.P), 2) + pow(ptcl.mass, 2))));
	double ptcl_BetaSq = pow(ptcl_Beta, 2);
	double ptcl_Gamma = 1 / sqrt(1 - ptcl_BetaSq);
	double ptcl_GammaSq = pow(ptcl_Gamma, 2);

	double W_max = (2 * electron_mass * ptcl_BetaSq * ptcl_GammaSq) / (1 + (2 * ptcl_Gamma * electron_mass / ptcl.mass) + pow((electron_mass / ptcl.mass), 2));
	double dEdX = ((K * pow(ptcl.q, 2) * mat.density) * (mat.atomicNum / mat.atomicMass) * (1 / ptcl_BetaSq)) * ((0.5 * log((2 * electron_mass * ptcl_BetaSq * ptcl_GammaSq * W_max) / pow((I*mat.atomicNum), 2))) - ptcl_BetaSq);

	return dEdX;
}
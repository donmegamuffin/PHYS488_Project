#include "stdafx.h"
#include "Particle.h"



Particle::Particle()
{
}

Particle::Particle(Transform in_Trans)
{
	particleTransform = in_Trans;
}

Particle::Particle(Transform in_Trans, double in_Energy, double in_Momentum, double in_Charge, double in_Mass, double in_Lifetime)
{
	particleTransform = in_Trans;
	if (in_Energy != NULL)
	{
		energy = in_Energy;
	}
	else
	{
		energy = pow((pow(in_Momentum, 2.) + pow(in_Mass, 2.)), (0.5));
	}
	
	if (in_Momentum != NULL)
	{
		momentum = in_Momentum;
	}
	else
	{
		momentum = pow((pow(in_Energy,2.)-pow(in_Mass,2.)), (0.5));
	}

	if (in_Momentum == NULL && in_Energy == NULL)
	{
		
	}
	
	charge = in_Charge;
	mass = in_Mass;
	restLifetime = in_Lifetime;
	gamma = energy / mass;
	relativisticLifetime = gamma*restLifetime;
	beta = pow((1 - pow(gamma,-2)), 0.5);
	actualLifetime = (-relativisticLifetime*log((double)rand()/(double)RAND_MAX)); //TODO
}

double Particle::getGamma()
{
	return gamma;
}

double Particle::getBeta()
{
	return beta;
}

double Particle::getLifetime()
{
	return actualLifetime;
}


Particle::~Particle()
{
}

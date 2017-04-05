#include "stdafx.h"
#include "DataTypes.h"
/*---------------------------VECTOR----------------------------------------------*/
/*CONSTRUCTORS*/
Vector::Vector(double in_x, double in_y, double in_z)
{
	X = in_x;
	Y = in_y;
	Z = in_z;
}
Vector::Vector(double xyz[3])
{
	X = xyz[0];
	Y = xyz[1];
	Z = xyz[2];
}
Vector::Vector()
{
	X = 0.0;
	Y = 0.0;
	Z = 0.0;
}
/*SETTERS*/
void Vector::setX(double in_x)
{
	X = in_x;
	return;
}
void Vector::setY(double in_y)
{
	Y = in_y;
	return;
}
void Vector::setZ(double in_z)
{
	Z = in_z;
	return;
}
/*GETTERS*/
double Vector::getX() { return X; }
double Vector::getY() { return Y; }
double Vector::getZ() { return Z; }
/*CONVERTERS*/
double* Vector::toDoubleArray()
{
	double OutArray[3];
	OutArray[0] = getX();
	OutArray[1] = getY();
	OutArray[2] = getZ();
	return OutArray;
}
/*---------------------------ROTATION----------------------------------------------*/
/*CONSTRUCTORS*/
Rotation::Rotation(double in_Roll, double in_Pitch, double in_Yaw)
{
	Roll = fmod(in_Roll, 360.0);
	Pitch = fmod(in_Pitch, 360.0);
	Yaw = fmod(in_Yaw, 360.0);
}
Rotation::Rotation(double xyz[3])
{
	Roll = fmod(xyz[0], 360.0);
	Pitch = fmod(xyz[1], 360.0);
	Yaw = fmod(xyz[2], 360.0);
}
Rotation::Rotation()
{
	Roll = 0.0;
	Pitch = 0.0;
	Yaw = 0.0;
}
/*SETTERS*/
void Rotation::setRoll(double in_roll)
{
	Roll = in_roll;
	return;
}
void Rotation::setPitch(double in_Pitch)
{
	Pitch = in_Pitch;
	return;
}
void Rotation::setYaw(double in_Yaw)
{
	Yaw = in_Yaw;
	return;
}
/*GETTERS*/
double Rotation::getRoll() { return Roll; }
double Rotation::getPitch() { return Pitch; }
double Rotation::getYaw() { return Yaw; }
/*CONVERTERS*/
double* Rotation::toDoubleArray()
{
	double OutArray[3];
	OutArray[0] = getRoll();
	OutArray[1] = getPitch();
	OutArray[2] = getYaw();
	return OutArray;
}
/*---------------------------TRANSFORM----------------------------------------------*/
Transform::Transform(Vector L, Rotation O, Vector S)
{
	Location = L;
	Orientation = O;
	Scale = S;
}
Transform::Transform()
{
	Location = { 0,0,0 };
	Orientation = { 0,0,0 };
	Scale = { 0,0,0 };
}
/*SETTERS*/
void Transform::setLocation(Vector in_Vector)
{
	Location = in_Vector;
	return;
}
void Transform::setOrientation(Rotation in_Rotation)
{
	Orientation = in_Rotation;
	return;
}
void Transform::setScale(Vector in_Vector)
{
	Scale = in_Vector;
	return;
}
/*GETTERS*/
Vector Transform::getLocation() { return Location; }
Rotation Transform::getOrientation() { return Orientation; }
Vector Transform::getScale() { return Scale; }
/*CONVERTERS*/

/*---------------------------FOURVECTOR----------------------------------------------*/
/*CONSTRUCTORS*/
FourVector::FourVector()
{	//Creates a vector filled with 0 values
	T, X, Y, Z = 0;
}
FourVector::FourVector(double in[4])
{
	T = in[0];
	X = in[1];
	Y = in[2];
	Z = in[3];
}
FourVector::FourVector(double in_T, double in_X, double in_Y, double in_Z)
{
	T = in_T;
	X = in_X;
	Y = in_Y;
	Z = in_Z;
}
/*SETTERS*/
void FourVector::setT(double in_T)
{
	T = in_T;
	return;
}
void FourVector::setX(double in_X)
{
	X = in_X;
	return;
}
void FourVector::setY(double in_Y)
{
	Y = in_Y;
	return;
}
void FourVector::setZ(double in_Z)
{
	Z = in_Z;
	return;
}
/*GETTERS*/
double FourVector::getT()
{
	return T;
}
double FourVector::getX()
{
	return X;
}
double FourVector::getY()
{
	return Y;
}
double FourVector::getZ()
{
	return Z;
}
/*CONVERTERS*/
Vector FourVector::toVector()
{
	Vector OutVector;
	OutVector.setX(getX());
	OutVector.setY(getY());
	OutVector.setZ(getZ());
	return OutVector;
}
double* FourVector::toDoubleArray()
{
	double OutArray[4];
	OutArray[0] = getT();
	OutArray[1] = getX();
	OutArray[2] = getY();
	OutArray[3] = getZ();
 	return OutArray;
}

#pragma once
#include <iostream>
#include <string>

class Vector
{
public:
	Vector(double, double, double);  //Constructor
	Vector(double[3]);
	Vector();

	void setX(double);
	void setY(double);
	void setZ(double);

	double getX();
	double getY();
	double getZ();

	double* toDoubleArray();

private:
	double X;
	double Y;
	double Z;
};
class Rotation
{
public:
	Rotation(double, double, double);
	Rotation(double[3]);
	Rotation();

	/*SETTERS*/
	void setRoll(double);
	void setPitch(double);
	void setYaw(double);
	/*GETTERS*/
	double getRoll();
	double getPitch();
	double getYaw();

	double* toDoubleArray();

private:
	double Roll, Pitch, Yaw;

};
class Transform
{
public:
	Transform(Vector, Rotation, Vector);
	Transform();
	/*SETTERS*/
	void setLocation(Vector);
	void setOrientation(Rotation);
	void setScale(Vector);
	/*GETTERS*/
	Vector getLocation();
	Rotation getOrientation();
	Vector getScale();

private:
	Vector Location, Scale;
	Rotation Orientation;
};
class FourVector
{
public:
	FourVector();
	FourVector(double[4]);
	FourVector(double, double, double, double);

	void setT(double);
	void setX(double);
	void setY(double);
	void setZ(double);

	double getT();
	double getX();
	double getY();
	double getZ();

	Vector toVector();
	double* toDoubleArray();

private:
	double T, X, Y, Z;
};
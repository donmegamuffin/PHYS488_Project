#pragma once
class Vector
{
public:
	Vector(double, double, double);  //Constructor
	Vector(double[3]);
	void setX(double);
	void setY(double);
	void setZ(double);

	double getX();
	double getY();
	double getZ();
	 
	~Vector();

private:
	double X;
	double Y;
	double Z;
};


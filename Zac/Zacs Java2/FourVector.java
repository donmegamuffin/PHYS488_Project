class FourVector
{
    private double T;
    private double X;
    private double Y;
    private double Z;
            /*CONSTRUCTORS*/
    FourVector()
    {	//Creates a vector filled with 0 values
    	T = 0;
    	X = 0;
    	Y = 0;
    	Z = 0;
    }
    FourVector(double[] in_fourVec)
    {
    	T = in_fourVec[0];
    	X = in_fourVec[1];
    	Y = in_fourVec[2];
    	Z = in_fourVec[3];
    }
    FourVector(double in_T, double in_X, double in_Y, double in_Z)
    {
    	T = in_T;
    	X = in_X;
    	Y = in_Y;
    	Z = in_Z;
    }
    /*SETTERS*/
    void setT(double in_T)
    {
    	T = in_T;
    	return;
    }
    void setX(double in_X)
    {
    	X = in_X;
    	return;
    }
    void setY(double in_Y)
    {
    	Y = in_Y;
    	return;
    }
    void setZ(double in_Z)
    {
    	Z = in_Z;
    	return;
    }
    /*GETTERS*/
    double getT()
    {
    	return T;
    }
    double getX()
    {
    	return X;
    }
    double getY()
    {
    	return Y;
    }
    double getZ()
    {
    	return Z;
    }
    /*CONVERTERS*/
    Vector toVector()
    {
    	Vector OutVector = new Vector();
    	OutVector.setX(getX());
    	OutVector.setY(getY());
    	OutVector.setZ(getZ());
    	return OutVector;
    }
    double[] toDoubleArray()
    {
    	double[] OutArray = new double[4];
    	OutArray[0] = getT();
    	OutArray[1] = getX();
    	OutArray[2] = getY();
    	OutArray[3] = getZ();
     	return OutArray;
    }
}
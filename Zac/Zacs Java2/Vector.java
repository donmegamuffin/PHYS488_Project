public class Vector
{
    private double X;
    private double Y;
    private double Z;
    /**DEFAULT CONSTRUCTOR:
     * NO PARAMS - Vector is defaulted to {0,0,0} metres
     */
    public Vector()
    {
        X = 0;
        Y = 0;
        Z = 0;
    }
    
    /**ARRAY CONSTRUCTOR:
     * @param in_xyz - A 1D, 3-value double array giving X Y Z positions in metres
     */
    public Vector(double[] in_xyz)
    {
        X = in_xyz[0];
        Y = in_xyz[1];
        Z = in_xyz[2];
    }
    
    /**THREE DOUBLE CONSTRUCTOR:
     * @param in_X - A double giving the X position in metres
     * @param in_Y - A double giving the Y position in metres
     * @param in_Z - A double giving the Z position in metres
     */
    public Vector(double in_X, double in_Y, double in_Z)
    {
        X = in_X;
        Y = in_Y;
        Z = in_Z;        
    }
    
    /*SETTERS*/
    /**
     * Setter - Sets the X value of the vector
     * @param in_X - Input a new X value for the vector.
     */
    void setX(double in_x)
    {
    	X = in_x;
    	return;
    }
    /**
     * Setter - Sets the Y value of the vector
     * @param in_Y - Input a new Y value for the vector.
     */
    void setY(double in_y)
    {
    	Y = in_y;
    	return;
    }
    /**
     * Setter - Sets the Z value of the vector
     * @param in_Z - Input a new Z value for the vector.
     */
    void setZ(double in_z)
    {
    	Z = in_z;
    	return;
    }
    /*GETTERS*/
    /**
     * Getter - Gets the X value of the vector
     * @return - Returns the X value of the vector.
     */
    double getX() { return X; }
    /**
     * Getter - Gets the Y value of the vector
     * @return - Returns the Y value of the vector.
     */
    double getY() { return Y; }
    /**
     * Getter - Gets the Z value of the vector
     * @return - Returns the Z value of the vector.
     */
    double getZ() { return Z; }
    /*CONVERTERS*/
    double[] toDoubleArray()
    {
    	double[] OutArray = new double[3];
    	OutArray[0] = getX();
    	OutArray[1] = getY();
    	OutArray[2] = getZ();
    	return OutArray;
    }
}
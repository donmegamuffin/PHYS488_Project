public class Rotation
{
    private double Roll;
    private double Pitch;
    private double Yaw;
    /*CONSTRUCTORS*/
    /**THREE DOUBLE CONSTRUCTOR:
     * - Creates a Rotation with Roll, Pitch, Yaw values 
     * from 0.0 to 360.0 degree doubles.
     * @param in_Roll - A double giving the Roll angle in degrees
     * @param in_Pitch - A double giving the Pitch angle in degrees
     * @param in_Yaw - A double giving the Yaw angle in degrees
     */
    Rotation(double in_Roll, double in_Pitch, double in_Yaw)
    {
    	Roll = (in_Roll%360.0);
    	Pitch = (in_Pitch%360.0);
    	Yaw = (in_Yaw%360.0);
    }
    /**ARRAY CONSTRUCTOR:
     * - Creates a Rotation with Roll, Pitch, Yaw values 
     * from 0.0 to 360.0 degree doubles.
     * @param xyz - A double array giving the Roll, Pitch, Yaw 
     * as each value of the array in Degrees
     */
    Rotation(double[] xyz)
    {
    	Roll = (xyz[0]%360.0);
    	Pitch = (xyz[1]%360.0);
    	Yaw = (xyz[2]%360.0);
    }
    /**DEFAULT CONSTRUCTOR:
     * Creates a Rotation with all values set to 0.0 degrees.
     */
    Rotation()
    {
    	Roll = 0.0;
    	Pitch = 0.0;
    	Yaw = 0.0;
    }
    /*SETTERS*/
    void setRoll(double in_roll)
    {
    	Roll = in_roll;
    	return;
    }
    void setPitch(double in_Pitch)
    {
    	Pitch = in_Pitch;
    	return;
    }
    void setYaw(double in_Yaw)
    {
    	Yaw = in_Yaw;
    	return;
    }
    /*GETTERS*/
    double getRoll() { return Roll; }
    double getPitch() { return Pitch; }
    double getYaw() { return Yaw; }
    
    double getRadRoll() { return Roll*Math.PI/180; }
    double getRadPitch() { return Pitch*Math.PI/180; }
    double getRadYaw() { return Yaw*Math.PI/180; }
    /*CONVERTERS*/
    double[] toDoubleArray()
    {
    	double[] OutArray = new double[3];
    	OutArray[0] = getRoll();
    	OutArray[1] = getPitch();
    	OutArray[2] = getYaw();
    	return OutArray;
    }
}
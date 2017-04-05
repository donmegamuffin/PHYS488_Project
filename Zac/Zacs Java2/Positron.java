import java.util.Random;
/**
 * Creates a Muon with a limited lifetime and a 3-Vector position.
 * 
 * @author (Zachary Humphreys) 
 * @version (v0.2 29/03/2017)
 */
public class Positron 
{
    //Particle constants
    static final double MASS = 0.5109989461;            //MeV/c^2
    static final double CHARGE = 1;                    //e
    //Particle variables
    private double momentum;        //MeV
    private double energy;          //MeV
    private double beta;
    private double gamma;
    Vector location;        //Position x,y,z in metres
    //Flags runtime errors
    boolean bErrors = false;
    /**
     * CONSTRUCTOR
     * @param arg : write "energy" or "momentum" or use standard notation
     *              depending which is wanted for [EorP] input.
     * @param in_Vec : a world position (in metres) for the creation of the particle.
     * @paran inEorP : depending on what was written for [arg], input particle energy or momentum in MeV.
     */
    public Positron(String arg, Vector location, double EorP)
    {
        // initialise instance variables
        this.location = location;
        if(arg.charAt(0)=='e'||arg.charAt(0)=='E')
        {
            setEnergy(EorP);
        }else if
        ( //allows input of the word momentum OR the standard notation
        arg.charAt(0)=='p'||
        arg.charAt(0)=='P'||
        arg.charAt(0)=='m'||
        arg.charAt(0)=='M'
        )
        {
            setMomentum(EorP);
        }else
        {
            System.out.println(Error.POSITRON_EORP_UNSPECIFIED.toString());
            bErrors = true;
            return; //Because nothing specified, doesn't check anything else and returns
        }
        //Values sanity check
    	if(beta>=1)
    	{
    	    System.out.println(Error.POSITRON_SPEED_VIOLATION.toString());
            bErrors = true;
            return;
    	}
    	if(momentum>energy)
    	{
    	    System.out.println(Error.POSITRON_E_P_IMBALANCE.toString());
            bErrors = true;
            return;
    	}
    	if(VectorMaths.FuzzyEqual(5,Math.sqrt(Math.pow(energy,2)+Math.pow(momentum,2)),MASS))
    	{
    	    System.out.println(Error.POSITRON_M_E_EQUIVALENCE_FAIL.toString());
    	    System.out.println("Calculated: " + Math.sqrt(Math.pow(energy,2)+Math.pow(momentum,2)) +
    	    " MeV. Given: " + MASS);
            bErrors = true;
            return;
    	}
    }

    //-----------------GETTERS-----------------
    public double getMomentum()
    {
        return momentum;
    }
    public double getEnergy()
    {
        return energy;
    }
    public double getBeta()
    {
        return beta;
    }
    public double getGamma()
    {
        return gamma;
    }
    //-----------------SETTERS-----------------
    public void setMomentum(double momentum)
    {
        this.momentum = momentum;
        energy = energy = Math.pow((Math.pow(momentum, 2.) + Math.pow(MASS, 2.)), (0.5));
        gamma = energy / MASS;
        beta = Math.pow((1 - Math.pow(gamma,-2)), 0.5);
    }
    public void setEnergy(double energy)
    {
        this.energy = energy;
        momentum = Math.pow((Math.pow(energy,2.)-Math.pow(MASS,2.)), (0.5));
        gamma = energy / MASS;
        beta = Math.pow((1 - Math.pow(gamma,-2)), 0.5);
    }
    //-----------------METHODS-----------------
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod()
    {
        return 0;
    }
}

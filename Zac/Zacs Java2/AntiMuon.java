import java.util.Random;
/**
 * Creates a Muon with a limited lifetime and a 3-Vector position.
 * 
 * @author (Zachary Humphreys) 
 * @version (v0.2 29/03/2017)
 */
public class AntiMuon 
{
    //Particle constants
    private static final double MASS = 105.6583745;            //MeV/c^2
    private static final double MEAN_LIFETIME = 2.1969811e-6;  //s
    private static final double CHARGE = 1;                    //e
    //Particle variables
    private double momentum;        //MeV
    private double energy;          //MeV
    private double beta;
    private double gamma;
    private double relativisticLifetime;
    private double actualLifetime;
    Vector location;        //Position x,y,z in metres
    //Flags runtime errors
    public double percentLeeway = 1;
    public boolean bErrors = false;
    /**
     * CONSTRUCTOR
     * @param arg : write "energy" or "momentum" or use standard notation
     *              depending which is wanted for [EorP] input.
     * @param in_Vec : a world position (in metres) for the creation of the particle.
     * @paran inEorP : depending on what was written for [arg], input particle energy or momentum in MeV.
     */
    public AntiMuon(String arg, Vector location, double EorP)
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
        {   //Arg error check
            System.out.println(Error.MUON_EORP_UNSPECIFIED.toString());
            bErrors = true;
            return; //Because nothing specified, doesn't check anything else and returns
        }
    	relativisticLifetime = gamma*MEAN_LIFETIME;
    	Random randGen = new Random();
    	actualLifetime = (-relativisticLifetime*Math.log(randGen.nextDouble())); 
    	
    	//Values sanity check
    	if(beta>=1)
    	{
    	    System.out.println(Error.MUON_SPEED_VIOLATION.toString());
            bErrors = true;
            return;
    	}
    	if(MASS>energy)
    	{
    	    System.out.println(Error.MUON_E_M_IMBALANCE.toString());
    	    bErrors = true;  
    	    return;
    	}
    	if(momentum>energy)
    	{
    	    System.out.println(Error.MUON_E_P_IMBALANCE.toString());
            bErrors = true;
            return;
    	}
    	if(!VectorMaths.FuzzyEqual(percentLeeway,Math.sqrt(Math.pow(energy,2)-Math.pow(momentum,2)),MASS))
    	{
    	    System.out.println(Error.MUON_M_E_EQUIVALENCE_FAIL.toString());
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
    public double getActualLifetime()
    {
        return actualLifetime;
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

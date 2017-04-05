public abstract class AntiMuon extends Particle
{   //Constants
    static final double mass = 105.6583745;            //MeV/c^2
    static final double mean_lifetime = 2.1969811e-6;  //s
    static final double charge = 1;                    //e
    //Variables
    abstract public double getLifeTime();
    abstract public double getVelocity();
}
public abstract class Particle extends Actor
{   //Constants
    static final double c = 299792458;      //m/s
    double mass;                            //MeV/c^2
    double mean_lifetime;                   //s
    double charge;                          //e
                          
    //Variables
    double momentum;                        //MeV/c
    double energy;                          //MeV
    double beta;
    double gamma;
    double lifetime;                        //s
}

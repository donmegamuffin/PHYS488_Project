import java.util.Random;
class Positron_C extends Positron
{
    Random randGen = new Random();
    public Positron_C(double in_momentum)
    {
        momentum = in_momentum;
        energy = Math.sqrt(Math.pow(momentum,2)+Math.pow(mass,2));
        beta = (momentum/energy);
        gamma = (energy/mass);
        lifetime = momentum*gamma;
    }
    public double getVelocity()
    {
        return (beta*c);
    }
}
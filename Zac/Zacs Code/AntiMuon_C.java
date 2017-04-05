import java.util.Random;
class AntiMuon_C extends AntiMuon
{
    Random randGen = new Random();
    public AntiMuon_C(double in_momentum)
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
    public double getLifeTime()
    {
        return -lifetime*Math.log(randGen.nextDouble());
    }
}
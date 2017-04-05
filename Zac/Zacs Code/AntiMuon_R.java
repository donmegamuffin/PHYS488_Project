class AntiMuon_R extends AntiMuon
{
    public AntiMuon_R(double in_momentum)
    {
        momentum = in_momentum;        
    }
    public double getVelocity()
    {
        return (beta*c);
    }
    public double getLifeTime()
    {
        return 1;
    }
}
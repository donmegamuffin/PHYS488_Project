public class CombinedTest
{
    public class Vector
    {
        double[] Vec = new double[3];
        public Vector(double x,double y,double z)
        {
            Vec[0] = x;
            Vec[1] = y;
            Vec[2] = z;
        }
        public Vector(double[] xyz)
        {
            for(int n=0;n<3;n++)
            {
                Vec[n] = xyz[n]; 
            }
        }
    }
    public class Rotation
    {
        double[] Rot = new double[3];
        public Rotation(double[] in_Rot)
        {
            for(int n=0;n<3;n++)
            {
                Rot[n] = in_Rot[n]%360;
            }
        }
    }
    public class Transform
    {
        Vector Location;
        Rotation Orientation;
        Vector Scale;
        public Transform(Vector in_Location, Rotation in_Rotation, Vector in_Scale)
        {
            if(in_Location!=null)
            {
                Location = in_Location;
            }
            if(in_Rotation!=null)
            {
                Orientation = in_Rotation;
            }
            if(in_Scale!=null)
            {
                Scale=in_Scale;
            }
            else
            {
                System.out.println("Empty Transform");
            }
        }
    }
    class Particle{
        private Transform Particle_Transform;
        private double Mass;
        private double Energy;
        private double Momentum;
        public Particle(Transform in_Transform)
        {
            Particle_Transform = in_Transform;
        }
        void SetMass(double in_Mass){double Mass = in_Mass;}
        void SetEnergy(double in_Energy){double Energy = in_Energy;}
        void SetMomentum(double in_Momentum){double Momentum = in_Momentum;}
        //----------------SETTERS-----------------
        public void SetLocation(Vector in_Location){ Particle_Transform.Location = in_Location;}
        public void SetRotation(Rotation in_Rotation){Particle_Transform.Orientation = in_Rotation;}
        public void SetScale(Vector in_Scale){Particle_Transform.Scale = in_Scale;}
        public void SetTransform(Transform in_Transform){ Particle_Transform = in_Transform;}
        //--------------GETTERS-------------------
        public Vector GetLocation(){return Particle_Transform.Location;}
        public Rotation GetRotation(){return Particle_Transform.Orientation;}
        public Vector GetScale(){return Particle_Transform.Scale;}
        public Transform GetTransform(){return Particle_Transform;}
    }
}
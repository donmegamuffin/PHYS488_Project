package Data; 
abstract public class DataTypes
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
        public Transform(Vector in_Location, Rotation in_Rotation, Vector in_Scale, Transform in_Transform)
        {
            if(in_Transform==null)
            {
                if(in_Location!=null)
                {
                    SetLocation(in_Location);
                }
                if(in_Rotation!=null)
                {
                    SetRotation(in_Rotation);
                }
                if(in_Scale!=null)
                {
                    SetScale(in_Scale);
                }
            }else
            {
                SetTransform(in_Transform);
            }
        }
    }
    Transform Actor_Transform;
    //----------------SETTERS-----------------
    public void SetLocation(Vector in_Location){ Actor_Transform.Location = in_Location;}
    public void SetRotation(Rotation in_Rotation){Actor_Transform.Orientation = in_Rotation;}
    public void SetScale(Vector in_Scale){Actor_Transform.Scale = in_Scale;}
    public void SetTransform(Transform in_Transform){ Actor_Transform = in_Transform;}
    //--------------GETTERS-------------------
    public Vector GetLocation(){return Actor_Transform.Location;}
    public Rotation GetRotation(){return Actor_Transform.Orientation;}
    public Vector GetScale(){return Actor_Transform.Scale;}
    public Transform GetTransform(){return Actor_Transform;}
}
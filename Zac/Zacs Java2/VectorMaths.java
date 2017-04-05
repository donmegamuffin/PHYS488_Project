import java.io.*;

class VectorMaths
{
    //---------------------DOUBLE ARRAY------------------------------------------
    public static double[] CrossProduct(double[] in_vec1, double[] in_vec2)
    {
        double[] outvec = new double[3];
        if(in_vec1.length == 3 && in_vec2.length == 3)
        {
            outvec[0] = ((in_vec1[1]*in_vec2[2])-(in_vec2[1]*in_vec1[2]));
            outvec[1] = ((in_vec1[2]*in_vec2[0])-(in_vec2[2]*in_vec1[0]));
            outvec[2] = ((in_vec1[0]*in_vec2[1])-(in_vec2[0]*in_vec1[1]));
            return outvec;
        }
        else
        {
            System.out.println(Error.MATHS_CP_BAD_LENGTH.toString());
            return in_vec1;
        }
    }
    
    public static double[] Scale(double scalar, double[] in_vec)
    {
        double[] out_vec = new double[in_vec.length];
        for(int n=0;n<in_vec.length;n++)
        {
            out_vec[n] = in_vec[n]*scalar;
        }
        return out_vec;
    }
    
    public static double[] Add(double[] in_vec1, double[] in_vec2)
    {
        double[] out_vec = new double[in_vec1.length];
        for(int n=0;n<in_vec1.length;n++)
        {
            out_vec[n] = in_vec1[n] + in_vec2[n];
        }
        return out_vec;
    }
    
    public static double[] Subtract(double[] in_vec1, double[] in_vec2)
    {
        double[] out_vec = new double[in_vec1.length];
        for(int n=0;n<in_vec1.length;n++)
        {
            out_vec[n] = in_vec1[n] - in_vec2[n];
        }
        return out_vec;
    }
    
    public static double DotProduct(double[] in_vec1, double[] in_vec2)
    {
        double out=0;
        for(int n=0;n<in_vec1.length;n++)
        {
            out = (out+(in_vec1[n] * in_vec2[n]));
        }
        return out;
    }
    
    
    //---------------------VECTOR------------------------------------------
    public static Vector CrossProduct(Vector vec1, Vector vec2)
    {
        Vector outvec = new Vector();
        outvec.setX((vec1.getY()*vec2.getZ())-(vec2.getY()*vec1.getZ()));
        outvec.setY((vec1.getZ()*vec2.getX())-(vec2.getZ()*vec1.getX()));
        outvec.setZ((vec1.getX()*vec2.getY())-(vec2.getX()*vec1.getY()));
        return outvec;
    }
    
    public static Vector Scale(double scalar, Vector in_vec)
    {
        Vector out_vec = new Vector();
        out_vec.setX(scalar*in_vec.getX());
        out_vec.setY(scalar*in_vec.getY());
        out_vec.setZ(scalar*in_vec.getZ());
        return out_vec;
    }
    
    public static Vector Add(Vector in_vec1, Vector in_vec2)
    {
        Vector out_vec = new Vector();
        out_vec.setX(in_vec1.getX()+in_vec2.getX());
        out_vec.setY(in_vec1.getY()+in_vec2.getY());
        out_vec.setZ(in_vec1.getZ()+in_vec2.getZ());
        return out_vec;
    }
    
    public static Vector Subtract(Vector in_vec1, Vector in_vec2)
    {
        Vector out_vec = new Vector();
        out_vec.setX(in_vec1.getX()-in_vec2.getX());
        out_vec.setY(in_vec1.getY()-in_vec2.getY());
        out_vec.setZ(in_vec1.getZ()-in_vec2.getZ());
        return out_vec;
    }
    
    public static double DotProduct(Vector in_vec1, Vector in_vec2)
    {
        double out;
        out=((in_vec1.getX()*in_vec2.getX())+
        (in_vec1.getY()*in_vec2.getY())+
        (in_vec1.getZ()*in_vec2.getZ()));
        return out;
    }
    //--------------TEMP--------------
    public static boolean FuzzyEqual(double percent, double x, double y)
    {
        double upper = 1 + percent*0.01;
        double lower = 1 - percent*0.01;
        if((x/y)<=upper && (x/y)>=lower)
        {return true;}
        else{return false;}
    }
}

import java.io.*;

class VectorMaths
{
    public static double[] CrossProduct(double[] in_vec1, double[] in_vec2)
    {
        double[] outvec = new double[3];
        if(in_vec1.length == in_vec2.length)
        {
            outvec[0] = ((in_vec1[1]*in_vec2[2])-(in_vec2[1]*in_vec1[2]));
            outvec[1] = ((in_vec1[2]*in_vec2[0])-(in_vec2[2]*in_vec1[0]));
            outvec[2] = ((in_vec1[0]*in_vec2[1])-(in_vec2[0]*in_vec1[1]));
            return outvec;
        }
        else
        {
            System.out.println("Vectors are of unequal length; aborted");
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
}
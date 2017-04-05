import java.io.*;
import java.util.Arrays;

class Test
{   
    static double[] vector1 = {1,2,3};
    static double[] vector2 = {4,5,6};
    static double scale = 7;
    public static void main()
    {   //VECTOR TEST
        double[] CrossProduct = VectorMaths.CrossProduct(vector1,vector2);
        double[] Scale = VectorMaths.Scale(scale,vector1);
        double[] Add = VectorMaths.Add(vector1,vector2);
        double[] Subtract = VectorMaths.Subtract(vector1,vector2);
        double DotProduct = VectorMaths.DotProduct(vector1,vector2);
        
        System.out.println(Arrays.toString(CrossProduct));
        System.out.println(Arrays.toString(Scale));
        System.out.println(Arrays.toString(Add));
        System.out.println(Arrays.toString(Subtract));
        System.out.println(DotProduct);
        
        //PARTICLES TEST
        AntiMuon_C MuonC = new AntiMuon_C(1);
        AntiMuon_R MuonR = new AntiMuon_R(1);
        Positron_C PositronC = new Positron_C(1);
        Positron_R PositronR = new Positron_R(1);
        
        
    }
}
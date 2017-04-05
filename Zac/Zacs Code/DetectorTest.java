import java.io.*;
import java.util.Arrays;
import Data.DataTypes;

class DetectorTest
{
    public static void main()
    {
        //Get Detector plane vector
        Transform TestTransform = new Transform(null,null,null,null);
        Detector MyDetector = new Detector(TestTransform);
        double[] p1 = {1,2};
        double[] p2 = {3,5};
        double[] p3 = {2,7};
        double[] p4 = {3,9};
        System.out.println(MyDetector.Intersect_Check(p1,p2,p3,p4));
        //Get particle move vector
        //Check if they intersect
    }
    
}
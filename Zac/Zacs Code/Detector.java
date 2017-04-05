import java.util.Arrays;
class Detector extends Actor
{
    Vector TestVec = new Vector(1,2,3);
    Rotation TestRot = new Rotation(new double[]{0,420,180});
    Transform Detector_Transform = new Transform(TestVec,TestRot,TestVec,null);
    Transform Detector_Transform_Testcop = new Transform(null,null,null,Detector_Transform);
    
    public Detector(Transform in_Transform)
    {
        Detector_Transform = in_Transform;  
        System.out.println(GetTransform());
    }
    double orientation(double[] point_a, double[] point_b, double[] point_c)
    {
        double val = (point_b[1]-point_a[1])*(point_c[0]-point_b[0])-(point_b[0]-point_a[0])*(point_c[1]-point_b[1]);
        return val;
    }
    // Given three colinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    boolean onSegment(double[] p, double[] q, double[] r)
    {
        if (q[0] <= max(p[0], r[0]) && q[0] >= min(p[0], r[0]) && q[1] <= max(p[1], r[1]) && q[1] >= min(p[1], r[1])){return true;}
        else{ return false;}
    }
    boolean Intersect_Check(double[] point_a, double[] point_b, double[] point_c, double[] point_d)
    {
        //Find the four orientations needed
        double o1 = orientation(point_a, point_b, point_c);
        double o2 = orientation(point_a, point_b, point_d);
        double o3 = orientation(point_c, point_d, point_a);
        double o4 = orientation(point_c, point_d, point_b);
        //General Case
        if(o1!=o2 && o3!=o4){return true;}
        // Special Cases
        // point_a, point_b and point_c are colinear and point_c lies on segment point_apoint_b
        else if (o1 == 0 && onSegment(point_a, point_c, point_b)) {return true;}
     
        // point_a, point_b and point_c are colinear and point_d lies on segment point_apoint_b
        else if (o2 == 0 && onSegment(point_a, point_d, point_b)) {return true;}
     
        // point_c, point_d and point_a are colinear and point_a lies on segment point_cpoint_d
        else if (o3 == 0 && onSegment(point_c, point_a, point_d)) {return true;}
     
         // point_c, point_d and point_b are colinear and point_b lies on segment point_cpoint_d
        else if (o4 == 0 && onSegment(point_c, point_b, point_d)){ return true;}
     
        else{return false;} // Doesn't fall in any of the above cases
    }
    double max(double a, double b)
    {
        if(a>b){return a;}
        else if(b>=a){return b;}
        else {return 0;}
    }
    double min(double a, double b)
    {
        if(a<b){return a;}
        else if(b<=a){return b;}
        else {return 0;}
    }
}

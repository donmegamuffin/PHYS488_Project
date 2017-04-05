import java.io.*;
import java.util.Random;
import java.util.Arrays; 

// 2D tracking of a high energy muon through iron with no magnetic field
class electron_circle
{
    static BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in)) ;
    static PrintWriter screen = new PrintWriter( System.out, true);
    static Random randGen = new Random();    
    
    static final double muonmass = 105.6583745; //MeV
    static final double c = 299792458;
    static final double muonmass_ERROR = 0.0000024;
    static final double magnetic_field = 1.45;
    static final double radius = 7.112;      
      
    static final double magic_momentum = 29.3; // lorenz factor for things to cancel out
    static final double muon_momentum = Math.sqrt(Math.pow(muonmass,2)*(Math.pow(magic_momentum,2)-1)); //muon momnetum that will be measured, all others will be noise
    static final double muon_momentum_ERROR = (muonmass_ERROR/muonmass)*muon_momentum; //Error from mass error only
    static final double muon_energy = magic_momentum*muonmass;
    static final double mean_lifetime = 2.1969811E-6;
    static final double circumference = 2*Math.PI*radius;
    static final double speed = c*Math.sqrt(1-(1/Math.pow(magic_momentum,2)));
    static final double electron_mass = 0.5109989461; //MeV   
     static final double electron_charge = 1.6021766208E-19;
    static double finalX;
    static double finalY;
        
    static double detector_angle1 = (15*Math.PI)/180;
    static double detector_angle2 = (180*Math.PI)/180;
    static double detector_angle3 = (270*Math.PI)/180;
    static double [] [] detector_positions = new double [2] [464]; // where [0][] is x and [1][] is y  , this is for each of the 9 detectors
    static double [] electron_X;
    static double [] electron_Y;
    static int nmax = 2000;
    static int H;
    static double [] [] Hit;
      
    private static double [] [] Detector_hit() {       
        double [] a = {radius-0.08,radius-0.08,radius-0.12,radius-0.12,radius-0.12,radius-0.16,radius-0.16,radius-0.16,radius-0.16}; 
        double [] detector_angle = new double [3];        
        detector_angle1 = (15*Math.PI)/180;
        double []  width = {0.06,0.06,0.10,0.10,0.10,0.14,0.14,0.14,0.14};
           
         for (int n = 0; n <= 1; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 31; i++) {
              detector_positions [0] [i+32*n] = ((a [n])+((i)*(width [n]/31)))*Math.cos(real_angle);
              detector_positions [1] [i+32*n] = ((a [n])+((i)*(width [n]/31)))*Math.sin(real_angle);
            }
        }        
         for (int n = 2; n <= 4; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 47; i++) {
              detector_positions [0] [i+32*2+48*(n-2)] = ((a [n])+((i)*(width [n]/47)))*Math.cos(real_angle);
              detector_positions [1] [i+32*2+48*(n-2)] = ((a [n])+((i)*(width [n]/47)))*Math.sin(real_angle);
            }
        }  
         for (int n = 5; n <= 8; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 63; i++) {
              detector_positions [0] [i+32*2+48*3+64*(n-5)] = ((a [n])+((i)*(width [n]/63)))*Math.cos(real_angle);
              detector_positions [1] [i+32*2+48*3+64*(n-5)] = ((a [n])+((i)*(width [n]/63)))*Math.sin(real_angle);
            }
        }  
        //all dots have an error of 0.01m
        return detector_positions;
    }    
    
    private static void WriteToDetectors() throws IOException {
        FileWriter file = new FileWriter("detector.csv");  
        PrintWriter outputFile = new PrintWriter("detector.csv");
        
        Detector_hit();
         
        for (int n = 0; n < 464; n++) {             
        outputFile.println((n+1) + "," + detector_positions [0][n] + "," + detector_positions [1][n]);
        }
               
       outputFile.close();
       screen.println("Data written to disk in file " + "detector.csv");
       return;
    }
    
      public static void main (String [] args) throws IOException
    {
    FileWriter file = new FileWriter("HITS.csv");  
    PrintWriter outputFile = new PrintWriter("HITS.csv");
    
    electron_X = new double [nmax];
    electron_Y = new double [nmax];
    H = 0;
    Hit = new double [2] [H+1];
    
    double angle = 22;
        finalX = radius*Math.cos((angle*Math.PI)/180);
        finalY = radius*Math.sin((angle*Math.PI)/180); //decided to decay at 16 degrees
        
        double electron_energy = muon_energy*0.7; //for now, this will be chnaged
        double electron_momentum = Math.sqrt(Math.pow(electron_energy,2) - Math.pow(electron_mass,2));
        double electron_lorentz = electron_energy/electron_mass;
        double electron_path_radius = ((electron_momentum*electron_charge*1E6)/c)/(magnetic_field*electron_charge); //without use of lorentz as lorentz is very high
        double electron_radiusdifference = radius - electron_path_radius;
        double electron_pathcentre_x = electron_radiusdifference*Math.cos((angle*Math.PI)/180);
        double electron_pathcentre_y = electron_radiusdifference*Math.sin((angle*Math.PI)/180);
        Detector_hit();       
        boolean hit1;
        boolean hit2;
        
        for (int n=0; n < nmax; n++) {
            hit1 = false;
            hit2 = false;
            
            electron_X [n] = electron_path_radius*Math.cos((((double)n*360/(double)nmax)*Math.PI)/180) + electron_pathcentre_x;
            electron_Y [n] = electron_path_radius*Math.sin((((double)n*360/(double)nmax)*Math.PI)/180) + electron_pathcentre_y;
            for (int L = 0; L < 464; L++) {
            if (electron_X [n] <= (detector_positions [0] [L] + 0.01) && electron_X [n] >= (detector_positions [0] [L] - 0.01)){
                hit1 = true;                
            }
            if (electron_Y [n] <= (detector_positions [1] [L] + 0.01) && electron_Y [n] >= (detector_positions [1] [L] - 0.01)){
                hit2 = true;                 
            }
            if (hit1 == true && hit2 == true){
                Hit [0][H] = electron_X [n];
                Hit [1][H] = electron_Y [n];
                outputFile.println((H+1) + "," + Hit [0][H] + "," + Hit [1][H]);
                H++;                
            }
            
            Hit = new double [2] [H+1];
           }
            
        }
        
        
    
    
    
    WriteToDetectors();
     
    
    outputFile.close();
    screen.println("Data written to disk in file " + "HITS.csv");
    return;
    }
    
}
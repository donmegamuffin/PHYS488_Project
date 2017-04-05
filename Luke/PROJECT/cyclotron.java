import java.io.*;
import java.util.Random;
import java.util.Arrays; 

// 2D tracking of a high energy muon through iron with no magnetic field
class cyclotron
{
    static BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in)) ;
    static PrintWriter screen = new PrintWriter( System.out, true);
    static Random randGen = new Random();    
    
    static final double muonmass = 105.6583745; //MeV
    static final double c = 299792458;
    static final double muonmass_ERROR = 0.0000024;
    static final double magnetic_field = 1.45;
    static final double radius = 7.112;       
<<<<<<< HEAD
    static final int number_of_muons = 15;    
=======
    static final int number_of_muons = 20;    
>>>>>>> origin/master
    static final double magic_momentum = 29.3; // lorenz factor for things to cancel out
    static final double muon_momentum = Math.sqrt(Math.pow(muonmass,2)*(Math.pow(magic_momentum,2)-1)); //muon momnetum that will be measured, all others will be noise
    static final double muon_momentum_ERROR = (muonmass_ERROR/muonmass)*muon_momentum; //Error from mass error only
    static final double muon_energy = magic_momentum*muonmass;
    static final double mean_lifetime = 2.1969811E-6;
    static final double circumference = 2*Math.PI*radius;
    static final double speed = c*Math.sqrt(1-(1/Math.pow(magic_momentum,2)));
    static final double electron_mass = 0.5109989461; //MeV   
     static final double electron_charge = 1.6021766208E-19;
    static double [] finalX;
    static double [] finalY;
    static double [] finaltime;    
    static double detector_angle1 = (15*Math.PI)/180;
    static double detector_angle2 = (180*Math.PI)/180;
    static double detector_angle3 = (270*Math.PI)/180;
<<<<<<< HEAD
    static double [] [] detector_positions = new double [2] [270]; // where [0][] is x and [1][] is y  , this is for each of the 9 detectors
    static double [] [] electron_X;
    static double [] [] electron_Y;
    static int nmax = 200;
    static int H;
    static double [] [] Hit;
=======
    static double [] [] detector_positions = new double [2] [90]; // where [0][] is x and [1][] is y  , this is for each of the 9 detectors    
    
>>>>>>> origin/master
    private static void WriteToCircle() throws IOException {
        FileWriter file = new FileWriter("circle.csv");  
        PrintWriter outputFile = new PrintWriter("circle.csv");
         
        for (int n = 0; n < number_of_muons; n++) {             
        outputFile.println((n+1) + "," + finalX [n] + "," + finalY [n] + "," + finaltime [n]);
        }
    
       outputFile.close();
       screen.println("Data written to disk in file " + "circle.csv");
       return;
    }
        
    private static double [] [] Detector_hit() {       
        double [] a = {radius-0.08,radius-0.08,radius-0.12,radius-0.12,radius-0.12,radius-0.16,radius-0.16,radius-0.16,radius-0.16}; 
        double [] detector_angle = new double [3];        
        detector_angle [0] = (15*Math.PI)/180;
        detector_angle [1] = (180*Math.PI)/180;
        detector_angle [2] = (270*Math.PI)/180;
        for (int L = 0; L <= 2; L++) {        
         for (int n = 0; n <= 8; n++) {
            double real_angle = detector_angle [L] - ((0.09*n)/a [n]);            
            double []  width = {0.06,0.06,0.10,0.10,0.10,0.14,0.14,0.14,0.14};
            for (int i = 0; i <= 9; i++) {
              detector_positions [0] [n+i+9*n+L*90] = ((a [n])+((i)*(width [n]/9)))*Math.cos(real_angle);
              detector_positions [1] [n+i+9*n+L*90] = ((a [n])+((i)*(width [n]/9)))*Math.sin(real_angle);
            }
        }        
      }
        
        //all dots have an error of 0.01m
        return detector_positions;
    }    
    
    private static void WriteToDetectors() throws IOException {
        FileWriter file = new FileWriter("detector.csv");  
        PrintWriter outputFile = new PrintWriter("detector.csv");
        
        Detector_hit();
         
        for (int n = 0; n < 270; n++) {             
        outputFile.println((n+1) + "," + detector_positions [0][n] + "," + detector_positions [1][n]);
        }
               
       outputFile.close();
       screen.println("Data written to disk in file " + "detector.csv");
       return;
    }
    
<<<<<<< HEAD
    private static void WriteToElectron() throws IOException {
        FileWriter file = new FileWriter("Electron.csv");  
        PrintWriter outputFile = new PrintWriter("Electron.csv");
       for (int i =0; i < number_of_muons; i++) { 
        for (int n = 0; n < nmax; n++) {             
        outputFile.println((n+1) + "," + electron_X [i][n] + "," + electron_Y [i][n]);
        }
       }
       outputFile.close();
       screen.println("Data written to disk in file " + "Electron.csv");
       return;
    }
=======
>>>>>>> origin/master
   
    
      public static void main (String [] args) throws IOException
    {
    FileWriter file = new FileWriter("HITS.csv");  
    PrintWriter outputFile = new PrintWriter("HITS.csv");
        
    finalX = new double [number_of_muons];
    finalY = new double [number_of_muons];
    finaltime = new double [number_of_muons];
<<<<<<< HEAD
    electron_X = new double [number_of_muons] [nmax];
    electron_Y = new double [number_of_muons] [nmax];
    H = 0;
    Hit = new double [2] [H+1];
=======
>>>>>>> origin/master
    
    for (int i =0; i < number_of_muons; i++) {         
        double lifetime = -mean_lifetime*Math.log(randGen.nextDouble());    
        //screen.println("lifetime of muon " + (i+1) + " is " + lifetime);
        double distance_travelled = speed*lifetime;    
        double loops = distance_travelled/circumference;
        //screen.println("The amounts of loops it did is " + loops);
        finalX [i] = radius*Math.cos(distance_travelled/radius);
        finalY [i] = radius*Math.sin(distance_travelled/radius);
        finaltime [i] = lifetime;
        double electron_energy = muon_energy; //for now, this will be chnaged
        double electron_momentum = Math.sqrt(Math.pow(electron_energy,2) - Math.pow(electron_mass,2));
<<<<<<< HEAD
        double electron_lorentz = electron_energy/electron_mass;
        double electron_path_radius = ((electron_momentum*electron_charge*1E6)/c)/(magnetic_field*electron_charge); //without use of lorentz as lorentz is very high
        double electron_radiusdifference = radius - electron_path_radius;
        double electron_pathcentre_x = electron_radiusdifference*Math.cos(distance_travelled/radius);
        double electron_pathcentre_y = electron_radiusdifference*Math.sin(distance_travelled/radius);
        Detector_hit();       
        boolean hit1;
        boolean hit2;
        
        for (int n=0; n < nmax; n++) {
            hit1 = false;
            hit2 = false;
            
            electron_X [i][n] = electron_path_radius*Math.cos((((double)n*360/(double)nmax)*Math.PI)/180) + electron_pathcentre_x;
            electron_Y [i][n] = electron_path_radius*Math.sin((((double)n*360/(double)nmax)*Math.PI)/180) + electron_pathcentre_y;
            for (int L = 0; L < 270; L++) {
            if (electron_X [i][n] <= (detector_positions [0] [L] + 0.01) && electron_X [i][n] >= (detector_positions [0] [L] - 0.01)){
                hit1 = true;                
            }
            if (electron_Y [i][n] <= (detector_positions [1] [L] + 0.01) && electron_Y [i][n] >= (detector_positions [1] [L] - 0.01)){
                hit2 = true;                 
            }
            if (hit1 == true && hit2 == true){
                Hit [0][H] = electron_X [i][n];
                Hit [1][H] = electron_Y [i][n];
                outputFile.println((H+1) + "," + Hit [0][H] + "," + Hit [1][H]);
                H++;                
            }
            
            Hit = new double [2] [H+1];
           }
            
        }
        
        
    }
    
    WriteToCircle(); 
    WriteToDetectors();
    WriteToElectron();    
    
    outputFile.close();
    screen.println("Data written to disk in file " + "HITS.csv");
    return;
=======
        double electron_path_radius = ((electron_momentum*electron_charge*1E6)/c)/(magnetic_field*electron_charge);
    }
    
    WriteToCircle();
    WriteToDetectors();   
    
>>>>>>> origin/master
    }
    
}
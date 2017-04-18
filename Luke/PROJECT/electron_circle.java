import java.io.*;
import java.util.Random;
import java.util.Arrays;
import java.util.stream.*; 

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
    static double [] [] detector_positions = new double [2] [928]; // where [0][] is x and [1][] is y  , this is for each of the 9 detectors
    static double [] electron_X;
    static double [] electron_Y;
    static double [] predicted_electron_X;
    static double [] predicted_electron_Y;
    static int nmax = 100000;
    static double [] [] Hit;
    static double [] [] HitEnd;
    
    private static double [] [] Detector_hit() {       
        double [] a = {radius-0.085,radius-0.085,radius-0.125,radius-0.125,radius-0.125,radius-0.165,radius-0.165,radius-0.165,radius-0.165}; 
        double [] detector_angle = new double [3];        
        detector_angle1 = (15*Math.PI)/180;
        double []  width = {0.08,0.08,0.12,0.12,0.12,0.16,0.16,0.16,0.16};
           
         for (int n = 0; n <= 1; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 63; i++) {
              detector_positions [0] [i+64*n] = ((a [n])+((i)*(width [n]/63)))*Math.cos(real_angle);
              detector_positions [1] [i+64*n] = ((a [n])+((i)*(width [n]/63)))*Math.sin(real_angle);
            }
        }        
         for (int n = 2; n <= 4; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 95; i++) {
              detector_positions [0] [i+64*2+96*(n-2)] = ((a [n])+((i)*(width [n]/95)))*Math.cos(real_angle);
              detector_positions [1] [i+64*2+96*(n-2)] = ((a [n])+((i)*(width [n]/95)))*Math.sin(real_angle);
            }
        }  
         for (int n = 5; n <= 8; n++) {
            double real_angle = detector_angle1 - ((0.09*n)/a [n]);
            for (int i = 0; i <= 127; i++) {
              detector_positions [0] [i+64*2+96*3+128*(n-5)] = ((a [n])+((i)*(width [n]/127)))*Math.cos(real_angle);
              detector_positions [1] [i+64*2+96*3+128*(n-5)] = ((a [n])+((i)*(width [n]/127)))*Math.sin(real_angle);
            }
        }  
        //all dots have an error of 0.01m
        return detector_positions;
    }    
    
    private static void WriteToElectron() throws IOException {
        FileWriter file = new FileWriter("Electron.csv");  
        PrintWriter outputFile = new PrintWriter("Electron.csv");
       
        for (int n = 0; n < nmax; n++) {             
        outputFile.println((n+1) + "," + electron_X [n] + "," + electron_Y [n]);
        }
       
       outputFile.close();
       screen.println("Data written to disk in file " + "Electron.csv");
       return;
    }
    
    private static void WriteToDetectors() throws IOException {
        FileWriter file = new FileWriter("detector.csv");  
        PrintWriter outputFile = new PrintWriter("detector.csv");
        
        Detector_hit();
         
        for (int n = 0; n < 928; n++) {             
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
    int H = 0;   
    Hit = new double [2] [5000];
    double [][][] HitMid = new double [9] [2] [5000];
    HitEnd = new double [2] [9];
    int P = 1;
    int F = 0;
    int s = 0;
    int Max_P = 0;
    for (int n=0; n < nmax; n++) {
       electron_X [n] = electron_path_radius*Math.cos(((3+(double)n*15/nmax)*Math.PI)/180) + electron_pathcentre_x;
       electron_Y [n] = electron_path_radius*Math.sin(((3+(double)n*15/nmax)*Math.PI)/180) + electron_pathcentre_y;
       for (int L = 0; L < 928; L++) {            
            if (electron_X [n] <= (detector_positions [0] [L] + 0.0005) && electron_X [n] >= (detector_positions [0] [L] - 0.0005)){
                hit1 = true;                             
            }
            else {
                hit1 = false;
            }
            if (electron_Y [n] <= (detector_positions [1] [L] + 0.0005) && electron_Y [n] >= (detector_positions [1] [L] - 0.0005)){
                hit2 = true;                                
            }
            else {
                hit2 = false;
            }
            if (hit1 == true && hit2 == true){
                Hit [0][H] = electron_X [n];
                Hit [1][H] = electron_Y [n];
                if (L <= 63){
                    s = 0;
                }
                if (L >= 64 && L <= 127){
                    s = 1;
                }
                if (L >= 128 && L <= 223){
                    s = 2;
                }
                if (L >= 224 && L <= 319){
                    s = 3;
                }
                if (L >= 320 && L <= 415){
                    s = 4;
                }
                if (L >= 416 && L <= 543){
                    s = 5;
                }
                if (L >= 544 && L <= 671){
                    s = 6;
                }
                if (L >= 672 && L <= 799){
                    s = 7;
                }
                if (L >= 800){
                    s = 8;
                }
                if (H == 0){
                    HitMid [s][0][0] = Hit [0][0];
                    HitMid [s][1][0] = Hit [1][0];
                }
                if (H > 0){                    
                    if (Hit [0] [H-1] != Hit [0][H] && Hit [1][H-1] != Hit [1][H]) {  
                     if (F != s){
                         P = 0;                         
                     }
                     F = s;
                     HitMid [s][0][P] = Hit [0][H];
                     HitMid [s][1][P] = Hit [1][H];
                     if (Max_P < P){
                         Max_P = P - 1;
                     }
                     P++;                     
                  }
                }                
                H++;                
            }            
       }            
    }
    double [][] sum = new double [2][9];    
    for (int n=0; n < 9; n++) {
        sum [0][n] = 0;
        sum [1][n] = 0;
        for (int i=0; i < Max_P; i++) {
            sum [0][n] = sum [0][n] + HitMid [n][0][i];
            sum [1][n] = sum [1][n] + HitMid [n][1][i];
        }        
        HitEnd [0][n] = sum [0][n] / Max_P;
        HitEnd [1][n] = sum [1][n] / Max_P;
    }
    
    screen.println("The electron energy is " + electron_energy + " MeV");
    
    //using energy and time that the detectors detected I can work back and find the time and place it decayed from
    //electron_pathradius can be found from the energy and so the angle at which it decayed can be found
    
    double [] a_predicted_angle = new double [36];
    double [] mid_point = new double [2];
    double [] path_centre = new double [2];
    double [][] every_path_centre = new double [2][36];
    int l = 0;     
    int o = 0;
    int p = 0;
    double total = 0;
    for (int n=0; n < 36; n++) {
        if (n <= 7){          
          o = n;
        }
        if (n >= 8 && n <= 14) {
          l = 1;
          o = n - 8;
          p = 8;
        }
        if (n >= 15 && n <= 20) {
          l = 2;
          o = n - 15;
          p = 15;
        }      
        if (n >= 21 && n <= 25) {
          l = 3;
          o = n - 21;
          p = 21;
        }    
        if (n >= 26 && n <= 29) {
          l = 4;
          o = n - 26;
          p = 26;
        }   
        if (n >= 30 && n <= 32) {
          l = 5;
          o = n - 30;
          p = 30;
        }    
        if (n >= 33 && n <= 34) {
          l = 6;
          o = n - 33;
          p = 33;
        }         
        if (n == 35) {
          l = 7;
          o = n - 35;
          p = 35;
        } 
        
        double AB = Math.sqrt(Math.pow(HitEnd [0] [0+l] - HitEnd [0] [8-o],2) + Math.pow(HitEnd [1] [0+l] - HitEnd [1] [8-o],2));                
        double a_mid_point = Math.sqrt(Math.pow(electron_path_radius,2) - Math.pow(AB/2,2));
        double m_mid_point = (HitEnd [0] [0+l] - HitEnd [0] [8-o])/(HitEnd [1] [8-o] - HitEnd [1] [0+l]);
        
        mid_point [0] = (HitEnd [0] [0+l] + HitEnd [0] [8-o])/2;
        mid_point [1] = (HitEnd [1] [0+l] + HitEnd [1] [8-o])/2;    
        
        path_centre [0] = mid_point [0] - a_mid_point / Math.sqrt(1 + Math.pow(m_mid_point,2));
        path_centre [1] = mid_point [1] - (m_mid_point*a_mid_point) / Math.sqrt(1 + Math.pow(m_mid_point,2));
        
        every_path_centre [0][o+p] = path_centre [0];
        every_path_centre [1][o+p] = path_centre [1];
        
        a_predicted_angle [o+p] = Math.atan2(path_centre [1]/electron_radiusdifference,path_centre [0]/electron_radiusdifference)*180/Math.PI;
        
    }
    
    double average_predicted_angle = DoubleStream.of(a_predicted_angle).sum()/36; 
    double average_every_path_centre_x = DoubleStream.of(every_path_centre [0]).sum()/36; 
    double average_every_path_centre_y = DoubleStream.of(every_path_centre [1]).sum()/36; 
    screen.println(average_predicted_angle);
    screen.println(electron_pathcentre_x + "   " + electron_pathcentre_y  + "   " +  average_every_path_centre_x  + "   " +  average_every_path_centre_y);
    
    double electron_speed = c*Math.sqrt(1-( Math.pow(electron_mass,2)/Math.pow(electron_energy,2) ));
    double [] time = new double [10];
    double [] hit_angle = new double [9];
    for (int n=0; n < 9; n++) {
        hit_angle [n] = Math.atan2( HitEnd[1][n] , HitEnd[0][n] );
        double distance_from_decay = Math.abs( electron_path_radius * (hit_angle [n] - 22) );
        time [n] = distance_from_decay / electron_speed;
    }
    outputFile.println(1 + "," + "x Axis" + "," + "y Axis" + "," + "Time since creation");
    outputFile.println(1 + "," + HitEnd [0][0] + "," + HitEnd [1][0] + "," + time [0]);
    for (int n=1; n < 9; n++) {
    outputFile.println((n+1) + "," + HitEnd [0][n] + "," + HitEnd [1][n] + "," + time [n]); 
    }
    
    WriteToDetectors();
    WriteToElectron();
       
    
    outputFile.close();
    screen.println("Data written to disk in file " + "HITS.csv");
    return;
    }
    
}
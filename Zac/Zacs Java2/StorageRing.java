import java.io.*;
/**
 * Executable simulation of storage ring
 * 
 * @author (Zachary Humphreys) 
 * @version (v0.1 29/03/2017)
 */
public class StorageRing
{
    static int numOfParticles;
    public static void main(int particles) throws IOException
    {
        numOfParticles = particles;
        //Ring constants
        double Ring_Circumference = 14*Math.PI;
        double c = 299792458; //m/s
        
        //Particle spawn constants
        Histogram lifeDist = new Histogram(20,0,5e-4);
        AntiMuon[] MuonArray = new AntiMuon[numOfParticles];
        Positron[] PosiArray = new Positron[numOfParticles];
        double energy = 3106;
        Vector SpawnPosition = new Vector(0,0,0);
        Rotation[] DeathRotation = new Rotation[numOfParticles];
        Vector[] DeathPosition = new Vector[numOfParticles];
        Vector[] PosiLocalPos = new Vector[numOfParticles];
        //Spawn particles
        for(int n=0;n<numOfParticles;n++)
        {
            MuonArray[n] = new AntiMuon("e",SpawnPosition,energy);
            DeathRotation[n] = new Rotation();
            DeathPosition[n] = new Vector();
            //ERROR CHECK
            if(MuonArray[n].bErrors == true)
            {
                System.out.println(System.identityHashCode(MuonArray[n])+" has been deleted.");
                MuonArray[n] = null;
            }
            lifeDist.fill(MuonArray[n].getActualLifetime());
            
            //Calculate where on ring it decays
            double travelled = c*MuonArray[n].getBeta()*MuonArray[n].getActualLifetime();
            double stoppedAt = (travelled%Ring_Circumference);
            DeathRotation[n].setYaw((stoppedAt*360)/Ring_Circumference);
            DeathPosition[n].setX(7*Math.cos(DeathRotation[n].getRadYaw()));
            DeathPosition[n].setY(7*Math.sin(DeathRotation[n].getRadYaw()));
            
            
            PosiArray[n] = new Positron("e",DeathPosition[n],1000);
            if(PosiArray[n].bErrors == true)
            {
                System.out.println(System.identityHashCode(PosiArray[n])+" has been deleted.");
                PosiArray[n] = null;
            }
            PosiLocalPos[n] = new Vector();          
        }
        lifeDist.print();
        lifeDist.writeToDisk("Lifetimes.csv");
        
        String filename = "DeathPositions.csv";
        FileWriter file = new FileWriter(filename);     // this creates the file with the given name
        PrintWriter outputFile = new PrintWriter(file); // this sends the output to file

        // Write the file as a comma seperated file (.csv) so it can be read it into EXCEL
        // File coulmn headers
        outputFile.println("Muon# , x , y");
    
        // now make a loop to write the contents of each bin to disk, one number at a time
        // together with the x-coordinate of the centre of each bin.
        for (int n=0;n<numOfParticles; n++) 
        {
            // comma separated values
            outputFile.println(n + "," + DeathPosition[n].getX() + "," + DeathPosition[n].getY());
        }
        outputFile.close(); // close the output file
        System.out.println("Data written to disk in file " + filename +"\n\n");
        return;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    public void DecaysToDisk(String filename) throws IOException //<<<TASK2.1>>
    {
        FileWriter file = new FileWriter(filename);     // this creates the file with the given name
        PrintWriter outputFile = new PrintWriter(file); // this sends the output to file

        // Write the file as a comma seperated file (.csv) so it can be read it into EXCEL
        // File coulmn headers
        outputFile.println("Muon# , x , y");
    
        // now make a loop to write the contents of each bin to disk, one number at a time
        // together with the x-coordinate of the centre of each bin.
        for (int n=0;n<numOfParticles; n++) 
        {
            // comma separated values
            outputFile.println(n + "," + 
        }
        outputFile.close(); // close the output file
        System.out.println("Data written to disk in file " + filename +"\n\n");
        return;
    }
    */
}

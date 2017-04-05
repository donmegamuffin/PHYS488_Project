using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace CsharpTest {
    public static class Program {
        static int numOfParticles;
        public static void Main()
        {
            //Get user input
            Console.Write("Please insert the number of particles you wish to simulate:\t\n");
            Console.Write("---NOTE: Currently no error-checking.---\n");
            numOfParticles = Int32.Parse(Console.ReadLine());
            
            //Main function


            //Ring constants
            double Ring_Circumference = 14 * Math.PI;
            double c = 299792458; //m/s

            //Particle spawn constants
            Histogram lifeDist = new Histogram(20, 0, 5e-4);
            AntiMuon[] MuonArray = new AntiMuon[numOfParticles];
            double energy = 3106;
            Vector SpawnPosition = new Vector(0, 0, 0);
            Rotation[] DeathRotation = new Rotation[numOfParticles];
            Vector[] DeathPosition = new Vector[numOfParticles];
            Vector[] PosiLocalPos = new Vector[numOfParticles];
            //Spawn particles
            for (int n = 0; n < numOfParticles; n++)
            {
                MuonArray[n] = new AntiMuon("e", SpawnPosition, energy,n);
                DeathRotation[n] = new Rotation();
                DeathPosition[n] = new Vector();
                //ERROR CHECK
                if (MuonArray[n].bErrors == true)
                {
                    Console.WriteLine(MuonArray[n].GetHashCode() + " has been deleted.");
                    MuonArray[n] = null;
                }

                lifeDist.fill(MuonArray[n].getActualLifetime());

                //Calculate where on ring it decays
                double travelled = c * MuonArray[n].getBeta() * MuonArray[n].getActualLifetime();
                double stoppedAt = (travelled % Ring_Circumference);
                DeathRotation[n].setYaw((stoppedAt * 360) / Ring_Circumference);
                DeathPosition[n].setX(7 * Math.Cos(DeathRotation[n].getRadYaw()));
                DeathPosition[n].setY(7 * Math.Sin(DeathRotation[n].getRadYaw()));
            }
            lifeDist.print();
            lifeDist.writeToDisk("Lifetimes.csv");

            //FILE WRITING
            String filename = "DeathPositions.csv";
            StringBuilder csvcontent = new StringBuilder();

            // Write the file as a comma seperated file (.csv) so it can be read it into EXCEL
            // File coulmn headers
            csvcontent.AppendLine("Muon# , x , y");

            // now make a loop to write the contents of each bin to disk, one number at a time
            // together with the x-coordinate of the centre of each bin.
            for (int i = 0; i < numOfParticles; i++)
            {
                // comma separated values
                csvcontent.AppendLine(i + "," + DeathPosition[i].getX() + "," + DeathPosition[i].getY());
            }
            File.AppendAllText(filename, csvcontent.ToString());
            Console.WriteLine("Data written to disk in file " + filename + "\n\n");
            //Hangs console
            Console.ReadLine();
            return;
        }
    }
}


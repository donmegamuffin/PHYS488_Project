using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace CsharpTest {
    public class Histogram {
        private double binlow, binhigh;
        private double binsize;
        private int SIZE;
        private int nfilled; //<<<TASK1.1>>>

        // integer array to store the actual histogram data
        private int[] hist;
        private long underflow, overflow;


        // constructor for the class Histogram
        public Histogram(int size, double low, double high)
        {
            // store the parameters and setup the histogram
            // note that parameters need to have different names than class variables
            SIZE = size;
            binlow = low;
            binhigh = high;

            binsize = (binhigh - binlow) / (double)SIZE;
            hist = new int[SIZE];
            underflow = 0;
            overflow = 0;
        }

        //-------------------------------------
        // instance methods start here
        //-------------------------------------

        public int getSize()
        {
            return SIZE;
        }

        public int getNfilled()          //<<<TASK1.2>>>
        {
            return nfilled;
        }

        public long getUnderflow()       //<<<TASK1.2>>>
        {
            return underflow;
        }

        public long getOverflow()        //<<<TASK1.2>>>
        {
            return overflow;
        }

        public double getBinError(int bin) //<<<TASK1.3>>>
        {
            return Math.Sqrt(hist[bin]);
        }

        public long getIntegral() //<<<TASK1.4>>>
        {
            long sum = 0;
            for (int i = 0; i < SIZE; i++)
            {
                sum = sum + hist[i];
            }
            return sum;
        }

        public void print() //<<<TASK1.5>>>
        {   //Prints relavent histogram information to console
            for (int i = 0; i < SIZE; i++)
            {
                Console.WriteLine("Bin " + i + " contents is: " + hist[i]);
            }
            Console.WriteLine("Number of histogram fill attempts: " + getNfilled());
            Console.WriteLine("The sum of all the bins is: " + getIntegral());
            Console.WriteLine("The overflows are: " + getOverflow() + " and underflows: " + getUnderflow());
        }

        public void writeToDisk(String filename) 
        {
            StringBuilder filecontent = new StringBuilder();
            // Write the file as a comma seperated file (.csv) so it can be read it into EXCEL
            // first some general information about the histogram
            filecontent.AppendLine("Binlow , " + binlow);     // note the comma in the text here 
        filecontent.AppendLine("Binint , " + binsize); 
        filecontent.AppendLine("nbins  , " + SIZE);    
        filecontent.AppendLine("Trials , " + getNfilled());
        filecontent.AppendLine("Integral , " + getIntegral());
        filecontent.AppendLine("Overflows , " + getOverflow());
        filecontent.AppendLine("Underflows , " + getUnderflow());
        filecontent.AppendLine("bin# , bin_centre , counts , error");
    
        // now make a loop to write the contents of each bin to disk, one number at a time
        // together with the x-coordinate of the centre of each bin.
        for (int n = 0; n<SIZE; n++) 
        {
            // calculate the x coordinate of the centre of each bin
            double binCentre = binlow + binsize / 2 + n * binsize; 
                                                                   // comma separated values
        filecontent.AppendLine(n + "," + binCentre + "," + hist[n] + "," + getBinError(n)); 
        }

        File.AppendAllText(filename, filecontent.ToString());
        Console.WriteLine("Data written to disk in file " + filename +"\n\n");
        return;
    }

//----------------------------------------
public void fill(double value)
{
    if (value < binlow) { underflow++; }
    else if (value >= binhigh) { overflow++; }
    else
    {
        // add 1 to the correct bin
        int bin = (int)((value - binlow) / binsize);
        hist[bin]++;
    }
    nfilled++; //<<<TASK1.1>>>
}

//-------------------------------------
public int getContent(int nbin)
{
    // returns the contents on bin 'nbin' to the user
    return hist[nbin];
}
}

}

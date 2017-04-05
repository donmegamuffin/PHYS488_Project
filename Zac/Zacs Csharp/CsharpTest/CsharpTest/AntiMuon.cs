using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CsharpTest {
    /**
     * Creates a Muon with a limited lifetime and a 3-Vector position.
     * 
     * @author (Zachary Humphreys) 
     * @version (v0.2 29/03/2017)
     */
    public class AntiMuon {
        //Particle constants
        private static readonly double MASS = 105.6583745;            //MeV/c^2
        private static readonly double MEAN_LIFETIME = 2.1969811e-6;  //s
        //private static readonly double CHARGE = 1;                    //e
                                                                   //Particle variables
        private double momentum;        //MeV
        private double energy;          //MeV
        private double beta;
        private double gamma;
        private double relativisticLifetime;
        private double actualLifetime;
        Vector location;        //Position x,y,z in metres
                                //Flags runtime errors
        public double percentLeeway = 1;
        public bool bErrors = false;
        /**
         * CONSTRUCTOR
         * @param arg : write "energy" or "momentum" or use standard notation
         *              depending which is wanted for [EorP] input.
         * @param in_Vec : a world position (in metres) for the creation of the particle.
         * @paran inEorP : depending on what was written for [arg], input particle energy or momentum in MeV.
         */
        public AntiMuon(string arg, Vector location, double EorP, int n) //TODO fix this n
        {
            // initialise instance variables
            this.location = location;
            if (arg[0] == 'e' || arg[0] == 'E')
            {
                setEnergy(EorP);
            }
            else if
           ( //allows input of the word momentum OR the standard notation
           arg[0] == 'p' ||
           arg[0] == 'P' ||
           arg[0] == 'm' ||
           arg[0] == 'M'
           )
            {
                setMomentum(EorP);
            }
            else
            {   //Arg error check
                Error.printError(Error.ErrorList.MUON_EORP_UNSPECIFIED);
                bErrors = true;
                return; //Because nothing specified, doesn't check anything else and returns
            }
            setLifetime(n);

            //Values sanity check
            if (beta >= 1)
            {
                Error.printError(Error.ErrorList.MUON_SPEED_VIOLATION);
                bErrors = true;
                return;
            }
            if (MASS > energy)
            {
                Error.printError(Error.ErrorList.MUON_E_M_IMBALANCE);
                bErrors = true;
                return;
            }
            if (momentum > energy)
            {
                Error.printError(Error.ErrorList.MUON_E_P_IMBALANCE);
                bErrors = true;
                return;
            }
            if (!VectorMaths.FuzzyEqual(percentLeeway, Math.Sqrt(Math.Pow(energy, 2) - Math.Pow(momentum, 2)), MASS))
            {
                Error.printError(Error.ErrorList.MUON_M_E_EQUIVALENCE_FAIL);
                Console.WriteLine("Calculated: " + Math.Sqrt(Math.Pow(energy, 2) + Math.Pow(momentum, 2)) +
                " MeV. Given: " + MASS);
                bErrors = true;
                return;
            }
        }

        //-----------------GETTERS-----------------
        public double getMomentum()
        {
            return momentum;
        }
        public double getEnergy()
        {
            return energy;
        }
        public double getBeta()
        {
            return beta;
        }
        public double getGamma()
        {
            return gamma;
        }
        public double getActualLifetime()
        {
            return actualLifetime;
        }
        //-----------------SETTERS-----------------
        public void setMomentum(double momentum)
        {
            this.momentum = momentum;
            energy = energy = Math.Pow((Math.Pow(momentum, 2) + Math.Pow(MASS, 2)), (0.5));
            gamma = energy / MASS;
            beta = Math.Pow((1 - Math.Pow(gamma, -2)), 0.5);
        }
        public void setEnergy(double energy)
        {
            this.energy = energy;
            momentum = Math.Pow((Math.Pow(energy, 2) - Math.Pow(MASS, 2)), (0.5));
            gamma = energy / MASS;
            beta = Math.Pow((1 - Math.Pow(gamma, -2)), 0.5);
        }
        private void setLifetime(int n)
        {
            relativisticLifetime = gamma * MEAN_LIFETIME;
            Random randGen = new Random(n);
            actualLifetime = (-relativisticLifetime * Math.Log(randGen.NextDouble()));
        }
        //-----------------METHODS-----------------
        /**
         * An example of a method - replace this comment with your own
         * 
         * @param  y   a sample parameter for a method
         * @return     the sum of x and y 
         */
        public int sampleMethod()
        {
            return 0;
        }
    }


}

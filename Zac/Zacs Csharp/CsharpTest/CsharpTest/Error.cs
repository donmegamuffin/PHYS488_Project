using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CsharpTest {
    public class Error {
        public enum ErrorList {
            //TESTER
            [System.ComponentModel.Description("This is a test error.")]
            TEST_ERROR = 0,
            //MUON RELATED
            MUON_EORP_UNSPECIFIED = 10, //"A muon's Energy or Momentum input is unspecified."
            MUON_SPEED_VIOLATION = 11, //"A muon is travelling faster than the speed of light."),
            MUON_E_P_IMBALANCE = 12, //"A muon's momentum is higher than its energy."),
            MUON_E_M_IMBALANCE = 13, //"A muon's mass is higher than its energy."),
            MUON_M_E_EQUIVALENCE_FAIL = 14, //"A muon's mass-energy equivalence is incorrect. Einstein would be sad."),

            //POSITRON RELATED
            POSITRON_EORP_UNSPECIFIED = 20,//"A positron's Energy or Momentum input is unspecified."),
            POSITRON_SPEED_VIOLATION = 21, //"A positron is travelling faster than the speed of light."),
            POSITRON_E_P_IMBALANCE = 22, //"A positron's momentum is higher than its energy."),
            POSITRON_E_M_IMBALANCE = 23, //"A positron's mass is higher than its energy."),
            POSITRON_M_E_EQUIVALENCE_FAIL = 24, //"A positron's mass-energy equivalence is incorrect. Einstein would be sad."),

            //MATHS RELATED
            MATHS_CP_BAD_LENGTH = 30, //"A Cross Product argument vector is not of length 3. OUTPUT IS INVALID.")

            //MISC
            NOT_FOUND = 404, //"Error not found.");
        }
        /// <summary>
        /// Console Writer: 
        /// Prints to console an error message determined by the Error-code argument
        /// </summary>
        /// <param name="ErrorCode"> Input the code of error wanted to be displayed to console.</param>
        public static void printError(int ErrorCode)
        {
            switch ((ErrorList)ErrorCode)
            {
                //TEST
                case ErrorList.TEST_ERROR:
                    Console.WriteLine("Error " + ErrorCode + ": This is a test error.");
                    break;
                //MUON RELATED        
                case ErrorList.MUON_EORP_UNSPECIFIED:
                    Console.WriteLine("Error " + ErrorCode + ": A muon's Energy or Momentum input is unspecified.");
                    break;
                case ErrorList.MUON_SPEED_VIOLATION:
                    Console.WriteLine("Error " + ErrorCode + ": A muon is travelling faster than the speed of light.");
                    break;
                case ErrorList.MUON_E_P_IMBALANCE:
                    Console.WriteLine("Error " + ErrorCode + ": A muon's momentum is higher than its energy.");
                    break;
                case ErrorList.MUON_E_M_IMBALANCE:
                    Console.WriteLine("Error " + ErrorCode + ": A muon's mass is higher than its energy.");
                    break;
                case ErrorList.MUON_M_E_EQUIVALENCE_FAIL:
                    Console.WriteLine("Error " + ErrorCode + ": A muon's mass-energy equivalence is incorrect. Einstein would be sad.");
                    break;

                //POSITRON RELATED
                case ErrorList.POSITRON_EORP_UNSPECIFIED:
                    Console.WriteLine("Error " + ErrorCode + ": A positron's Energy or Momentum input is unspecified.");
                    break;
                case ErrorList.POSITRON_SPEED_VIOLATION:
                    Console.WriteLine("Error " + ErrorCode + ": A positron is travelling faster than the speed of light.");
                    break;
                case ErrorList.POSITRON_E_P_IMBALANCE:
                    Console.WriteLine("Error " + ErrorCode + ": A positron's momentum is higher than its energy.");
                    break;
                case ErrorList.POSITRON_E_M_IMBALANCE:
                    Console.WriteLine("Error " + ErrorCode + ": A positron's mass is higher than its energy.");
                    break;
                case ErrorList.POSITRON_M_E_EQUIVALENCE_FAIL:
                    Console.WriteLine("Error " + ErrorCode + ": A positron's mass-energy equivalence is incorrect. Einstein would be sad.");
                    break;

                //MATHS RELATED
                case ErrorList.MATHS_CP_BAD_LENGTH:
                    Console.WriteLine("Error " + ErrorCode + ": A Cross Product argument vector is not of length 3. OUTPUT IS INVALID.");
                    break;
                //MISC
                case ErrorList.NOT_FOUND:
                    Console.WriteLine("Error " + ErrorCode + ": Error not found.");
                    break;
                default:
                    Console.WriteLine("Error " + ErrorCode + ": An unspecified (or incorrectly inputted error-code) error.");
                    break;
            }
        }
        /// <summary>
        /// Console Writer: 
        /// Prints to console an error message determined by the Error-code argument
        /// </summary>
        /// <param name="Error"> Input the Error wanted to be displayed to console.</param>
        public static void printError(Error.ErrorList Error) { printError((int)Error); }
    }
}

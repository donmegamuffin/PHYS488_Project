using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CsharpTest 
{
    /// <summary>
    /// DATA TYPE: Creates a 3D vector with double precision.
    /// </summary>
    public class Vector {
        private double X;
        private double Y;
        private double Z;
        /// <summary>
        /// Constructor - Default: creates a vector with default position of (0,0,0).
        /// </summary>
        public Vector()
        {
            X = 0;
            Y = 0;
            Z = 0;
        }
        /// <summary>
        /// Constructor - Array: creates a vector with positions determined by argument array.
        /// </summary>
        /// <param name="xyz"> A 1D, 3-value double array giving X Y Z positions</param>
        public Vector(double[] xyz)
        {
            X = xyz[0];
            Y = xyz[1];
            Z = xyz[2];
        }

        /// <summary>
        /// Constructor - Separate: creates a vector with X,Y,Z positions determined separately.
        /// </summary>
        /// <param name="X"> The X position of the Vector. </param>
        /// <param name="Y"> The Y position of the Vector.</param>
        /// <param name="Z"> The Z position of the Vector.</param>
        public Vector(double X, double Y, double Z)
        {
            this.X = X;
            this.Y = Y;
            this.Z = Z;
        }

        /*SETTERS*/

        /// <summary>
        /// Setter - Set the X value of the Vector.
        /// </summary>
        /// <param name="X"> Input the new X value for the Vector </param>
        public void setX(double X)
        {
            this.X = X;
            return;
        }
        /// <summary>
        /// Setter - Set the Y value of the Vector.
        /// </summary>
        /// <param name="Y"> Input the new Y value for the Vector </param>
        public void setY(double Y)
        {
            this.Y = Y;
            return;
        }
        /// <summary>
        /// Setter - Set the Z value of the Vector.
        /// </summary>
        /// <param name="Z"> Input the new Z value for the Vector </param>
        public void setZ(double Z)
        {
            this.Z = Z;
            return;
        }
        /*GETTERS*/
        public double getX() { return X; }
        public double getY() { return Y; }
        public double getZ() { return Z; }
        /*CONVERTERS*/
        double[] toDoubleArray()
        {
            double[] OutArray = new double[3];
            OutArray[0] = getX();
            OutArray[1] = getY();
            OutArray[2] = getZ();
            return OutArray;
        }
    }
    public class Rotation {
        private double Roll;
        private double Pitch;
        private double Yaw;
        /*CONSTRUCTORS*/
        /**THREE DOUBLE CONSTRUCTOR:
         * - Creates a Rotation with Roll, Pitch, Yaw values 
         * from 0.0 to 360.0 degree doubles.
         * @param in_Roll - A double giving the Roll angle in degrees
         * @param in_Pitch - A double giving the Pitch angle in degrees
         * @param in_Yaw - A double giving the Yaw angle in degrees
         */
        public Rotation(double in_Roll, double in_Pitch, double in_Yaw)
        {
            Roll = (in_Roll % 360.0);
            Pitch = (in_Pitch % 360.0);
            Yaw = (in_Yaw % 360.0);
        }
        /**ARRAY CONSTRUCTOR:
         * - Creates a Rotation with Roll, Pitch, Yaw values 
         * from 0.0 to 360.0 degree doubles.
         * @param xyz - A double array giving the Roll, Pitch, Yaw 
         * as each value of the array in Degrees
         */
        public Rotation(double[] xyz)
        {
            Roll = (xyz[0] % 360.0);
            Pitch = (xyz[1] % 360.0);
            Yaw = (xyz[2] % 360.0);
        }
        /**DEFAULT CONSTRUCTOR:
         * Creates a Rotation with all values set to 0.0 degrees.
         */
        public Rotation()
        {
            Roll = 0.0;
            Pitch = 0.0;
            Yaw = 0.0;
        }
        /*SETTERS*/
        public void setRoll(double in_roll)
        {
            Roll = in_roll;
            return;
        }
        public void setPitch(double in_Pitch)
        {
            Pitch = in_Pitch;
            return;
        }
        public void setYaw(double in_Yaw)
        {
            Yaw = in_Yaw;
            return;
        }
        /*GETTERS*/
        public double getRoll() { return Roll; }
        public double getPitch() { return Pitch; }
        public double getYaw() { return Yaw; }

        public double getRadRoll() { return Roll * Math.PI / 180; }
        public double getRadPitch() { return Pitch * Math.PI / 180; }
        public double getRadYaw() { return Yaw * Math.PI / 180; }
        /*CONVERTERS*/
        public double[] toDoubleArray()
        {
            double[] OutArray = new double[3];
            OutArray[0] = getRoll();
            OutArray[1] = getPitch();
            OutArray[2] = getYaw();
            return OutArray;
        }
    }
    public class Transform {
        Vector Location;
        Rotation Orientation;
        Vector Scale;
        Transform(Vector L, Rotation O, Vector S)
        {
            Location = L;
            Orientation = O;
            Scale = S;
        }
        Transform()
        {
            double[] empty = new double[3];
            Location = new Vector(empty);
            Orientation = new Rotation(empty);
            Scale = new Vector(empty);
        }
        /*SETTERS*/
        void setLocation(Vector in_Vector)
        {
            Location = in_Vector;
            return;
        }
        void setOrientation(Rotation in_Rotation)
        {
            Orientation = in_Rotation;
            return;
        }
        void setScale(Vector in_Vector)
        {
            Scale = in_Vector;
            return;
        }
        /*GETTERS*/
        Vector getLocation() { return Location; }
        Rotation getOrientation() { return Orientation; }
        Vector getScale() { return Scale; }
    }
    public class FourVector {
        private double T;
        private double X;
        private double Y;
        private double Z;
        /*CONSTRUCTORS*/
        FourVector()
        {   //Creates a vector filled with 0 values
            T = 0;
            X = 0;
            Y = 0;
            Z = 0;
        }
        FourVector(double[] in_fourVec)
        {
            T = in_fourVec[0];
            X = in_fourVec[1];
            Y = in_fourVec[2];
            Z = in_fourVec[3];
        }
        FourVector(double in_T, double in_X, double in_Y, double in_Z)
        {
            T = in_T;
            X = in_X;
            Y = in_Y;
            Z = in_Z;
        }
        /*SETTERS*/
        void setT(double in_T)
        {
            T = in_T;
            return;
        }
        void setX(double in_X)
        {
            X = in_X;
            return;
        }
        void setY(double in_Y)
        {
            Y = in_Y;
            return;
        }
        void setZ(double in_Z)
        {
            Z = in_Z;
            return;
        }
        /*GETTERS*/
        double getT()
        {
            return T;
        }
        double getX()
        {
            return X;
        }
        double getY()
        {
            return Y;
        }
        double getZ()
        {
            return Z;
        }
        /*CONVERTERS*/
        Vector toVector()
        {
            Vector OutVector = new Vector();
            OutVector.setX(getX());
            OutVector.setY(getY());
            OutVector.setZ(getZ());
            return OutVector;
        }
        double[] toDoubleArray()
        {
            double[] OutArray = new double[4];
            OutArray[0] = getT();
            OutArray[1] = getX();
            OutArray[2] = getY();
            OutArray[3] = getZ();
            return OutArray;
        }
    }
}

class Transform
{
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
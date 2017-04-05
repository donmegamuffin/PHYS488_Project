class FunctionalityTester
{
    public static void main()
    {
        Vector TestVec = new Vector();
        FourVector Test4Vec = new FourVector();
        Rotation TestRot = new Rotation();
        Transform TestTran = new Transform(TestVec,TestRot,TestVec);
        
        System.out.println("Success :"+ VectorMaths.FuzzyEqual(100,100,100));
        System.out.println("x too big :"+ VectorMaths.FuzzyEqual(1,102,100));
        System.out.println("x too small :"+ VectorMaths.FuzzyEqual(1,98,100));
        System.out.println("x ~ y :"+ VectorMaths.FuzzyEqual(1,99,100));
        System.out.println("y too big :"+ VectorMaths.FuzzyEqual(1,100,102));
        System.out.println("y too small :"+ VectorMaths.FuzzyEqual(1,100,98));
        //TEST MUON AND ERROR FUNCTIONALITY
        

        String[] ArgArray = {"e","E","p","P","t","m","M","g"};
        Positron[] PosiArray = new Positron[ArgArray.length];
        for(int n=0; n<ArgArray.length; n++)
        {   //Creates positrons and checks for errors
            PosiArray[n] = new Positron(ArgArray[n],TestVec,106);
            if(PosiArray[n].bErrors == true)
            {
                System.out.println(System.identityHashCode(PosiArray[n])+" has been deleted.");
                PosiArray[n] = null;
            }
        }
        AntiMuon[] MuonArray = new AntiMuon[ArgArray.length];
        for(int n=0; n<ArgArray.length; n++)
        {   //Creates positrons and checks for errors
            MuonArray[n] = new AntiMuon(ArgArray[n],TestVec,10);
            if(MuonArray[n].bErrors == true)
            {
                System.out.println(System.identityHashCode(MuonArray[n])+" has been deleted.");
                MuonArray[n] = null;
            }
        }
        
        for(int n=0; n<MuonArray.length; n++)
        {
            if(MuonArray[n]!=null)
            {
                System.out.printf("1");
            }else
            {
                System.out.printf("0");            
            }
        }
        System.out.println("\t"+MuonArray.length);
        
        double[] x = {1,2};
        double[] y = {1,2,3};
        VectorMaths.CrossProduct(x,y);
        VectorMaths.CrossProduct(y,y);
        
        System.out.println(Error.TEST_ERROR.toString());
    }
}
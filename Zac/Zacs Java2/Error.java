
/**
 * This contains all the known errors and the toString() override reporter method
 * 
 * @author (Zachary Humphreys)
 * @version (v0.1 29/03/2017)
 */
public enum Error
{   //TESTER
    TEST_ERROR(0, "This is a test error."),
    //MUON RELATED
    MUON_EORP_UNSPECIFIED(10,"A muon's Energy or Momentum input is unspecified."),
    MUON_SPEED_VIOLATION(11, "A muon is travelling faster than the speed of light."),
    MUON_E_P_IMBALANCE(12, "A muon's momentum is higher than its energy."),
    MUON_E_M_IMBALANCE(13, "A muon's mass is higher than its energy."),
    MUON_M_E_EQUIVALENCE_FAIL(14, "A muon's mass-energy equivalence is incorrect. Einstein would be sad."),
   
    //POSITRON RELATED
    POSITRON_EORP_UNSPECIFIED(20,"A positron's Energy or Momentum input is unspecified."),
    POSITRON_SPEED_VIOLATION(21, "A positron is travelling faster than the speed of light."),
    POSITRON_E_P_IMBALANCE(22, "A positron's momentum is higher than its energy."),
    POSITRON_E_M_IMBALANCE(23, "A positron's mass is higher than its energy."),
    POSITRON_M_E_EQUIVALENCE_FAIL(24, "A positron's mass-energy equivalence is incorrect. Einstein would be sad."),
    
    //MATHS RELATED
    MATHS_CP_BAD_LENGTH(30, "A Cross Product argument vector is not of length 3. OUTPUT IS INVALID."),
    
    //MISC
    NOT_FOUND(404, "Error not found.");
    private final int code;
    private final String description;
    private Error(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
    public String getDescritpion()
    {
        return description;
    }
    public int getCode()
    {
        return code;
    }
    @Override
    public String toString()
    {
        return "ERROR " + code + ": " + description;
    }
}

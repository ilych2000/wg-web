package ru.wg.web.pojo;

public class CalculationCriteria extends Criteria {

    private int ID;

    private int CALCULATION_ID;

    private String K1;

    private String K2;

    private String K3;

    private String ACTUAL_VALUE;

    private String FI;

    /**
     * @return the iD
     */
    public int getID() {
        return ID;
    }

    /**
     * @param iD the iD to set
     */
    public void setID(int iD) {
        ID = iD;
    }

    /**
     * @return the cALCULATION_ID
     */
    public int getCALCULATION_ID() {
        return CALCULATION_ID;
    }

    /**
     * @param cALCULATION_ID the cALCULATION_ID to set
     */
    public void setCALCULATION_ID(int cALCULATION_ID) {
        CALCULATION_ID = cALCULATION_ID;
    }

    /**
     * @return the k1
     */
    public String getK1() {
        return K1;
    }

    /**
     * @param k1 the k1 to set
     */
    public void setK1(String k1) {
        K1 = k1;
    }

    /**
     * @return the k2
     */
    public String getK2() {
        return K2;
    }

    /**
     * @param k2 the k2 to set
     */
    public void setK2(String k2) {
        K2 = k2;
    }

    /**
     * @return the k3
     */
    public String getK3() {
        return K3;
    }

    /**
     * @param k3 the k3 to set
     */
    public void setK3(String k3) {
        K3 = k3;
    }

    /**
     * @return the aCTUAL_VALUE
     */
    public String getACTUAL_VALUE() {
        return ACTUAL_VALUE;
    }

    /**
     * @param aCTUAL_VALUE the aCTUAL_VALUE to set
     */
    public void setACTUAL_VALUE(String aCTUAL_VALUE) {
        ACTUAL_VALUE = aCTUAL_VALUE;
    }

    /**
     * @return the fI
     */
    public String getFI() {
        return FI;
    }

    /**
     * @param fI the fI to set
     */
    public void setFI(String fI) {
        FI = fI;
    }
}

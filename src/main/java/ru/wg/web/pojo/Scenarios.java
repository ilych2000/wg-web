package ru.wg.web.pojo;

public class Scenarios {

    private int ID;

    private int CALCULATION_ID;

    private int CHECKED;

    private int OBJECT_TYPE_ID;

    private int SCENARIOS_GROUP_ID;

    private int RANK;

    private String NPP;

    private String NAME;

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
     * @return the sCENARIOS_GROUP_ID
     */
    public int getSCENARIOS_GROUP_ID() {
        return SCENARIOS_GROUP_ID;
    }

    /**
     * @param sCENARIOS_GROUP_ID the sCENARIOS_GROUP_ID to set
     */
    public void setSCENARIOS_GROUP_ID(int sCENARIOS_GROUP_ID) {
        SCENARIOS_GROUP_ID = sCENARIOS_GROUP_ID;
    }

    /**
     * @return the rANK
     */
    public int getRANK() {
        return RANK;
    }

    /**
     * @param rANK the rANK to set
     */
    public void setRANK(int rANK) {
        RANK = rANK;
    }

    /**
     * @return the nPP
     */
    public String getNPP() {
        return NPP;
    }

    /**
     * @param nPP the nPP to set
     */
    public void setNPP(String nPP) {
        NPP = nPP;
    }

    /**
     * @return the nAME
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * @param nAME the nAME to set
     */
    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public int getOBJECT_TYPE_ID() {
        return OBJECT_TYPE_ID;
    }

    public void setOBJECT_TYPE_ID(int oBJECT_TYPE_ID) {
        OBJECT_TYPE_ID = oBJECT_TYPE_ID;
    }

    /**
     * @return the cHECKED
     */
    public int getCHECKED() {
        return CHECKED;
    }

    /**
     * @param cHECKED the cHECKED to set
     */
    public void setCHECKED(int cHECKED) {
        CHECKED = cHECKED;
    }

}

/**
 *
 */
package ru.wg.web.pojo;

/**
 * @author Илья
 */
public class Criteria {

    private int CRITERIA_ID;

    private int SAFETY_GROUP_ID;

    private String NAME;

    private String SIGN;

    private String NPP;

    /**
     * @return the sAFETY_GROUP_ID
     */
    public int getSAFETY_GROUP_ID() {
        return SAFETY_GROUP_ID;
    }

    /**
     * @param sAFETY_GROUP_ID the sAFETY_GROUP_ID to set
     */
    public void setSAFETY_GROUP_ID(int sAFETY_GROUP_ID) {
        SAFETY_GROUP_ID = sAFETY_GROUP_ID;
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

    /**
     * @return the sIGN
     */
    public String getSIGN() {
        return SIGN;
    }

    /**
     * @param sIGN the sIGN to set
     */
    public void setSIGN(String sIGN) {
        SIGN = sIGN;
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
     * @return the cRITERIA_ID
     */
    public int getCRITERIA_ID() {
        return CRITERIA_ID;
    }

    /**
     * @param cRITERIA_ID the cRITERIA_ID to set
     */
    public void setCRITERIA_ID(int cRITERIA_ID) {
        CRITERIA_ID = cRITERIA_ID;
    }

}

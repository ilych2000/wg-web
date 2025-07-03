/**
 * 
 */
package ru.wg.web.pojo;

/**
 * @author Илья
 *
 */
public class CalculationTable extends Criteria {

	private int ID;
	private int CALCULATION_ID;
	private int SCENARIOS_ID;

	private String SCENARIOS_NPP;
	private float FI;
	private int RANG;
	private float KZNI;
	private float UTCHFI;
	private String G;
	private String D;
	private String E1;
	private String E2;
	private String E3;
	private int ENABLE;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the sCENARIOS_NPP
	 */
	public String getSCENARIOS_NPP() {
		return SCENARIOS_NPP;
	}

	/**
	 * @param sCENARIOS_NPP
	 *            the sCENARIOS_NPP to set
	 */
	public void setSCENARIOS_NPP(String sCENARIOS_NPP) {
		SCENARIOS_NPP = sCENARIOS_NPP;
	}

	/**
	 * @return the fFI
	 */
	public float getFI() {
		return FI;
	}

	/**
	 * @param fFI
	 *            the fFI to set
	 */
	public void setFI(float fFI) {
		FI = fFI;
	}

	/**
	 * @return the rANG
	 */
	public int getRANG() {
		return RANG;
	}

	/**
	 * @param rANG
	 *            the rANG to set
	 */
	public void setRANG(int rANG) {
		RANG = rANG;
	}

	/**
	 * @return the kZNI
	 */
	public float getKZNI() {
		return KZNI;
	}

	/**
	 * @param kZNI
	 *            the kZNI to set
	 */
	public void setKZNI(float kZNI) {
		KZNI = kZNI;
	}

	/**
	 * @return the uTCHFI
	 */
	public float getUTCHFI() {
		return UTCHFI;
	}

	/**
	 * @param uTCHFI
	 *            the uTCHFI to set
	 */
	public void setUTCHFI(float uTCHFI) {
		UTCHFI = uTCHFI;
	}

	/**
	 * @return the g
	 */
	public String getG() {
		return G;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(String g) {
		G = g;
	}

	/**
	 * @return the d
	 */
	public String getD() {
		return D;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public void setD(String d) {
		D = d;
	}

	/**
	 * @return the e1
	 */
	public String getE1() {
		return E1;
	}

	/**
	 * @param e1
	 *            the e1 to set
	 */
	public void setE1(String e1) {
		E1 = e1;
	}

	/**
	 * @return the e2
	 */
	public String getE2() {
		return E2;
	}

	/**
	 * @param e2
	 *            the e2 to set
	 */
	public void setE2(String e2) {
		E2 = e2;
	}

	/**
	 * @return the e3
	 */
	public String getE3() {
		return E3;
	}

	/**
	 * @param e3
	 *            the e3 to set
	 */
	public void setE3(String e3) {
		E3 = e3;
	}

	/**
	 * @return the sCENARIOS_ID
	 */
	public int getSCENARIOS_ID() {
		return SCENARIOS_ID;
	}

	/**
	 * @param sCENARIOS_ID
	 *            the sCENARIOS_ID to set
	 */
	public void setSCENARIOS_ID(int sCENARIOS_ID) {
		SCENARIOS_ID = sCENARIOS_ID;
	}

	/**
	 * @return the eNABLE
	 */
	public int getENABLE() {
		return ENABLE;
	}

	/**
	 * @param eNABLE
	 *            the eNABLE to set
	 */
	public void setENABLE(int eNABLE) {
		ENABLE = eNABLE;
	}

	/**
	 * @return the cALCULATION_ID
	 */
	public int getCALCULATION_ID() {
		return CALCULATION_ID;
	}

	/**
	 * @param cALCULATION_ID
	 *            the cALCULATION_ID to set
	 */
	public void setCALCULATION_ID(int cALCULATION_ID) {
		CALCULATION_ID = cALCULATION_ID;
	}

    @Override
    public String toString() {
        return "CalculationTable [ID=" + ID + ",\n\t CALCULATION_ID="
                + CALCULATION_ID + ",\n\t SCENARIOS_ID=" + SCENARIOS_ID
                + ",\n\t SCENARIOS_NPP=" + SCENARIOS_NPP + ",\n\t FI=" + FI
                + ",\n\t RANG=" + RANG + ",\n\t KZNI=" + KZNI + ",\n\t UTCHFI="
                + UTCHFI + ",\n\t G=" + G + ",\n\t D=" + D + ",\n\t E1=" + E1
                + ",\n\t E2=" + E2 + ",\n\t E3=" + E3 + ",\n\t ENABLE=" + ENABLE
                + "]";
    }



}

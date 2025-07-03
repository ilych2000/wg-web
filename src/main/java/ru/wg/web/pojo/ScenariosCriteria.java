package ru.wg.web.pojo;

public class ScenariosCriteria {
	private int ID;
	private int CALCULATION_ID;
	private int CRITERIA_ID;
	private int SCENARIOS_ID;
	private int CHECKING;
	private Integer RANK;

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

	/**
	 * @return the cRITERIA_ID
	 */
	public int getCRITERIA_ID() {
		return CRITERIA_ID;
	}

	/**
	 * @param cRITERIA_ID
	 *            the cRITERIA_ID to set
	 */
	public void setCRITERIA_ID(int cRITERIA_ID) {
		CRITERIA_ID = cRITERIA_ID;
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
	 * @return the cHECKING
	 */
	public int getCHECKING() {
		return CHECKING;
	}

	/**
	 * @param cHECKING
	 *            the cHECKING to set
	 */
	public void setCHECKING(int cHECKING) {
		CHECKING = cHECKING;
	}

	/**
	 * @return the rANK
	 */
	public Integer getRANK() {
		return RANK;
	}

	/**
	 * @param rANK the rANK to set
	 */
	public void setRANK(Integer rANK) {
		RANK = rANK;
	}

}

package ru.wg.web.pojo;

public class Calculation {
	private int ID;
	private int OBJECT_ID;
	private String NAME;
	private String OBJECT_NAME;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public int getOBJECT_ID() {
		return OBJECT_ID;
	}

	public void setOBJECT_ID(int oBJECT_ID) {
		OBJECT_ID = oBJECT_ID;
	}

	/**
	 * @return the oBJECT_NAME
	 */
	public String getOBJECT_NAME() {
		return OBJECT_NAME;
	}

	/**
	 * @param oBJECT_NAME
	 *            the oBJECT_NAME to set
	 */
	public void setOBJECT_NAME(String oBJECT_NAME) {
		OBJECT_NAME = oBJECT_NAME;
	}

}

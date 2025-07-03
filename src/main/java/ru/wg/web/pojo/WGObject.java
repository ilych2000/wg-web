package ru.wg.web.pojo;

public class WGObject {
	private int ID;
	private String NAME;
	private int OBJECT_TYPE_ID;
	private String OBJECT_TYPE_NAME;

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

	/**
	 * @return the oBJECT_TYPE_ID
	 */
	public int getOBJECT_TYPE_ID() {
		return OBJECT_TYPE_ID;
	}

	/**
	 * @param oBJECT_TYPE_ID
	 *            the oBJECT_TYPE_ID to set
	 */
	public void setOBJECT_TYPE_ID(int oBJECT_TYPE_ID) {
		OBJECT_TYPE_ID = oBJECT_TYPE_ID;
	}

	/**
	 * @return the oBJECT_TYPE_NAME
	 */
	public String getOBJECT_TYPE_NAME() {
		return OBJECT_TYPE_NAME;
	}

	/**
	 * @param oBJECT_TYPE_NAME the oBJECT_TYPE_NAME to set
	 */
	public void setOBJECT_TYPE_NAME(String oBJECT_TYPE_NAME) {
		OBJECT_TYPE_NAME = oBJECT_TYPE_NAME;
	}

}

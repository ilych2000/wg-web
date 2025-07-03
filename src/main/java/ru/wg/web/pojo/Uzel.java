package ru.wg.web.pojo;

import java.util.List;

public class Uzel{
	private int ID;
	private String NAME;
	private List<WGObject> OBJECTS;

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

	public List<WGObject> getOBJECTS() {
		return OBJECTS;
	}

	public void setOBJECTS(List<WGObject> oBJECTS) {
		OBJECTS = oBJECTS;
	}

}

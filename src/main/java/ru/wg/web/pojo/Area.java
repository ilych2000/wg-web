package ru.wg.web.pojo;

import java.util.List;

public class Area {

    private int ID;

    private String NAME;

    private List<Uzel> UZELS;

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

    public List<Uzel> getUZELS() {
        return UZELS;
    }

    public void setUZELS(List<Uzel> uZELS) {
        UZELS = uZELS;
    }

}

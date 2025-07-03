package ru.wg.web.pojo;

public class ScenarioGroup {

    private int ID;

    private String NAME;

    /**
     * Возвращает значение поля {@link #ID iD}
     *
     * @return значение поля
     */
    public int getID() {
        return ID;
    }

    /**
     * Устанавливает новое значение полю {@link #ID iD}
     *
     * @param iD значение, устанавливаемое полю
     */
    public void setID(int iD) {
        ID = iD;
    }

    /**
     * Возвращает значение поля {@link #NAME nAME}
     *
     * @return значение поля
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * Устанавливает новое значение полю {@link #NAME nAME}
     *
     * @param nAME значение, устанавливаемое полю
     */
    public void setNAME(String nAME) {
        NAME = nAME;
    }

}

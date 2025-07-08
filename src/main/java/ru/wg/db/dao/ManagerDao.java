package ru.wg.db.dao;

import java.util.List;
import java.util.Map;

import ru.wg.db.driver.DataSourceInt;
import ru.wg.web.pojo.AreaControl;
import ru.wg.web.pojo.Calculation;
import ru.wg.web.pojo.CalculationCriteria;
import ru.wg.web.pojo.CalculationTable;
import ru.wg.web.pojo.ScenarioGroup;
import ru.wg.web.pojo.Scenarios;
import ru.wg.web.pojo.ScenariosCriteria;

public interface ManagerDao {

    /**
     * Получить список расчетов по идентификатору объекта. Если идентификатор <code>null</code>, то
     * возвращается полный список.
     *
     * @param aID идентификатор объекта
     * @return список {@link Calculation}
     * @throws Exception
     */
    public List<Calculation> getCalculateListByObjectID(Integer aID) throws Exception;

    /**
     * Получить расчет по идентификатору
     *
     * @param aID идентификатор расчета
     * @return {@link Calculation}
     * @throws Exception
     */
    public Calculation getCalculationByID(Integer aID) throws Exception;

    /**
     * Получить сценарии по идентификатору расчета.
     *
     * @param aID идентификатор расчета
     * @return {@link Scenarios}
     * @throws Exception
     */
    public List<Scenarios> getScenariosListByCalculateID(Integer aID) throws Exception;

    /**
     * Получить критерии по идентификатору расчета.
     *
     * @param aID идентификатор расчета
     * @return {@link CalculationCriteria}
     * @throws Exception
     */
    public List<CalculationCriteria> getCriteriaListByCalculateID(Integer aID) throws Exception;

    /**
     * Получить подбор критериев по идентификатору расчета.
     *
     * @param aID идентификатор расчета
     * @return {@link ScenariosCriteria}
     * @throws Exception
     */
    public Map<String, Integer> getScenariosCriteriaListByCalculateID(Integer aID) throws Exception;

    /**
     * @param aCalculateID
     * @param aScenarioID
     * @param aCriteriaID
     * @param aCheckValue
     * @throws Exception
     */
    public void setScenariosCriteriaCheck(Integer aCalculateID, Integer aScenarioID,
            Integer aCriteriaID, Integer aCheckValue) throws Exception;

    /**
     * @param aDataTable
     * @param aDataField
     * @param aDataValue
     * @param aDataId
     * @throws Exception
     */
    public void updateFieldValue(String aDataTable, String aDataField, String aDataValue,
            String aDataId) throws Exception;

    /**
     * Установить DataSource БД
     *
     * @param DS
     */
    public void setLocalDS(DataSourceInt DS);

    /**
     * Получить список подразделений объектов.
     *
     * @return list AreaControl
     * @throws Exception
     */
    public abstract List<AreaControl> getControlList() throws Exception;

    /**
     * Получить список видов сценария.
     *
     * @return list ScenarioGroup
     * @throws Exception
     */
    public abstract List<ScenarioGroup> getScenarioGroupList() throws Exception;

    /**
     * Добавить или удалить scenarioID в списке отмеченых сценариев расчета
     *
     * @param calcID
     * @param scenarioID
     * @param check
     * @throws Exception
     */
    public abstract void setCalculationScenariosIdsCheck(Integer calcID, Integer scenarioID,
            Integer check) throws Exception;

    /**
     * Получить расчетную таблицу по идентификатору объекта.
     *
     * @param aID идентификатор объекта
     * @param aEnable получить только выбранные записи расчета
     * @return список {@link CalculationTable}
     * @throws Exception
     */
    public List<CalculationTable> getCalculateTableListByID(Integer aID, boolean aEnable)
            throws Exception;

    /**
     * Получить сгенерированную расчетную таблицу по идентификатору объекта.
     *
     * @param aID идентификатор объекта
     * @return список {@link CalculationTable}
     * @throws Exception
     */
    public List<CalculationTable> getGeneratedCalculateTableListByID(Integer aID) throws Exception;

    /**
     * Установить доступность записи в расчетной таблице по идентификатору объекта
     *
     * @param aID идентификатор объекта
     * @param aEnable доступность записи
     * @throws Exception
     */
    public void setCalculateTableEnableByID(int aID, boolean aEnable) throws Exception;

    /**
     * Создать запись в расчетной таблице
     *
     * @param aCt
     * @throws Exception
     */
    public void createCalculateTable(CalculationTable aCt) throws Exception;

    /**
     * Удалить записи в расчетной таблице по идентификатору объекта
     *
     * @param aID идентификатор объекта
     * @throws Exception
     */
    public void deleteCalculateTable(int aID) throws Exception;

    /**
     * Обновление данных расчета
     *
     * @param calculateTable таблица с данными
     * @throws Exception
     */
    public void updateCalculateTableID(List<CalculationTable> calculateTable) throws Exception;
}

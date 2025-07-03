package ru.wg.db.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.wg.db.dao.ManagerDao;
import ru.wg.db.driver.DataSourceInt;
import ru.wg.web.pojo.*;

public class SQLiteManagerDaoJdbc implements ManagerDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(SQLiteManagerDaoJdbc.class);

	private DataSourceInt localDS;

	/**
	 * Номер контекста в списке БД.
	 */
	private static final int DB_CONTEXT = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLocalDS(DataSourceInt DS) {
		localDS = DS;

	}

	private <T> List<T> getResultListObject(Class<T> returnClass,
			String aSqltext, Object... aParams) throws Exception {

		List<Map<String, Object>> res = localDS.executeQueryListResult(
				DB_CONTEXT, aSqltext, aParams);

		List<T> ret = new ArrayList<T>(res.size());

		for (Map<String, Object> map : res) {
			T obj = returnClass.newInstance();
			ret.add(obj);
			ru.wg.utils.MessageUtils.fillFromMap(map, obj);
		}

		return ret;
	}

	@SuppressWarnings("unused")
	private <T> T getResultObject(Class<T> returnClass, String aSqltext,
			Object... aParams) throws Exception {

		T ret = null;
		List<Map<String, Object>> res = localDS.executeQueryListResult(
				DB_CONTEXT, aSqltext, aParams);

		if (res.size() > 0) {

			ret = returnClass.newInstance();
			ru.wg.utils.MessageUtils.fillFromMap(res.get(0), ret);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AreaControl> getControlList() throws Exception {

		List<AreaControl> ret = getResultListObject(AreaControl.class,
				"SELECT ID, NAME FROM CONTROL");

		for (AreaControl aControl : ret) {
			List<Area> areas = getResultListObject(Area.class,
					"SELECT ID, NAME FROM AREA WHERE CONTROL_ID=?",
					aControl.getID());
			aControl.setAREAS(areas);
			for (Area area : areas) {
				List<Uzel> uzels = getResultListObject(Uzel.class,
						"SELECT ID, NAME FROM UZEL WHERE AREA_ID=?",
						area.getID());
				area.setUZELS(uzels);

				for (Uzel uzel : uzels) {
					List<WGObject> wgObjects = getResultListObject(
							WGObject.class,
							"SELECT o.*, t.NAME OBJECT_TYPE_NAME FROM OBJECT o, OBJECT_TYPE t  WHERE t.ID = o.OBJECT_TYPE_ID and o.UZEL_ID=?",
							uzel.getID());
					uzel.setOBJECTS(wgObjects);
				}

			}

		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Calculation> getCalculateListByObjectID(Integer aID)
			throws Exception {

		List<Calculation> ret = getResultListObject(
				Calculation.class,
				"SELECT c.ID, c.NAME, c.OBJECT_ID, o.NAME OBJECT_NAME  FROM CALCULATION c, OBJECT o WHERE c.OBJECT_ID = o.ID and c.OBJECT_ID = ?",
				aID);

		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calculation getCalculationByID(Integer aID) throws Exception {

		List<Calculation> ret = getResultListObject(
				Calculation.class,
				"SELECT c.ID, c.NAME, c.OBJECT_ID, o.NAME OBJECT_NAME  FROM CALCULATION c, OBJECT o WHERE c.OBJECT_ID = o.ID and c.ID = ?",
				aID);
		if (ret.size() > 0) {
			return ret.get(0);
		}

		return null;
	}

	@Override
	public List<Scenarios> getScenariosListByCalculateID(Integer aID)
			throws Exception {

		List<Scenarios> ret = getResultListObject(
				Scenarios.class,
				"SELECT s.*, like( '%,' || s.ID || ',%', c.SCENARIOS_IDS) CHECKED FROM SCENARIOS s, CALCULATION c, OBJECT o WHERE s.OBJECT_TYPE_ID = o.OBJECT_TYPE_ID and o.ID = c.OBJECT_ID and c.ID=?",
				aID);

		return ret;
	}

	@Override
	public List<CalculationCriteria> getCriteriaListByCalculateID(Integer aID)
			throws Exception {

		List<CalculationCriteria> ret = getResultListObject(
				CalculationCriteria.class,
				"SELECT o.ID, o.CRITERIA_ID, o.CALCULATION_ID, c.SAFETY_GROUP_ID, c.NAME, c.SIGN, c.NPP, o.K1, o.K2, o.K3, o.ACTUAL_VALUE, o.FI FROM CRITERIA c, CALCULATION_CRITERIA o WHERE c.ID = o.CRITERIA_ID and o.CALCULATION_ID=?",
				aID);

		return ret;
	}

	@Override
	public Map<String, Integer> getScenariosCriteriaListByCalculateID(
			Integer aID) throws Exception {

		List<ScenariosCriteria> res = getResultListObject(
				ScenariosCriteria.class,
				"SELECT * FROM SCENARIOS_CRITERIA WHERE CALCULATION_ID=?", aID);

		Map<String, Integer> ret = new HashMap<String, Integer>(res.size());

		for (ScenariosCriteria sc : res) {
			ret.put(sc.getSCENARIOS_ID() + "-" + sc.getCRITERIA_ID(),
					sc.getCHECKING());
		}

		return ret;
	}

	@Override
	public void setScenariosCriteriaCheck(Integer aCalculateID,
			Integer aScenarioID, Integer aCriteriaID, Integer aCheckValue)
			throws Exception {

		List<Map<String, Object>> res = localDS
				.executeQueryListResult(
						DB_CONTEXT,
						"SELECT * FROM SCENARIOS_CRITERIA WHERE CALCULATION_ID=? AND SCENARIOS_ID=? AND CRITERIA_ID=?",
						new Object[] { aCalculateID, aScenarioID, aCriteriaID });
		String sql;
		if (res.size() != 0) {
			sql = "UPDATE SCENARIOS_CRITERIA SET CHECKING=? WHERE CALCULATION_ID=? AND SCENARIOS_ID=? AND CRITERIA_ID=?";
		} else {
			sql = "INSERT INTO SCENARIOS_CRITERIA (CHECKING, CALCULATION_ID, SCENARIOS_ID, CRITERIA_ID) VALUES(?,?,?,?)";
		}

		localDS.executeUpdate(DB_CONTEXT, sql, aCheckValue, aCalculateID,
				aScenarioID, aCriteriaID);

	}

	@Override
	public void updateFieldValue(String aDataTable, String aDataField,
			String aDataValue, String aDataId) throws Exception {

		localDS.executeUpdate(DB_CONTEXT, "UPDATE " + aDataTable + " SET "
				+ aDataField + "=? WHERE ID=?", aDataValue, aDataId);
	}

	@Override
	public List<ScenarioGroup> getScenarioGroupList() throws Exception {

		List<ScenarioGroup> ret = getResultListObject(ScenarioGroup.class,
				"SELECT * FROM SCENARIOS_GROUP");

		return ret;
	}

	@Override
	public void setCalculationScenariosIdsCheck(Integer calcID,
			Integer scenarioID, Integer aCheck) throws Exception {
		List<Map<String, Object>> res = localDS.executeQueryListResult(
				DB_CONTEXT, "SELECT SCENARIOS_IDS FROM CALCULATION WHERE ID=?",
				new Object[] { calcID });

		if (!res.isEmpty()) {
			String ids = (String) res.get(0).get("SCENARIOS_IDS");
			String scID = String.valueOf(scenarioID) + ",";
			boolean check = aCheck.equals(Integer.valueOf(1));
			boolean doUpdate = false;

			if (ids == null || ids.isEmpty()) {
				ids = ",";
			}

			if (check) {
				if (!ids.contains(scID)) {
					ids += scID;
					doUpdate = true;
				}
			} else {
				if (ids.contains(scID)) {
					ids = ids.replace(scID, "");
					doUpdate = true;
				}
			}

			if (doUpdate) {
				localDS.executeUpdate(DB_CONTEXT,
						"UPDATE CALCULATION SET SCENARIOS_IDS=? WHERE ID=?",
						ids, calcID);
			}
		}

	}

	@Override
	public List<CalculationTable> getCalculateTableListByID(Integer aID,
			boolean aEnable) throws Exception {
		List<CalculationTable> res = getResultListObject(
				CalculationTable.class,
				"SELECT  S.NPP SCENARIOS_NPP, C.NAME, C.SIGN, C.SAFETY_GROUP_ID, replace(CC.FI,',','.') FI, CT.ID, CT.CALCULATION_ID, CT.SCENARIOS_ID, CT.CRITERIA_ID, CT.RANG, CT.KZNI, CT.UTCHFI, CT.G, CT.D, CT.E1, CT.E2, CT.E3 FROM SCENARIOS AS S, CALCULATION_CRITERIA AS CC, CRITERIA AS C, CALCULATION_TABLE AS CT WHERE C.ID = CT.CRITERIA_ID AND S.ID = CT.SCENARIOS_ID AND CC.CALCULATION_ID = CT.CALCULATION_ID AND CC.CRITERIA_ID = CT.CRITERIA_ID AND CT.CALCULATION_ID = ?"
						+ ((aEnable) ? " AND CT.ENABLE = 1" : "")
						+ " ORDER BY CT.SCENARIOS_ID", aID);

		return res;
	}

	@Override
	public List<CalculationTable> getGeneratedCalculateTableListByID(Integer aID)
			throws Exception {
		List<CalculationTable> res = getResultListObject(
				CalculationTable.class,
				"SELECT  C.ID, SC.CALCULATION_ID, SC.CRITERIA_ID,  SC.SCENARIOS_ID FROM   SCENARIOS_CRITERIA SC,  CALCULATION C WHERE SC.CALCULATION_ID = C.ID AND SC.CHECKING = 1 AND like( '%,' || SC.SCENARIOS_ID || ',%', c.SCENARIOS_IDS)=1 AND SC.CALCULATION_ID = ?",
				aID);

		return res;
	}

	@Override
	public void setCalculateTableEnableByID(int aID, boolean aEnable)
			throws Exception {
		localDS.executeUpdate(DB_CONTEXT,
				"UPDATE CALCULATION_TABLE SET ENABLE=? WHERE ID=?",
				(aEnable) ? 1 : 0, aID);
	}

	@Override
	public void createCalculateTable(CalculationTable aCt) throws Exception {

		localDS.executeUpdate(
				DB_CONTEXT,
				"INSERT INTO CALCULATION_TABLE (CALCULATION_ID, SCENARIOS_ID, CRITERIA_ID, ENABLE, RANG, KZNI, UTCHFI, G, D, E1, E2, E3) VALUES (?, ?, ?, 1, 1, '-1', '-1', '-1', '-1', '-1', '-1', '-1')",
				aCt.getCALCULATION_ID(), aCt.getSCENARIOS_ID(),
				aCt.getCRITERIA_ID());
	}

	@Override
	public void deleteCalculateTable(int aID) throws Exception {
		localDS.executeUpdate(DB_CONTEXT,
				"DELETE FROM CALCULATION_TABLE WHERE ID=?", aID);

	}

	@Override
	public void updateCalculateTableID(
			final List<CalculationTable> calculateTable) throws Exception {
		@SuppressWarnings("serial")
		List<Object[]> aListParams = new ArrayList<Object[]>(
				calculateTable.size()) {
			{
				for (CalculationTable ct : calculateTable) {
					add(new Object[] { ct.getKZNI(), ct.getUTCHFI(), ct.getG(),
							ct.getD(), ct.getE1(), ct.getE2(), ct.getE3(),
							ct.getID() });
				}
			}
		};
		localDS.executeListUpdate(
				DB_CONTEXT,
				"UPDATE CALCULATION_TABLE SET KZNI = ?, UTCHFI = ?, G = ?, D = ?, E1 = ?, E2 = ?, E3 = ? WHERE ID = ?",
				aListParams);

	}

}

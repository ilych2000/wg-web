/**
 *
 */
package ru.wg.web.controllers.utils;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;

import ru.wg.db.dao.ManagerDao;
import ru.wg.web.pojo.Calculation;
import ru.wg.web.pojo.CalculationCriteria;
import ru.wg.web.pojo.ScenarioGroup;
import ru.wg.web.pojo.Scenarios;

/**
 * @author Илья
 */
public class ExportExcelUtils {

    public static void writeExel(String id, HttpServletResponse response, ManagerDao _sqlManager)
            throws Exception {
        int calcID = Integer.valueOf(id);

        Calculation calculation = _sqlManager.getCalculationByID(calcID);
        List<CalculationCriteria> criteriaList = _sqlManager.getCriteriaListByCalculateID(calcID);
        List<Scenarios> scenariosList = _sqlManager.getScenariosListByCalculateID(calcID);
        Map<String, Integer> scenariosCriteria =
                _sqlManager.getScenariosCriteriaListByCalculateID(calcID);
        List<ScenarioGroup> scenarioGroupList = _sqlManager.getScenarioGroupList();

        Map<Integer, ScenarioGroup> scGroup = new HashMap<Integer, ScenarioGroup>();

        for (ScenarioGroup scg : scenarioGroupList) {
            scGroup.put(scg.getID(), scg);
        }

        OutputStream out = response.getOutputStream();
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();

        CellStyle borderCellStyle =
                new CellStyleBuilder(wb.createCellStyle()).setBorder().wrapText().alignTop().get();

        CellStyle borderVCenterCellStyle = new CellStyleBuilder(wb.createCellStyle()).setBorder()
                .wrapText()
                .toVerticalCenter()
                .get();

        CellStyle borderCenterCellStyle =
                new CellStyleBuilder(wb.createCellStyle()).setBorder().wrapText().toCenter().get();

        CellStyle titleSheetCellStyle = new CellStyleBuilder(wb.createCellStyle()).toCenter().get();

        CellStyle titleTableCellStyle =
                new CellStyleBuilder(wb.createCellStyle()).setBorder().toCenter().get();

        CellStyle titleHorizTableCellStyle = new CellStyleBuilder(wb.createCellStyle()).setBorder()
                .toCenter()
                .rotateLeft()
                .wrapText()
                .get();

        SheetHelper sh = new SheetHelper(wb.createSheet("Сценарии"));

        sh.addCell("Таблица 1. Сценарии аварий " + calculation.getOBJECT_NAME(), 0, 0, 0, 1,
                titleSheetCellStyle);

        int rowNum = 2;

        sh.addCell("№№ п/п", rowNum, 0, titleTableCellStyle);
        sh.addCell("Сценарии аварий", rowNum, 1, titleTableCellStyle)
                .getRow()
                .setHeightInPoints(25);
        rowNum++;

        int grIndex = 0;

        for (Scenarios sc : scenariosList) {
            if (sc.getCHECKED() != 0) {
                if (sc.getSCENARIOS_GROUP_ID() != grIndex) {
                    grIndex = sc.getSCENARIOS_GROUP_ID();

                    Cell c = sh.addCell(scGroup.get(grIndex).getNAME(), rowNum++, 0, 0, 1,
                            borderCellStyle);
                    c.getRow().setHeightInPoints(20);
                }

                sh.addCell(sc.getNPP(), rowNum, 0, borderVCenterCellStyle);
                sh.addCell(sc.getNAME(), rowNum++, 1, borderCellStyle);
            }
        }
        sh.setColumnWidth(0, 10);
        sh.setColumnWidth(1, 120);

        sh = new SheetHelper(wb.createSheet("Подбор критериев"));

        sh.addCell("обозначение\nкритерия", 2, 0, 1, 0, titleHorizTableCellStyle)
                .getRow()
                .setHeightInPoints(50);
        sh.getRow(3).setHeightInPoints(18);
        sh.setColumnWidth(0, 7);
        sh.addCell("Название критерия", 2, 1, 1, 0, titleTableCellStyle);
        sh.setColumnWidth(1, 100);

        int col = 2;
        for (Scenarios sc : scenariosList) {
            if (sc.getCHECKED() != 0) {
                sh.addCell(sc.getNPP(), 2, col, titleHorizTableCellStyle);
                sh.setColumnWidth(col++, 5);
            }
        }
        rowNum = 4;
        Map<Scenarios, Integer> countScen = new HashMap<Scenarios, Integer>();
        for (CalculationCriteria cr : criteriaList) {
            sh.addCell(cr.getSIGN(), rowNum, 0, borderCenterCellStyle);
            sh.addCell(cr.getNAME(), rowNum, 1, borderCellStyle);
            col = 2;

            for (Scenarios sc : scenariosList) {
                if (sc.getCHECKED() != 0) {
                    Integer key = scenariosCriteria.get(sc.getID() + "-" + cr.getID());
                    if (key == null) {
                        key = 0;
                    }
                    if (key == 1) {
                        Integer count = countScen.get(sc);
                        if (count == null) {
                            count = 1;
                        } else {
                            count++;
                        }
                        countScen.put(sc, count);
                        sh.addCell("●", rowNum, col++, borderCenterCellStyle);
                    } else {
                        sh.addCell("", rowNum, col++, borderCenterCellStyle);

                    }
                }
            }

            rowNum++;
        }
        col = 2;
        int withTable = 1;
        for (Scenarios sc : scenariosList) {

            if (sc.getCHECKED() != 0) {
                withTable++;
                Integer count = countScen.get(sc);
                if (count == null) {

                    count = 0;
                }

                sh.addCell(count, 3, col++, borderCenterCellStyle);

            }
        }
        sh.addCell("Сценарии", 0, 0, 0, withTable, titleSheetCellStyle);
        sh.createFreezePane(2, 4);
        response.setContentType("application/ms-excel");
        response.setHeader("Expires:", "0");
        response.setHeader("Content-Disposition", "attachment;filename=\"report.xls\";");
        wb.write(out);
        out.flush();
        out.close();
        wb.close();
    }

}

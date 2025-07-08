package ru.wg.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ru.wg.web.pojo.CalculationTable;

@WebServlet(name = "getevaltable", urlPatterns = "/getevaltable.json")
public class AjaxEvalTableController extends AbstractAjaxController {

    /** 	 */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AjaxEvalTableController.class);

    @Override
    public Object doPostAjaxOperation(Object inObject, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings({"unchecked"})
        final Map<String, Object> parameters = (Map<String, Object>) inObject;

        Integer calcID = Integer.valueOf(String.valueOf(parameters.get("ID")));
        boolean isRaschet = "1".equals(parameters.get("RASCHET"));

        if (isRaschet) {
            List<CalculationTable> calculateTable =
                    _sqlManager.getCalculateTableListByID(calcID, true);

            List<List<CalculationTable>> curentListCT = new ArrayList<List<CalculationTable>>();

            int scenID = -1;
            List<CalculationTable> innerListCT = null;

            for (CalculationTable curCT : calculateTable) {

                if (scenID != curCT.getSCENARIOS_ID()) {
                    innerListCT = new ArrayList<CalculationTable>();
                    curentListCT.add(innerListCT);
                    scenID = curCT.getSCENARIOS_ID();
                }
                innerListCT.add(curCT);
            }

            for (List<CalculationTable> scenList : curentListCT) {

                int summRang = 0;

                for (CalculationTable ct : scenList) {
                    summRang += ct.getRANG();
                }

                float wMax = -1f;

                for (CalculationTable ct : scenList) {
                    float wi = (curentListCT.size() + ct.getRANG() + 1f) / summRang;
                    ct.setKZNI(wi);

                    if (wMax < wi) {
                        wMax = wi;
                    }
                }
                for (CalculationTable ct : scenList) {
                    ct.setKZNI(ct.getKZNI() / wMax);
                    ct.setUTCHFI(2 + ((ct.getFI() - 2) * ct.getKZNI()));
                }
            }

            _sqlManager.updateCalculateTableID(calculateTable);

            return calculateTable;
        }
        Map<String, CalculationTable> curentMapCT = new HashMap<String, CalculationTable>();

        for (CalculationTable curCT : _sqlManager.getCalculateTableListByID(calcID, false)) {
            curentMapCT.put(curCT.getSCENARIOS_ID() + "-" + curCT.getCRITERIA_ID(), curCT);
        }

        for (CalculationTable genCT : _sqlManager.getGeneratedCalculateTableListByID(calcID)) {

            String key = genCT.getSCENARIOS_ID() + "-" + genCT.getCRITERIA_ID();
            CalculationTable ct = curentMapCT.get(key);
            if (ct != null) {
                if (ct.getENABLE() != 1) {
                    _sqlManager.setCalculateTableEnableByID(ct.getID(), true);
                }

                genCT.setRANG(ct.getRANG());
                curentMapCT.remove(key);
            } else {
                genCT.setRANG(1);
                _sqlManager.createCalculateTable(genCT);
            }
        }

        for (CalculationTable ct : curentMapCT.values()) {
            _sqlManager.setCalculateTableEnableByID(ct.getID(), false);
        }
        return _sqlManager.getCalculateTableListByID(calcID, true);

    }

    @Override
    public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
            HttpServletResponse response) throws Exception {
        return doPostAjaxOperation(inObject, req, response);
    }

}

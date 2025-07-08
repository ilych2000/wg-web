package ru.wg.web.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ru.wg.web.pojo.CalculationCriteria;
import ru.wg.web.pojo.ScenarioGroup;
import ru.wg.web.pojo.Scenarios;

@WebServlet(name = "getculccontext", urlPatterns = "/getculccontext.json")
public class AjaxCalculateContextController extends AbstractAjaxController {

    /** 	 */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AjaxCalculateContextController.class);

    @Override
    public Object doPostAjaxOperation(Object inObject, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        @SuppressWarnings({"unchecked"})
        final Map<String, Object> parameters = (Map<String, Object>) inObject;
        final Integer calcID = Integer.valueOf(String.valueOf(parameters.get("ID")));

        CalcContext ret = new CalcContext(_sqlManager.getCriteriaListByCalculateID(calcID),
                _sqlManager.getScenariosListByCalculateID(calcID),
                _sqlManager.getScenariosCriteriaListByCalculateID(calcID),
                _sqlManager.getScenarioGroupList());

        return ret;
    }

    @Override
    public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
            HttpServletResponse response) throws Exception {

        return doPostAjaxOperation(inObject, req, response);
    }

    public static class CalcContext {

        private List<CalculationCriteria> CRITERIAS;

        private List<Scenarios> SCENARIOS;

        private List<ScenarioGroup> SCENARIOS_GROUP;

        private Map<String, Integer> SCENARIOS_CRITERIA;

        public CalcContext(List<CalculationCriteria> cRITERIAS, List<Scenarios> sCENARIOS,
                Map<String, Integer> sCENARIOS_CRITERIA, List<ScenarioGroup> sCENARIOS_GROUP) {
            super();
            CRITERIAS = cRITERIAS;
            SCENARIOS = sCENARIOS;
            SCENARIOS_CRITERIA = sCENARIOS_CRITERIA;
            SCENARIOS_GROUP = sCENARIOS_GROUP;
        }

        /**
         * @return the cRITERIAS
         */
        public List<CalculationCriteria> getCRITERIAS() {
            return CRITERIAS;
        }

        /**
         * @return the sCENARIOS
         */
        public List<Scenarios> getSCENARIOS() {
            return SCENARIOS;
        }

        /**
         * @return the sCENARIOS_GROUP
         */
        public List<ScenarioGroup> getSCENARIOS_GROUP() {
            return SCENARIOS_GROUP;
        }

        /**
         * @return the sCENARIOS_CRITERIA
         */
        public Map<String, Integer> getSCENARIOS_CRITERIA() {
            return SCENARIOS_CRITERIA;
        }

    }
}

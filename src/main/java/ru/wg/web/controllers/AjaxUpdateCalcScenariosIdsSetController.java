package ru.wg.web.controllers;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "updatescenset", urlPatterns = "/updatescenset.json")
public class AjaxUpdateCalcScenariosIdsSetController extends
		AbstractAjaxController {

	/** 	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(AjaxUpdateCalcScenariosIdsSetController.class);

	@Override
	public Object doPostAjaxOperation(Object inObject,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		@SuppressWarnings({ "unchecked" })
		final Map<String, Object> parameters = (Map<String, Object>) inObject;

		Integer calcID = Integer.valueOf(String.valueOf(parameters
				.get("CALCULATION_ID")));
		Integer scenarioID = Integer.valueOf(String.valueOf(parameters
				.get("SCENARIOS_ID")));
		Integer check = Integer.valueOf(String.valueOf(parameters
				.get("CHECK_VALUE")));

		_sqlManager.setCalculationScenariosIdsCheck(calcID, scenarioID, 
				check);

		return new Object();
	}

	@Override
	public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		return doPostAjaxOperation(inObject, req, response);
	}

}

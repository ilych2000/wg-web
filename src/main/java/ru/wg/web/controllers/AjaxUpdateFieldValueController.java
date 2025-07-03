package ru.wg.web.controllers;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "updatefield", urlPatterns = "/updatefield.json")
public class AjaxUpdateFieldValueController extends AbstractAjaxController {

	/** 	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(AjaxUpdateFieldValueController.class);

	@Override
	public Object doPostAjaxOperation(Object inObject,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		@SuppressWarnings({ "unchecked" })
		final Map<String, Object> parameters = (Map<String, Object>) inObject;

		String aDataTable = String.valueOf(parameters.get("DATA_TABLE"));
		String aDataField = String.valueOf(parameters.get("DATA_FIELD"));
		String aDataValue = String.valueOf(parameters.get("DATA_VALUE"));
		String aDataId = String.valueOf(parameters.get("DATA_ID"));

		_sqlManager.updateFieldValue(aDataTable, aDataField, aDataValue,
				aDataId);

		return new Object();
	}

	@Override
	public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		return doPostAjaxOperation(inObject, req, response);
	}

}

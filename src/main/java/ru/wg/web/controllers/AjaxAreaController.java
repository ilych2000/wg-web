package ru.wg.web.controllers;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "getarea", urlPatterns = "/getarea.json")
public class AjaxAreaController extends AbstractAjaxController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(AjaxAreaController.class);

	@Override
	public Object doPostAjaxOperation(Object inObject,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		@SuppressWarnings({ "unchecked", "unused" })
		final Map<String, Object> parameters = (Map<String, Object>) inObject;

		return _sqlManager.getControlList();
	}

	@Override
	public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return doPostAjaxOperation(inObject, req, response);
	}

}

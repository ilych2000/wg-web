package ru.wg.web.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ru.wg.web.controllers.utils.ExportExcelUtils;

@WebServlet(name = "exportexcel", urlPatterns = "/exportexcel.do")
public class ExportExcelController extends AbstractAuthControllerEx {

    /** 	 */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(ExportExcelController.class);

    @Override
    protected void doOperation(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        final String calcID = request.getParameter("ID");

        ExportExcelUtils.writeExel(calcID, response, _sqlManager);

    }

}

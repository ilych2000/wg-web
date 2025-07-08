package ru.wg.web.controllers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;

import ru.wg.web.WebConstants;

public class AbstractAjaxController extends AbstractAuthControllerEx {

    private static final Logger log = Logger.getLogger(AbstractAjaxController.class);

    private static final long serialVersionUID = 1L;

    private static final Gson JSON = new GsonBuilder().setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .setDateFormat("dd.MM.yyyy")
            .create();

    @SuppressWarnings({"unchecked", "serial"})
    @Override
    public void doOperation(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        try {
            Object inObject = null;
            Object outObject = null;
            try {

                if ((request.getHeader(WebConstants.HTTP_HEADER_CONTENT_TYPE) == null)
                        || !request.getHeader(WebConstants.HTTP_HEADER_CONTENT_TYPE)
                                .contains(WebConstants.HTTP_HEADER_CONTENT_TYPE_MULTI_PART)) {

                    if (getResponsePojoClass() != null) {
                        try {
                            inObject = JSON.fromJson(request.getReader(), getResponsePojoClass());
                        } catch (MalformedJsonException e) {
                            if (log.isDebugEnabled()) {
                                log.debug(e);
                            }
                        }
                    } else {
                        inObject = new HashMap<String, Object>();

                        for (Entry<String, String[]> p : request.getParameterMap().entrySet()) {
                            ((Map<String, Object>) inObject).put(p.getKey(), p.getValue()[0]);
                        }
                    }
                }

                if (WebConstants.HTTP_METHOD_POST.equals(request.getMethod())) {
                    outObject = doPostAjaxOperation(inObject, request, response);
                } else {
                    outObject = doGetAjaxOperation(inObject, request, response);
                }
            } catch (final Exception e) {
                log.error("", e);

                outObject = new HashMap<String, String>() {

                    {
                        put("Exception", e.toString());
                    }
                };

            }

            if (outObject != null) {
                response.setContentType(WebConstants.HTTP_HEADER_CONTENT_TYPE_APPLICATION_JAVASCRIPT
                        + "; charset=" + WebConstants.ENCODING_UTF_8);
                PrintWriter out = response.getWriter();

                JSON.toJson(outObject, out);
                if (log.isDebugEnabled()) {
                    log.debug("outObject=" + outObject);
                    log.debug("JSON=" + JSON.toJson(outObject));
                }
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.error("doOperation.", e);
            throw e;
        }
    }

    public Object doAjaxOperation(Object inObject, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return null;
    }

    public Object doPostAjaxOperation(Object inObject, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return doAjaxOperation(inObject, request, response);
    }

    public Object doGetAjaxOperation(Object inObject, HttpServletRequest req,
            HttpServletResponse response) throws Exception {
        return doAjaxOperation(inObject, req, response);
    }

    public Class<Object> getResponsePojoClass() {
        return null;
    }

}

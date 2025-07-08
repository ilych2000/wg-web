package ru.wg.web.system;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import ru.wg.db.dao.ManagerDao;
import ru.wg.db.driver.sqlite.SQLiteDataSourceImpl;
import ru.wg.db.jdbc.SQLiteManagerDaoJdbc;
import ru.wg.web.controllers.AbstractAuthControllerEx;

public class InitApp implements Servlet {

    private static final Logger log = Logger.getLogger(InitApp.class);

    @SuppressWarnings("serial")
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        log.info("Init server...");
        final String realPath = servletConfig.getServletContext().getRealPath("");
        log.info("realPath=" + realPath);
        try {
            log.info("Init AppParameters from file '" + realPath + "/WEB-INF/settings.properties"
                    + "'");
            AppParameters.init(realPath + "/WEB-INF/settings.properties");
            AppParameters.setProperty(AppParameters.APP_REAL_PATH, realPath);

            ManagerDao sqlManager = new SQLiteManagerDaoJdbc();
            sqlManager.setLocalDS(new SQLiteDataSourceImpl(new ArrayList<String>() {

                {

                    add("jdbc:sqlite:" + realPath + "/WEB-INF/db/main.db");
                }
            }));

            AbstractAuthControllerEx.setSqlManager(sqlManager);
            log.info("Init server OK.");
        } catch (IOException e) {
            log.error(e);
        }

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

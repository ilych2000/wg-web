/**
 *
 */
package ru.wg.web.controllers;

import ru.wg.db.dao.ManagerDao;

/**
 * @author Илья
 */
public abstract class AbstractAuthControllerEx extends AbstractAuthController {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected static ManagerDao _sqlManager;

    public static void setSqlManager(ManagerDao aSqlManager) {
        _sqlManager = aSqlManager;
    }
}

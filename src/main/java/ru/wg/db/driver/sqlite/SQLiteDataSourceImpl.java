package ru.wg.db.driver.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import ru.wg.db.driver.DataSourceInt;

/**
 * Класс реализует интерфейс {@link DataSourceInt} для использования SQLite. <br>
 * Для внедрения в Spring контекст используйте
 *
 * <PRE>
 * {@code
 * <spring:bean id="dataSourceBean" name="dataSourceBean"
 *    class="ru.icl.soop.interaction.db.local.utils.sqlite.driver.SQLiteDataSourceImpl">
 *         <spring:constructor-arg>
 *             <spring:list>
 *                 <spring:value>jdbc:sqlite:путь до БД 1</spring:value>
 *                 <spring:value>jdbc:sqlite:путь до БД 2</spring:value>
 *                 <spring:value>jdbc:sqlite:путь до БД 3</spring:value>
 *             </spring:list>
 *         </spring:constructor-arg>
 *             <spring:constructor-arg>
 *                 <spring:props>
 *                     <spring:prop key="journal_mode">WAL</spring:prop>
 *                     <spring:prop key="foreign_keys">true</spring:prop>
 *                 </spring:props>
 *             </spring:constructor-arg>
 * </spring:bean>
 * }
 * </PRE>
 *
 * Список БД участвует в контексте выполнения SQL. Контекст начинается с 0.
 * <p>
 * В пути к БД можно использовать подстановку <b>${localDB.dir}</b> она указывает на папку
 * <i>WEB-INF/classes</i> в вэб приложении.
 */
public class SQLiteDataSourceImpl implements DataSourceInt {

    /** Логер */
    private static final Logger LOG = Logger.getLogger(SQLiteDataSourceImpl.class);

    /** Имя параметра подстановки пути до БД SQLite */
    private static final String LOCAL_DB_DIR_PATERN = "${localDB.dir}";

    /** Список {@link DataSource} к БД SQLite */
    private List<DataSource> _dataSourceCache = null;

    /**
     * Конструктор
     *
     * @param aUrls список путей к ДБ SQLite.
     */
    public SQLiteDataSourceImpl(List<String> aUrls) {
        this(aUrls, null);
    }

    /**
     * Конструктор
     *
     * @param aUrls список путей к ДБ SQLite.
     * @param aProperties параметры подключения к БД
     * @see org.sqlite.SQLiteConfig.Pragma
     */
    public SQLiteDataSourceImpl(List<String> aUrls, Properties aProperties) {
        if (aProperties == null) {
            aProperties = new Properties();
        }

        LOG.info("Initialising SQLiteDataSourceImpl with properties:"
                + aProperties.toString().replaceAll("[{,]", "\n\t"));

        String selfPath = this.getClass().getCanonicalName().replace(".", "/") + ".class";
        String selfPath1 = getClass().getClassLoader().getResource(selfPath).getPath();
        String dbPath = (selfPath1.substring(0, selfPath1.length() - selfPath.length()))
                .replaceAll("(classes|lib).+|file:", "") + "classes/";
        LOG.info("    Dir of local DBs=" + dbPath);

        SQLiteConfig dsConfig = new SQLiteConfig(aProperties);

        _dataSourceCache = new ArrayList<DataSource>(aUrls.size());

        for (String url : aUrls) {
            url = url.replace(LOCAL_DB_DIR_PATERN, dbPath);
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setEnforceForeinKeys(true);
            ds.setConfig(dsConfig);
            ds.setUrl(url);

            _dataSourceCache.add(ds);
            LOG.info("    add DataSourceCache url=" + url);
        }
        LOG.info("Initialising create SQLiteDataSourceImpl is OK.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws Exception
     */
    @Override
    public ResultSet executeQuery(int aContext, String aSqltext, Object... aParams)
            throws Exception {
        return executeQuery(aContext, 0, aSqltext, aParams);
    }

    /**
     * {@inheritDoc}
     *
     * @throws Exception
     */
    @Override
    public ResultSet executeQuery(int aContext, int maxRows, String aSqltext, Object... aParams)
            throws Exception {
        Connection conn = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Execute:" + getLogingString(aContext, aSqltext, aParams));
            }

            conn = _dataSourceCache.get(aContext).getConnection();
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON");
            PreparedStatement prst = conn.prepareStatement(aSqltext);

            bindParameters(prst, aParams);

            prst.setMaxRows(maxRows);

            return prst.executeQuery();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e1) {
                    LOG.error("closeResultSet", e1);
                }
            }
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> executeQueryListResult(int aContext, String aSqltext,
            Object... aParams) throws Exception {

        return executeQueryListResult(aContext, aSqltext, 0, aParams);
    }

    /**
     * {@inheritDoc}
     *
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> executeQueryListResult(int aContext, String aSqltext,
            int aMaxRows, Object... aParams) throws Exception {

        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

        ResultSet rs = executeQuery(aContext, aMaxRows, aSqltext, aParams);

        ResultSetMetaData metaResult = rs.getMetaData();

        int cc = metaResult.getColumnCount();

        while (rs.next()) {
            Map<String, Object> fields = new HashMap<String, Object>(cc);
            ret.add(fields);

            for (int i = 1; i <= cc; i++) {
                fields.put(metaResult.getColumnLabel(i), rs.getObject(i));
            }
        }
        closeResultSet(rs);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Result size: " + ret.size());
        }

        return ret;
    }

    /**
     * Возвращает строку сформированную для логирования
     *
     * @param aContext номер БД
     * @param aSqltext SQL текст
     * @param aParams параметры запроса
     * @return строка
     */
    private String getLogingString(int aContext, String aSqltext, Object[] aParams) {
        return " url=" + ((SQLiteDataSource) _dataSourceCache.get(aContext)).getUrl() + "\n\tSQL:"
                + aSqltext + "\n\tParameters: " + StringsToString(aParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int executeUpdate(int aContext, String aSqltext, Object... aParams) throws SQLException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Update :" + getLogingString(aContext, aSqltext, aParams));
        }

        int ret = -1;
        Connection conn = null;
        try {
            conn = _dataSourceCache.get(aContext).getConnection();
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON");
            PreparedStatement prst = conn.prepareStatement(aSqltext);

            bindParameters(prst, aParams);

            ret = prst.executeUpdate();

            if (ret > 0) {
                ret = conn.prepareStatement("select last_insert_rowid()").executeQuery().getInt(1);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Update SQL result:" + getLogingString(aContext, aSqltext, aParams)
                            + "\n\tLast insert rowid:" + ret);
                }
            } else {
                ret = -1;
            }

            return ret;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("closeResultSet", e);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int executeListUpdate(int aContext, String aSqltext, List<Object[]> aListParams)
            throws SQLException {
        Connection conn = null;
        int ret = 0;
        try {
            conn = _dataSourceCache.get(aContext).getConnection();
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON");
            conn.setAutoCommit(false);
            PreparedStatement prst = conn.prepareStatement(aSqltext);

            for (Object[] param : aListParams) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Update :" + getLogingString(aContext, aSqltext, param));
                }

                bindParameters(prst, param);

                ret += prst.executeUpdate();
            }
            conn.commit();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("closeResultSet", e);
                }
            }
        }
        return ret;
    }

    /**
     * Устанавливает параметры в {@link PreparedStatement}
     *
     * @param aStatment {@link PreparedStatement}
     * @param aParams параметры
     * @throws SQLException
     */
    private void bindParameters(PreparedStatement aStatment, Object... aParams)
            throws SQLException {
        if (aParams != null) {
            int i = 1;
            for (Object s : aParams) {
                aStatment.setObject(i, s);
                i++;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection(int aContext) throws SQLException {
        return _dataSourceCache.get(aContext).getConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataSourceInfo() {
        return _dataSourceCache.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeResultSet(ResultSet aRs) {
        if (aRs != null) {
            try {
                aRs.getStatement().getConnection().close();
            } catch (SQLException e) {
                LOG.error(e.toString());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultSet executeQuery(int aContext, String aSqltext) throws Exception {
        return executeQuery(aContext, aSqltext, (Object[]) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int executeUpdate(int aContext, String aSqltext) throws SQLException {
        return executeUpdate(aContext, aSqltext, (Object[]) null);
    }

    /**
     * Возвращает объединенную строку через запятую
     *
     * @param aString массив строк
     * @return объединенная строка
     */
    private static String StringsToString(Object[] aString) {
        StringBuilder ret = new StringBuilder();
        if (aString != null) {
            for (Object s : aString) {
                ret.append((ret.length() == 0) ? "" : ",").append(s);
            }
        } else {
            ret.append("no params");
        }

        return ret.toString();
    }
}

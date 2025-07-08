package ru.wg.db.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс исполнения запросов в локальной БД
 */
public interface DataSourceInt {

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @return {@link ResultSet}
     * @throws Exception
     */
    public ResultSet executeQuery(int aContext, String aSqltext) throws Exception;

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @param aParams параметры
     * @return {@link ResultSet}
     * @throws Exception
     */
    public ResultSet executeQuery(int aContext, String aSqltext, Object... aParams)
            throws Exception;

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aMaxRows возвращаемое максимальное количество записей
     * @param aSqltext SQL запрос
     * @param aParams параметры
     * @return {@link ResultSet}
     * @throws Exception
     */
    public ResultSet executeQuery(int aContext, int aMaxRows, String aSqltext, Object... aParams)
            throws Exception;

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @param aParams параметры
     * @return список объектов {@link Map} содержащих поле-значение
     * @throws Exception
     */
    public List<Map<String, Object>> executeQueryListResult(int aContext, String aSqltext,
            Object... aParams) throws Exception;

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aMaxRows возвращаемое максимальное количество записей
     * @param aSqltext SQL запрос
     * @param aParams параметры
     * @return список {@link Map} значений поле-значение
     * @throws Exception
     */
    public List<Map<String, Object>> executeQueryListResult(int aContext, String aSqltext,
            int aMaxRows, Object... aParams) throws Exception;

    /**
     * Закрывает {@link ResultSet}
     *
     * @param aRs {@link ResultSet}
     */
    public void closeResultSet(ResultSet aRs);

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @return id последней измененой записи
     * @throws SQLException
     */
    public int executeUpdate(int aContext, String aSqltext) throws SQLException;

    /**
     * Выполняет запрос
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @param aParams параметры
     * @return id последней измененой записи
     * @throws SQLException
     */
    public int executeUpdate(int aContext, String aSqltext, Object... aParams) throws SQLException;

    /**
     * Выполняет запросы со списком параметров
     *
     * @param aContext контекст
     * @param aSqltext SQL запрос
     * @param aListParams список масивов значений параметров запроса
     * @return количество измененных записей
     * @throws SQLException
     */
    public int executeListUpdate(int aContext, String aSqltext, List<Object[]> aListParams)
            throws SQLException;

    /**
     * Получение {@link Connection}
     *
     * @param aContext контекст
     * @return {@link Connection}
     * @throws SQLException
     */
    public Connection getConnection(int aContext) throws SQLException;

    /**
     * Выводит в лог информацию о подлюченых БД.
     *
     * @return String
     */
    public String getDataSourceInfo();

}

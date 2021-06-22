package core;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

/**
 * Класс для работы с БД
 */
public class Database {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost/qr_code_manager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public Database() {
    }

    public String[] getArrayFromDB(String table, String[] args) {
        return getArrayFromDB(table, args, "");
    }

    /**
     * Соединяется с БД
     * @return
     * @throws SQLException
     */
    public Connection connect() throws SQLException {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }

        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return connection;
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            out.println("Ошибка SQL !");
        }

        return connection;
    }

    /**
     * Возвращает массив указанных столбцов
     * @param table name of table
     * @param args columns
     * @param where
     * @return
     */
    public String[] getArrayFromDB(String table, String[] args, String where) {
        String[] result = new String[args.length];
        String query = "SELECT ";
        for (String arg : args)
            query += arg + ", ";
        query = query.substring(0, query.length() - 2) + " FROM " + table;
        if (!where.equals(""))
            query = query + " WHERE " + where;
        query = query + ";";
        ResultSet resultSet = getResultSet(query);
        try {
            if (resultSet.next()) {
                for (int i = 0; i < args.length; i++)
                    result[i] = resultSet.getString(args[i]);
            } else
                for (int i = 0; i < args.length; i++)
                    result[i] = "null";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Возвращает все данные в виде Массива (ArrayList<Map<String,Object>>)
     * @param table
     * @return
     */
    public ArrayList getEntities(String table){
        String baseQuery = "SELECT * FROM " + table;
        ArrayList entities = new ArrayList<>();
        try {
            entities = (ArrayList)
                    loadObjectFromResultSet(getResultSet(baseQuery));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  entities;
    }

    public Object loadObjectFromResultSet(ResultSet resultSet) throws Exception
    {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i < columnCount -1 ; i++) {
                String columnName = metaData.getColumnName(i);
                Object objectValue = resultSet.getObject(i);
                map.put(columnName, objectValue);
            }
            objectArrayList.add(map); // после того, как всю строку считали, сохраняем в массив и заново
        }
        return objectArrayList;
    }

    /**
     * Возвращает запрос в ResultSet
     * @param sqlQuery
     * @return
     */
    public ResultSet getResultSet(String sqlQuery) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect().prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }


}

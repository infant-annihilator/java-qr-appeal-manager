package models;

import core.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object
 *
 */
public class AppealOperations {

    private static final Logger logger = Logger.getLogger(AppealOperations.class.getName());

    /**
     * Выводит модель как строку с разделителем
     * @param resultSet
     * @param separator
     * @throws SQLException
     */
    public void printAsString(ResultSet resultSet, String separator) throws SQLException {
        while(resultSet.next()) {

            String FIODeclarant = resultSet.getString("FIO_declarant");
            String topic = resultSet.getString("topic");

//            System.out.printf("%d. %s - %d \n", id, FIODeclarant, topic);
            System.out.println(FIODeclarant + separator + topic);
        }
    }

    /**
     * Проверяет, существует ли заявление по Id
     * @param id Id заявления
     * @return bool
     * @throws SQLException
     */
    public boolean appealExists(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Appeal> appeals = new ArrayList<>();

        try {
            // подключение к БД
            Database db = new Database();
            connection = db.connect();
            connection.setAutoCommit(false);
            String query = "SELECT id, FIO_declarant, FIO_director, address, topic FROM appeal WHERE id = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setString(counter++, id);
            ResultSet resultSet = statement.executeQuery();

            return !appeals.isEmpty();
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }

        return appeals.isEmpty() ? false : true;
    }

    /**
     * Сохраняет новое заявление в БД
     * @param appeal Объект заявления, который нужно сохранить в БД
     * @return int
     * @throws SQLException
     */
    public int save(Appeal appeal) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // подключение к БД
            Database db = new Database();
            connection = db.connect();
            connection.setAutoCommit(false);
            String query = "INSERT INTO appeal(FIO_declarant, FIO_director, address, topic) VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setString(counter++, appeal.getFioDeclarant());
            statement.setString(counter++, appeal.getFioDirector());
            statement.setString(counter++, appeal.getAddress());
            statement.setString(counter++, appeal.getTopic());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }

            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }

        return 0;
    }

}
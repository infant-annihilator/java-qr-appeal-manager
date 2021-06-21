package models;

import core.Database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain model
 *
 * @author Julian Jupiter
 *
 */
public class Appeal implements Serializable {

    private static final long serialVersionUID = 3789909326487155148L;
    private int id;
    private String fioDeclarant;
    private String fioDirector;
    private String address;
    private String topic;

    public Appeal(int id, String fioDeclarant, String fioDirector, String address, String topic) {
        this.id = id;
        this.fioDeclarant = fioDeclarant;
        this.fioDirector = fioDirector;
        this.address = address;
        this.topic = topic;
    }

    /**
     * Ищет заявление по id
     * @param id
     * @return
     * @throws SQLException
     */
    public static Appeal findById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Appeal> appeals = new ArrayList<>();

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String fioDeclarant = resultSet.getString("FIO_declarant");
            String fioDirector = resultSet.getString("FIO_director");
            String address = resultSet.getString("address");
            String topic = resultSet.getString("topic");

            Appeal appeal = new Appeal(id, fioDeclarant, fioDirector, address, topic);

            return appeal;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFioDeclarant() {
        return fioDeclarant;
    }

    public void setFioDeclarant(String fioDeclarant) {
        this.fioDeclarant = fioDeclarant;
    }

    public String getFioDirector() {
        return fioDirector;
    }

    public void setFioDirector(String fioDirector) {
        this.fioDirector = fioDirector;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Выводит модель как строку с разделителем
     * @param separator
     * @throws SQLException
     */
    public String printAsString(String separator) throws SQLException {
            return this.fioDeclarant + separator + this.topic;
    }
}
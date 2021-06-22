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
    private static final int STATUS_REJECTED = 0;
    private static final int STATUS_CHECKED = 1;
    private static final int STATUS_AWAITING = 2;
    private int id;
    private String fioDeclarant;
    private String fioDirector;
    private String address;
    private String topic;
    private String content;
    private String resolution;
    private String note;
    private int status;


    public Appeal(int id, String fioDeclarant, String fioDirector, String address, String topic, String content, String resolution, String note, int status) {
        this.id = id;
        this.fioDeclarant = fioDeclarant;
        this.fioDirector = fioDirector;
        this.address = address;
        this.topic = topic;
        this.content = content;
        this.resolution = resolution;
        this.note = note;
        this.status = status;
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
            String content = resultSet.getString("content");
            String resolution = resultSet.getString("resolution");
            String note = resultSet.getString("note");
            int status = resultSet.getInt("status");

            Appeal appeal = new Appeal(id, fioDeclarant, fioDirector, address, topic, content, resolution, note, status);

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Возвращает модель как строку с разделителем
     * @throws SQLException
     */
    public String printAsString() throws SQLException {
            return "id:" + this.id
                    + " FIO_declarant:" + this.fioDeclarant
                    + " FIO_director" + this.fioDirector
                    + " address:" + this.address
                    + " topic:" + this.topic
                    + " content:" + this.content
                    + " resolution:" + this.resolution
                    + " note:" + this.note
                    + " status:" + this.status;
    }
}
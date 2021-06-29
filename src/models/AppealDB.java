package models;

import core.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для операций, связанных с БД
 * (непосредственно с таблицей Appeal)
 */
public class AppealDB {

    private static final Logger logger = Logger.getLogger(AppealDB.class.getName());

    /**
     * Ищет заявление по id
     * @param id Id заявления
     * @return Appeal Объект заявления
     * @throws SQLException
     */
    public static Appeal findById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

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

            Appeal appeal = new Appeal(fioDeclarant, fioDirector, address, topic, content, resolution, note, status);
            appeal.id = id;

            return appeal;
        }
        return null;
    }

    /**
     * Возвращает все заявления
     * @return ObservableList Динамический массив моделей Appeal
     * @throws SQLException
     */
    public static ObservableList<Appeal> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Appeal> appealsData = FXCollections.observableArrayList();

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal ORDER BY id DESC";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fioDeclarant = resultSet.getString("FIO_declarant");
            String fioDirector = resultSet.getString("FIO_director");
            String address = resultSet.getString("address");
            String topic = resultSet.getString("topic");
            String content = resultSet.getString("content");
            String resolution = resultSet.getString("resolution");
            String note = resultSet.getString("note");
            int status = resultSet.getInt("status");

            Appeal appeal = new Appeal(fioDeclarant, fioDirector, address, topic, content, resolution, note, status);
            appeal.setId(id);

            appealsData.add(appeal);
        }

        return appealsData;
    }

    /**
     * Возвращает все заявления со статусом "ожидание" (только новые)
     * @return ObservableList Динамический массив моделей Appeal
     * @throws SQLException
     */
    public static ObservableList<Appeal> findNew() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Appeal> appealsData = FXCollections.observableArrayList();

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal WHERE status = " + Appeal.STATUS_AWAITING;
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fioDeclarant = resultSet.getString("FIO_declarant");
            String fioDirector = resultSet.getString("FIO_director");
            String address = resultSet.getString("address");
            String topic = resultSet.getString("topic");
            String content = resultSet.getString("content");
            String resolution = resultSet.getString("resolution");
            String note = resultSet.getString("note");
            int status = resultSet.getInt("status");

            Appeal appeal = new Appeal(fioDeclarant, fioDirector, address, topic, content, resolution, note, status);
            appeal.setId(id);

            appealsData.add(appeal);
        }

        return appealsData;
    }

    /**
     * Сохраняет новое заявление в БД
     * @param appeal Объект заявления, который нужно сохранить в БД
     * @return int
     * @throws SQLException
     */
    public static int save(Appeal appeal) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // подключение к БД
            Database db = new Database();
            connection = db.connect();
            connection.setAutoCommit(false);

            if (appeal.id == 0) {
                String query = "INSERT INTO appeal(FIO_declarant, FIO_director, address, topic, content, status) VALUES(?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int counter = 1;
                statement.setString(counter++, appeal.getFioDeclarant());
                statement.setString(counter++, appeal.getFioDirector());
                statement.setString(counter++, appeal.getAddress());
                statement.setString(counter++, appeal.getTopic());
                statement.setString(counter++, appeal.getContent());
                statement.setInt(counter++, appeal.getStatus());
            } else {
                // если запись уже была в БД
                String query = "INSERT INTO appeal(id, FIO_declarant, FIO_director, address, topic, content, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int counter = 1;
                statement.setInt(counter++, appeal.getId());
                statement.setString(counter++, appeal.getFioDeclarant());
                statement.setString(counter++, appeal.getFioDirector());
                statement.setString(counter++, appeal.getAddress());
                statement.setString(counter++, appeal.getTopic());
                statement.setString(counter++, appeal.getContent());
                statement.setInt(counter++, appeal.getStatus());
            }


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

    /**
     * Обновляет заявление в БД
     * @param appeal Объект заявления, который нужно обновить в БД
     * @return int
     * @throws SQLException
     */
    public static int update(Appeal appeal) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // подключение к БД
            Database db = new Database();
            connection = db.connect();
            connection.setAutoCommit(false);
            String query = "UPDATE appeal SET resolution = ?, note = ?, status = ? WHERE id = " + appeal.id;
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setString(counter++, appeal.getResolution());
            statement.setString(counter++, appeal.getNote());
            statement.setInt(counter++, appeal.getStatus());
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

    /**
     * Чтение QR кода как строки
     * @param str
     * @return
     */
    public static int readString(String str) throws SQLException {
        int indexId = str.indexOf("id:"); // Нахождение слова id
        int indexFIO_declarant = str.indexOf("FIO_declarant:"); // Нахождение слова FIO_declarant
        int indexFIO_director = str.indexOf("FIO_director:"); // Нахождение слова FIO_director
        int indexaddress = str.indexOf("address:"); // Нахождение слова address
        int indextopic = str.indexOf("topic:"); // Нахождение слова topic
        int indexcontent = str.indexOf("content:"); // Нахождение слова content
        int indexresolution = str.indexOf("resolution:"); // Нахождение слова resolution
        int indexnote = str.indexOf("note:"); // Нахождение слова note
        int indexstatus = str.indexOf("status:"); // Нахождение слова status

        // Допустим наша строка называется Str где все данные из БД
        String id = str.substring(indexId + 3, indexFIO_declarant - 1);
        String FIO_declarant = str.substring(indexFIO_declarant + 14, indexFIO_director - 1);
        String FIO_director = str.substring(indexFIO_director + 13, indexaddress - 1);
        String address = str.substring(indexaddress + 8, indextopic - 1);
        String topic = str.substring(indextopic + 6, indexcontent - 1);
        String content = str.substring(indexcontent + 8, indexresolution - 1);
        String resolution = str.substring(indexresolution + 11, indexnote - 1);
        String note = str.substring(indexnote + 5, indexstatus - 1);
        int status = Integer.parseInt(str.substring(indexstatus + 7));

        Appeal appeal = new Appeal(FIO_declarant, FIO_director, address, topic, content, resolution, note, status);
        appeal.id = Integer.parseInt(id);

        if (appeal.status == Appeal.STATUS_AWAITING) {
            return save(appeal);
        } else {
            System.out.println(appeal.resolution);
            return update(appeal);
        }
    }


    /**
     * Возвращение последнего Id из БД
     * @return int
     * @throws SQLException
     */
    public static int getLastId() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal ORDER BY id DESC";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    /**
     * Считает количестов обращений со статусом ожидания
     * @return int
     * @throws SQLException
     */
    public static int countNew() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal WHERE status = " + Appeal.STATUS_AWAITING;
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;

        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    /**
     * Считает количестов обращений со статусом отклонено
     * @return int
     * @throws SQLException
     */
    public static int countRejected() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal WHERE status = " + Appeal.STATUS_REJECTED;
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;

        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    /**
     * Считает количестов обращений со статусом утверждено
     * @return int
     * @throws SQLException
     */
    public static int countChecked() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // подключение к БД
        Database db = new Database();
        connection = db.connect();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM appeal WHERE status = " + Appeal.STATUS_CHECKED;
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;

        while (resultSet.next()) {
            count++;
        }
        return count;
    }
}
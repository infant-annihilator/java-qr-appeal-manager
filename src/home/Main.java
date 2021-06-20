package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Appeal;
import models.AppealOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import static core.Database.getDBConnection;
import static java.lang.System.out;

/**
 * Входной класс программы
 */
public class Main extends Application {
    public static final String DB_URL = "jdbc:mysql://localhost/qr_code_manager";
    public static final String DB_Driver = "com.MySql.jdbc.Driver";
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private AppealOperations appealOperations = new AppealOperations();
    private double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setScene(new Scene(root));
        //set stage borderless
        primaryStage.initStyle(StageStyle.UNDECORATED);


        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.show();
    }

    /**
     * Подключение к БД
     */
    public static void dbConnect() {
        try {

            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            Connection connection = DriverManager.getConnection(DB_URL);//соединениесБД
            out.println("Соединение с СУБД выполнено.");
            connection.close();       // отключение от БД
            out.println("Отключение от СУБД выполнено.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            out.println("Ошибка SQL !");
        }
    }

    public static Appeal createAppealObject(String fioDeclarant, String fioDirector, String address, String topic) {
        Appeal appeal = new Appeal();
        appeal.setFioDeclarant(fioDeclarant);
        appeal.setFioDirector(fioDirector);
        appeal.setAddress(address);
        appeal.setTopic(topic);
        // return appealOpertations.save(appeal);
        return appeal;
    }

    /**
     * Тестовая функция для печати завяления в строку
     * @param id
     * @return
     * @throws SQLException
     */
    public static java.sql.ResultSet printAppeal(String id) throws SQLException {
        return AppealOperations.findById(id);
    }


    /**
     * Входная функция
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        getDBConnection();
        launch(args);
    }
}

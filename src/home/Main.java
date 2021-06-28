package home;

import core.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Appeal;
import models.AppealDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Входной класс программы
 */
public class Main extends Application {
    public static final String DB_URL = "jdbc:mysql://localhost/qr_code_manager";
    public static final String DB_Driver = "com.MySql.jdbc.Driver";
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private AppealDB appealDB = new AppealDB();
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
     * Тестовая функция для печати завяления в строку
     * @param id
     * @return
     * @throws SQLException
     */
    public static void printAppeal(int id) throws SQLException {
        Appeal appeal = AppealDB.findById(id);
        System.out.println(appeal.printAsString());
    }

    public static void getAppeals() throws SQLException {
        Database db = new Database();
        db.connect();
        ArrayList appeals = db.getEntities("appeal");
        System.out.println(appeals);
    }

    /**
     * Входная функция
     * @param args
     */
    public static void main(String[] args) throws Exception {

        // подключение к БД
        Database db = new Database();
        db.connect();

        // System.out.println(QRCodeRead.main("C:\\Users\\user\\Desktop\\MyQRCode.png"));
//        QRCodeGenerator.main("id:2 FIO_declarant:Лазо Егор FIO_director:Семёнов Константин address:Белградская 62 topic:Почему content:Какое решение данной проблемы? resolution:null note:null status:2");
        launch(args);
    }
}

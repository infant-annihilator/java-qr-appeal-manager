package home;

import core.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Входной класс программы
 */
public class Main extends Application {
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
     * Входная функция рограммы
     * @param args Необходимый параметр
     */
    public static void main(String[] args) throws Exception {

        // подключение к БД
        Database db = new Database();
        db.connect();

//        System.out.println(QRCodeRead.main(["C:\\Users\\user\\Desktop\\MyQRCode.png"]);
        launch(args);
    }
}

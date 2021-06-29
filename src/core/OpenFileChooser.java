package core;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OpenFileChooser extends Application {
    private final Desktop desktop = Desktop.getDesktop();

    /**
     * Входная функция класса
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        final FileChooser fileChooser = new FileChooser();

        TextArea textArea = new TextArea();
        textArea.setMinHeight(70);

        Button button1 = new Button("Select One File and Open");

        Button buttonM = new Button("Select Multi Files");

        button1.setOnAction(event -> {
            textArea.clear();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                openFile(file);
                List<File> files = Arrays.asList(file);
                printLog(textArea, files);
            }
        });

        buttonM.setOnAction(event -> {
            textArea.clear();
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);

            printLog(textArea, files);
        });

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        root.getChildren().addAll(textArea, button1, buttonM);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("JavaFX FileChooser (o7planning.org)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * ечать логов по открываемому файлу
     * @param textArea
     * @param files
     */
    private void printLog(TextArea textArea, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
            textArea.appendText(file.getAbsolutePath() + "\n");
        }
    }

    /**
     * Открытие файла
     * @param file Путь
     */
    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

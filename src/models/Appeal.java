package models;

import home.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * Модель таблицы Appeal
 */
public class Appeal implements Serializable {

    @Serial
    private static final long serialVersionUID = 3789909326487155148L;

    public static final int STATUS_REJECTED = 0;
    public static final int STATUS_CHECKED = 1;
    public static final int STATUS_AWAITING = 2;
    public int id;
    public String fioDeclarant;
    public String fioDirector;
    public String address;
    public String topic;
    public String content;
    public String resolution;
    public String note;
    public int status;
    public String statusTitle;
    public Button editBtn;

    /**
     * Конструктор модели
     * @param fioDeclarant ФИО заявителя
     * @param fioDirector ФИО директора
     * @param address Адрес
     * @param topic Тема
     * @param content Содержание
     * @param resolution Решение
     * @param note Примечание
     * @param status Статус
     */
    public Appeal(String fioDeclarant, String fioDirector, String address, String topic, String content, String resolution, String note, int status) {
        this.fioDeclarant = fioDeclarant;
        this.fioDirector = fioDirector;
        this.address = address;
        this.topic = topic;
        this.content = content;
        this.resolution = resolution;
        this.note = note;
        this.status = status;
        this.statusTitle = getStatusTitle();
        this.editBtn = getEditBtn();
    }

    /**
     * Статусы массивом строк
     * @return
     */
    public static String[] statuses() {
        String[] array = new String[3];

        array[STATUS_REJECTED] = "Отклонено";
        array[STATUS_CHECKED] = "Утверждено";
        array[STATUS_AWAITING] = "Ожидает";

        return array;
    }

    /**
     * Статусы как динамичесакий массив для выпадающего списка
     * @return
     */
    public static ObservableList observeStatuses() {
        ObservableList<String> array = FXCollections.observableArrayList();
        array.add(STATUS_REJECTED, "Отклонено");
        array.add(STATUS_CHECKED, "Утверждено");
        array.add(STATUS_AWAITING, "Ожидает");

        return array;
    }

    /**
     * Возвращает статус словом
     * @return string
     */
    public String getStatusTitle() {
        String[] array = statuses();
        return array[this.status];
    }

    /**
     * Возвращает кнопку редактирования
     * @return string
     */
    public Button getEditBtn() {
        Button btn = new Button("✎ Редактировать");
        btn.setStyle("-fx-cursor: hand");

        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/Home.fxml"));
                    try {
                        Parent root = loader.load();
                        Controller controller = loader.getController();

                        Scene newScene = new Scene(root);
                        Node node = (Node) event.getSource();
                        Stage thisStage = (Stage) node.getScene().getWindow();
                        thisStage.setScene(newScene);
                        thisStage.show();

                        controller.editAppeal(id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        return btn;
    }

    /**
     * Возвращает модель как строку с разделителем
     */
    public String printAsString() {
            return "id:" + this.id
                    + " FIO_declarant:" + this.fioDeclarant
                    + " FIO_director:" + this.fioDirector
                    + " address:" + this.address
                    + " topic:" + this.topic
                    + " content:" + this.content
                    + " resolution:" + this.resolution
                    + " note:" + this.note
                    + " status:" + this.status;
    }


    /* ГЕТТЕРЫ И СЕТТЕРЫ */

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
}
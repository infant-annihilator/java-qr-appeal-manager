package home;

import com.gluonhq.charm.glisten.control.TextField;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import core.qr.QRCodeGenerator;
import core.qr.QRCodeRead;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Appeal;
import models.AppealDB;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Класс, отвечающий за события интерфейса
 */
public class Controller implements Initializable {

    private ObservableList<Appeal> appealsData = FXCollections.observableArrayList();

    @FXML
    private Window main;

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnMain;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnNewAppeals;

    @FXML
    private Button btnAddAppeal;

    @FXML
    private Button btnUpdateAppeal;

    @FXML
    private Button btnScanQR;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnCreateAnotherAppeal;

    @FXML
    private Pane pnlHome;

    @FXML
    private Pane pnlNewAppeals;

    @FXML
    private Pane pnlNewAppeal;

    @FXML
    private Pane pnlScanQR;

    @FXML
    private Pane pnlQRScannedOne;

    @FXML
    private Pane pnlQRScannedTwo;

    @FXML
    private Pane pnlAppealCreated;

    @FXML
    private Pane pnlAppealUpdated;

    @FXML
    private Pane pnlDragging;

    @FXML
    private Pane pnlEditAppeal;

    @FXML
    private TextField inputFIODeclarant;

    @FXML
    private TextField inputFIODirector;

    @FXML
    private TextField inputAddress;

    @FXML
    private TextField inputTopic;

    @FXML
    private TextArea inputContext;

    @FXML
    private TextArea inputNote;

    @FXML
    private TextArea inputResolution;

    @FXML
    private ComboBox inputStatus;

    @FXML
    private Button btnNewAppeal;

    @FXML
    private Button btnChooseQR;

    @FXML
    private Region rgnDrag;

    @FXML
    private Label labelEditAppeal;

    @FXML
    private Label labelId;

    @FXML
    private Label labelFIODeclarant;

    @FXML
    private Label labelFIODirector;

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelTopic;

    @FXML
    private Text labelContent;

    @FXML
    private Label labelStatus;

    @FXML
    private Region rgnDrag1;

    @FXML
    private TableView<Appeal> tableAppeals;

    @FXML
    private TableColumn<Appeal, Integer> columnId;

    @FXML
    private TableColumn<Appeal, String> columnFIODeclarant;

    @FXML
    private TableColumn<Appeal, String> columnFIODirector;

    @FXML
    private TableColumn<Appeal, String> columnAddress;

    @FXML
    private TableColumn<Appeal, String> columnTopic;

    @FXML
    private TableColumn<Appeal, String> columnContent;

    @FXML
    private TableColumn<Appeal, String> columnStatus;



    @FXML
    private TableView<Appeal> tableNewAppeals;

    @FXML
    private TableColumn<Appeal, Integer> columnId1;

    @FXML
    private TableColumn<Appeal, String> columnFIODeclarant1;

    @FXML
    private TableColumn<Appeal, String> columnAddress1;

    @FXML
    private TableColumn<Appeal, String> columnTopic1;

    @FXML
    private TableColumn<Appeal, String> columnContent1;

    @FXML
    private TableColumn<Appeal, String> columnEditAppeal;
    /**
     * Инициализация данных при запуске приложения
     */
    public void initialize(URL location, ResourceBundle resources) {
        initAppeals();
        initNewAppeals();
    }

    public void initAppeals() {
        try {
            appealsData = AppealDB.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // устанавливаем тип и значение которое должно хранится в колонке
        columnId.setCellValueFactory(new PropertyValueFactory<Appeal, Integer>("id"));
        columnFIODeclarant.setCellValueFactory(new PropertyValueFactory<Appeal, String>("fioDeclarant"));
        columnFIODirector.setCellValueFactory(new PropertyValueFactory<Appeal, String>("fioDirector"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Appeal, String>("address"));
        columnTopic.setCellValueFactory(new PropertyValueFactory<Appeal, String>("topic"));
        columnContent.setCellValueFactory(new PropertyValueFactory<Appeal, String>("content"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Appeal, String>("statusTitle"));

        // заполняем таблицу данными
        tableAppeals.setItems(appealsData);
    }

    public void initNewAppeals() {
        try {
            appealsData = AppealDB.findNew();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // устанавливаем тип и значение которое должно хранится в колонке
        columnId1.setCellValueFactory(new PropertyValueFactory<Appeal, Integer>("id"));
        columnFIODeclarant1.setCellValueFactory(new PropertyValueFactory<Appeal, String>("fioDeclarant"));
        columnAddress1.setCellValueFactory(new PropertyValueFactory<Appeal, String>("address"));
        columnTopic1.setCellValueFactory(new PropertyValueFactory<Appeal, String>("topic"));
        columnContent1.setCellValueFactory(new PropertyValueFactory<Appeal, String>("content"));
        columnEditAppeal.setCellValueFactory(new PropertyValueFactory<Appeal, String>("editBtn"));


        // заполняем таблицу данными
        tableNewAppeals.setItems(appealsData);
    }

    /**
     * Закрытие окна при нажатии на крестик
     * @param actionEvent
     */
    public void closeWindow(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnClose) {
            System.exit(0);
        }
    }

    public void handleClicks(ActionEvent actionEvent) {
        // главноя страница
        if (actionEvent.getSource() == btnMain || actionEvent.getSource() == btnHome) {
            toHome();
        }
        if (actionEvent.getSource() == btnNewAppeals) {
            toNewAppeals();
        }
        if (actionEvent.getSource() == btnAddAppeal || actionEvent.getSource() == btnCreateAnotherAppeal) {
            toNewAppeal();
        }
        if(actionEvent.getSource() == btnScanQR) {
            toScan();
        }
    }

    public void newAppeal(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == btnNewAppeal) {
            String fioDeclarant = inputFIODeclarant.getText();
            String fioDirector = inputFIODirector.getText();
            String address = inputAddress.getText();
            String topic = inputTopic.getText();
            String content = inputContext.getText();
            int status = Appeal.STATUS_AWAITING;

            // добавление в БД
            Appeal appeal = new Appeal(fioDeclarant, fioDirector, address, topic, content, null, null, status);
            AppealDB appealDB = new AppealDB();
            appealDB.save(appeal);

            // создание QR-кода
//            String fileName = "Заявление" + Integer.toString(appeal.id);
            QRCodeGenerator.main(appeal.printAsString());

            // переключение страницы
            pnlAppealCreated.setVisible(true);
            pnlAppealCreated.setStyle("-fx-background-color : #02030A");
            pnlAppealCreated.toFront();

            // очистка форм
            inputFIODeclarant.setText("");
            inputFIODirector.setText("");
            inputAddress.setText("");
            inputTopic.setText("");
            inputContext.setText("");

            initAppeals();
            initNewAppeals();
        }
    }

    public void editAppeal(int id) throws SQLException {
        Appeal appeal = AppealDB.findById(id);

        labelEditAppeal.setText(appeal.id + ": " + appeal.topic);

        labelId.setText(String.valueOf(appeal.id));
        labelFIODeclarant.setText(appeal.fioDeclarant);
        labelFIODirector.setText(appeal.fioDirector);
        labelAddress.setText(appeal.address);
        labelTopic.setText(appeal.topic);

        ObservableList<String> statuses = FXCollections.observableArrayList(appeal.observeStatuses());
        inputStatus.setItems(statuses);
        inputStatus.setValue(appeal.statusTitle);

        labelContent.setText(appeal.content);
        labelContent.wrappingWidthProperty().set(500);

        toEditAppeal();
    }

    /**
     * Сохранение обновлённого заявления
     * @param actionEvent
     * @throws SQLException
     */
    public void updateAppeal(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == btnUpdateAppeal) {
            String id = labelId.getText();
            Appeal appeal = AppealDB.findById(Integer.parseInt(id));

            String resolution = inputResolution.getText();
            String note = inputNote.getText();
            String statusValue = (String) inputStatus.getValue();
            int status = appeal.observeStatuses().indexOf(statusValue);

            appeal.resolution = resolution;
            appeal.note = note;
            appeal.status = status;
            AppealDB.update(appeal);
            QRCodeGenerator.main(appeal.printAsString());

            initAppeals();
            initNewAppeals();

            toAppealUpdated();
        }
    }

    public void setOnDragOver(DragEvent event) throws NotFoundException, IOException, WriterException {
        if (event.getGestureSource() != rgnDrag
                && event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();

        pnlDragging.setVisible(true);
        pnlDragging.setStyle("-fx-background-color : #171a24");
        pnlDragging.toFront();
    }

    /**
     * При отпускании мыши при драг н дропе или выходе за окно
     * @param event
     * @throws NotFoundException
     * @throws IOException
     * @throws WriterException
     */
    public void setOnDragExited(DragEvent event) {
        event.consume();

        pnlScanQR.setVisible(true);
        pnlScanQR.setStyle("-fx-background-color : #02030A");
        pnlScanQR.toFront();
    }

    /**
     * Завершение Drag n Drop
     * @param event
     */
    public void setOnDragDropped(DragEvent event) throws NotFoundException, IOException, WriterException {
        Dragboard db = event.getDragboard();
//        boolean success = false;
        if (db.hasFiles()) {
//            System.out.println(db.getFiles().toString());

            QRCodeRead.main(db.getFiles().toString());
//            success = true;
        }

        /* let the source know whether the string was successfully
         * transferred and used */
//        event.setDropCompleted(success);

        pnlAppealCreated.setVisible(true);
        pnlAppealCreated.setStyle("-fx-background-color : #02030A");
        pnlAppealCreated.toFront();

        event.consume();
    }

    /**
     * Загрузка файла
     */
    public void loadFile() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузите QR код");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("QR коды", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(main);
        String appealAsString = null;
        if (selectedFile != null) {
            System.out.println(selectedFile);
            appealAsString = QRCodeRead.main(selectedFile.getPath());
        }

        AppealDB.readString(appealAsString);

        initAppeals();
        initNewAppeals();

        qrScanned();
    }

    public void qrScanned() {
        pnlQRScannedOne.setVisible(true);
        pnlQRScannedOne.setStyle("-fx-background-color : #02030A");
        pnlQRScannedOne.toFront();
    }

    public void toHome() {
        pnlHome.setVisible(true);
        pnlHome.setStyle("-fx-background-color : #02030A");
        pnlHome.toFront();
    }

    public void toScan() {
        pnlScanQR.setVisible(true);
        pnlScanQR.setStyle("-fx-background-color : #02030A");
        pnlScanQR.toFront();
    }

    public void toNewAppeal() {
        pnlNewAppeal.setVisible(true);
        pnlNewAppeal.setStyle("-fx-background-color : #02030A");
        pnlNewAppeal.toFront();
    }

    public void toNewAppeals() {
        pnlNewAppeals.setVisible(true);
        pnlNewAppeals.setStyle("-fx-background-color : #02030A");
        pnlNewAppeals.toFront();
    }

    public void toEditAppeal() {
        pnlEditAppeal.setVisible(true);
        pnlEditAppeal.setStyle("-fx-background-color : #02030A");
        pnlEditAppeal.toFront();
    }

    public void toAppealUpdated() {
        pnlAppealUpdated.setVisible(true);
        pnlAppealUpdated.setStyle("-fx-background-color : #02030A");
        pnlAppealUpdated.toFront();
    }
}

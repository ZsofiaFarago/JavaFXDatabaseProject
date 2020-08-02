package phonebook.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import phonebook.model.PDFGeneration;
import phonebook.model.PersonModel;

import java.io.FileOutputStream;
import java.lang.annotation.Documented;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TableView table;
    @FXML private TextField inputFirstName;
    @FXML private TextField inputLastName;
    @FXML private TextField inputEmail;
    @FXML private Button addNewContactButton;
    @FXML private StackPane menuPane;
    @FXML private Pane contactPane;
    @FXML private Pane exportPane;
    @FXML private Button exportButton;
    @FXML private TextField inputExport;

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<PersonModel> data = FXCollections.observableArrayList(
            new PersonModel("Pista", "Bácsi", "pista.bacsi@email.com"),
            new PersonModel("Dave", "Jason", "dave.jason@email.com"),
            new PersonModel("Darth", "Vader", "darth.vader@email.com")
    );

    private void setTableData() {
        TableColumn firstNameColumn = new TableColumn("Keresztnév");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<PersonModel, String>("firstName"));

        firstNameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<PersonModel,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<PersonModel,String> cellEditEvent) {
                        ((PersonModel) cellEditEvent.getTableView().getItems()
                                .get(cellEditEvent.getTablePosition().getRow()))
                                .setFirstName(cellEditEvent.getNewValue());
                    }
                }
        );

        TableColumn lastNameColumn = new TableColumn("Vezetéknév");
        lastNameColumn.setMinWidth(100);
        // Ebben az oszlopban minden cellának TextField legyen a tartalma
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<PersonModel, String>("lastName"));

        lastNameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<PersonModel,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<PersonModel,String> cellEditEvent) {
                        ((PersonModel) cellEditEvent.getTableView().getItems()
                                .get(cellEditEvent.getTablePosition().getRow()))
                                .setLastName(cellEditEvent.getNewValue());
                    }
                }
        );

        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(new PropertyValueFactory<PersonModel, String>("email"));

        emailColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<PersonModel,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<PersonModel,String> cellEditEvent) {
                        ((PersonModel) cellEditEvent.getTableView().getItems()
                                .get(cellEditEvent.getTablePosition().getRow()))
                                .setEmail(cellEditEvent.getNewValue());
                    }
                }
        );

        table.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn);
        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot);
        treeView.setShowRoot(false);

        Node contactNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> treeItemA = new TreeItem<>(MENU_CONTACTS);
        treeItemA.setExpanded(true);
        TreeItem<String> treeItemB = new TreeItem<>(MENU_EXIT);

        TreeItem<String> treeItemA1 = new TreeItem<>(MENU_LIST, contactNode);
        TreeItem<String> treeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        treeItemA.getChildren().addAll(treeItemA1, treeItemA2);
        treeItemRoot.getChildren().addAll(treeItemA, treeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                // Vesszük azt az objektumot, amelyre rákattintott a felhasználó és kimentjük a nevét
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableData();
        setMenuData();
    }

    @FXML
    public void addContact(ActionEvent actionEvent) {
        String email = inputEmail.getText();
        if (email.contains("@") && email.contains(".") && email.length() >= 6) {
            data.add(new PersonModel(inputFirstName.getText(), inputLastName.getText(), inputEmail.getText()));
            inputFirstName.clear();
            inputLastName.clear();
            inputEmail.clear();
        }
    }

    @FXML
    public void exportList(ActionEvent actionEvent) {
        String fileName = inputExport.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null || !fileName.equals("")) {
            PDFGeneration pdfGeneration = new PDFGeneration();
            pdfGeneration.pdfGeneration(fileName, data);
        }
    }
}

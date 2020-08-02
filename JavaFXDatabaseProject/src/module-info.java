module JavaFXDatabaseProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires itextpdf;

    opens phonebook;
    opens phonebook.controller;
    opens phonebook.model;
    opens phonebook.view;
}
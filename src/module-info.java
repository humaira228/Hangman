module letsee {
    requires javafx.controls;
    requires javafx.fxml;

    exports application;
    opens application to javafx.fxml;
}

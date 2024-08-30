module javafx.poov {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens javafx.poov to javafx.fxml, javafx.graphics;
    opens javafx.poov.modelo to javafx.base, javafx.graphics;
    opens javafx.poov.controller to javafx.fxml, javafx.graphics;
    exports javafx.poov;
}

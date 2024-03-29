module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
    exports com.example.lab4.GUI;
    opens com.example.lab4.GUI to javafx.fxml;
}
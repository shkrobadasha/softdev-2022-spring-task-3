module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports com.example.battleship.view;
    opens com.example.battleship.view to javafx.fxml;
    exports com.example.battleship;
    opens com.example.battleship to javafx.fxml;
}
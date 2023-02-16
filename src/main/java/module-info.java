module com.example.chessgamejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.andrei to javafx.fxml;
    exports com.andrei;
    exports com.andrei.except;
    opens com.andrei.except to javafx.fxml;
    exports com.andrei.pieces;
    opens com.andrei.pieces to javafx.fxml;
    exports com.andrei.game;
    opens com.andrei.game to javafx.fxml;
    exports com.andrei.gui;
    opens com.andrei.gui to javafx.fxml;
}
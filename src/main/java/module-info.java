module sistemaloja.aplicativo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;

    opens sistemaloja.aplicativo to javafx.fxml;
    exports sistemaloja.aplicativo;
    exports sistemaloja.aplicativo.Controller;
    opens sistemaloja.aplicativo.Controller to javafx.fxml;
}
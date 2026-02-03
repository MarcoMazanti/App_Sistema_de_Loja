module sistemaloja.aplicativo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens sistemaloja.aplicativo to javafx.fxml;
    exports sistemaloja.aplicativo;
    exports sistemaloja.aplicativo.Controller;
    opens sistemaloja.aplicativo.Controller to javafx.fxml;
    opens sistemaloja.aplicativo.Entity.Filial to com.fasterxml.jackson.databind;
    opens sistemaloja.aplicativo.Entity.Cliente to com.fasterxml.jackson.databind;
    opens sistemaloja.aplicativo.Entity.Empregado to com.fasterxml.jackson.databind;
    opens sistemaloja.aplicativo.Entity.Fornecedor to com.fasterxml.jackson.databind;
    opens sistemaloja.aplicativo.Entity.Estoque to com.fasterxml.jackson.databind;
    opens sistemaloja.aplicativo.Entity.Pagamento to com.fasterxml.jackson.databind;
}
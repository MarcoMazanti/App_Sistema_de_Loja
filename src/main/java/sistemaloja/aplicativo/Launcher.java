package sistemaloja.aplicativo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/LoginPage.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent, 550, 500));
        stage.setTitle("Gerenciador de Loja");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }
}

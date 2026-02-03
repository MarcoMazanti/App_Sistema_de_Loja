package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HeaderController {
    @FXML
    private ImageView exitIcon;

    @FXML
    private ImageView avatarIcon;

    @FXML
    private void onExitIconClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/LoginPage.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) exitIcon.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.centerOnScreen();
            stage.setMaxHeight(550);
            stage.setMaxWidth(500);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAvatarIconClicked() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sistema de Loja");
        alerta.setHeaderText("Sistema de Loja");
        alerta.setContentText("Bem vindo ao Sistema de Loja!");
        alerta.show();
    }
}

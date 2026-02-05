package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ResetarSenhaController {
    private Alert alerta;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField novaSenhaInput;

    @FXML
    private Button trocarSenhaButton;

    @FXML
    private Hyperlink lembrouSenhaText;

    @FXML
    private void onLembrouSenhaTextClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/LoginPage.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = trocarSenhaButton.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao direcionar ao Login!");
            alerta.show();
        }
    }

    // adicionar endpoint para resetar a senha
}

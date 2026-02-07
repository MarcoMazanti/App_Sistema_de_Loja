package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Empregado.TrocarSenha;
import sistemaloja.aplicativo.Repository.EmpregadoRepository;

public class ResetarSenhaController {
    private Alert alerta;

    @FXML
    public TextField cpfInput;
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField novaSenhaInput;
    @FXML
    public Button trocarSenhaButton;
    @FXML
    public Hyperlink lembrouSenhaText;

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

    @FXML
    private void onTrocarSenhaButtonClick() {
        try {
            EmpregadoRepository empregadoRepository = new EmpregadoRepository();

            TrocarSenha trocarSenha = new TrocarSenha(cpfInput.getText(), emailInput.getText(), novaSenhaInput.getText());

            if (empregadoRepository.trocarSenha(trocarSenha)) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/LoginPage.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = trocarSenhaButton.getScene();
                scene.setRoot(root);

                Stage stage = (Stage) scene.getWindow();
                stage.setMaximized(true);
                stage.show();
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText("Erro ao alterar a senha!");
                alerta.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao direcionar ao Login!");
            alerta.show();
        }
    }
}

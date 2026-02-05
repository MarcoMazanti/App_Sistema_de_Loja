package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Repository.EmpregadoRepository;

public class LoginController {
    private EmpregadoRepository empregadoRepository = new EmpregadoRepository();
    private Alert alerta;

    @FXML
    private TextField cpfInput;

    @FXML
    private PasswordField senhaInput;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink esqueceuSenhaText;

    @FXML
    private void onLoginButtonClick() {
        try {
            if (cpfInput.getText().isBlank() || senhaInput.getText().isBlank()) {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Preencha todos os campos!");
                alerta.show();
            } else {
                Login login = new Login(cpfInput.getText(), senhaInput.getText());

                Object usuario =  empregadoRepository.login(login,1);

                if (usuario != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/HomePage.fxml"));
                    Parent root = fxmlLoader.load();

                    HomeController homeController = fxmlLoader.getController();
                    homeController.setUsuarioLogado((EmpregadoRecordOne) usuario);

                    Scene scene = loginButton.getScene();
                    scene.setRoot(root);

                    Stage stage = (Stage) scene.getWindow();
                    stage.setMaximized(true);
                    stage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao efetuar login!");
            alerta.show();
        }
    }

    @FXML
    private void onEsqueceuSenhaTextClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/ResetPasswordPage.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = loginButton.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao direcionar a página para resetar a senha!");
            alerta.show();
        }
    }
}

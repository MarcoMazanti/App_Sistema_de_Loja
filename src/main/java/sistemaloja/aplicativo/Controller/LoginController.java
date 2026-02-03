package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Repository.EmpregadoRepository;

import java.util.Objects;

public class LoginController {
    private EmpregadoRepository empregadoRepository = new EmpregadoRepository();
    private Alert alerta;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField senhaInput;

    @FXML
    private Button loginButton;

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

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setResizable(true);
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
}

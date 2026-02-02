package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Repository.EmpregadoRepository;

public class LoginController {
    private EmpregadoRepository empregadoRepository = new EmpregadoRepository();
    private Alert alerta;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField senhaInput;

    @FXML
    private void onLoginButtonClick() {
        try {
            if (cpfInput.getText().isBlank() || senhaInput.getText().isBlank()) {
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Preencha todos os campos!");
            } else {
                Login login = new Login(cpfInput.getText(), senhaInput.getText());

                Object usuario =  empregadoRepository.login(login,1);

                if (usuario != null) {
                    alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setContentText("Login efetuado com sucesso!");
                } else {
                    alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setContentText("Login inválido!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao efetuar login!");
        } finally {
            alerta.show();
        }
    }
}

package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Repository.EmpregadoRepository;
import sistemaloja.aplicativo.Repository.FilialRepository;

public class HelloController {
    private FilialRepository filialRepository = new FilialRepository();
    private EmpregadoRepository empregadoRepository = new EmpregadoRepository();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(empregadoRepository.login(new Login("01234567890", "M@rco1234"), 3).toString());
    }
}

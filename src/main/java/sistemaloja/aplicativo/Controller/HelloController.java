package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sistemaloja.aplicativo.Repository.FilialRepository;

public class HelloController {
    private FilialRepository filialRepository = new FilialRepository();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(filialRepository.getAllFiliais(1).toString());
    }
}

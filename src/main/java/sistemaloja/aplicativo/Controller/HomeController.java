package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;


public class HomeController {
    @FXML
    private Label abaFilialLateral;
    @FXML
    private Label abaEmpregadoLateral;
    @FXML
    private Label abaClienteLateral;
    @FXML
    private Label abaFornecedorLateral;
    @FXML
    private Label abaEstoqueLateral;
    @FXML
    private Label abaPagamentoLateral;
    EmpregadoRecordOne usuarioLogado;

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}

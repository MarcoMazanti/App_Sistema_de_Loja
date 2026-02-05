package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;


public class HomeController {
    EmpregadoRecordOne usuarioLogado;

    @FXML
    private void sairAplicativo() {
        System.exit(0);
    }

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}

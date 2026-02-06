package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import javafx.scene.control.Label;


public class HomeController {
    private EmpregadoRecordOne usuarioLogado;

    @FXML
    public Label nomeHeaderLabel;
    @FXML
    public Label nomeSobrenomeLabel;
    @FXML
    public Label cargoLabel;
    @FXML
    public Button novaVendaButton;
    @FXML
    public Label totalVendasLabel;
    @FXML
    public Label totalEmpregadoLabel;
    @FXML
    public Label totalClienteLabel;
    @FXML
    public TableView tabelaPagamentosRecentes;
    @FXML
    public VBox boxLowItem;

    @FXML
    private void sairAplicativo() {
        System.exit(0);
    }

    @FXML
    private void onFilialDisplayClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onEmpregadoDisplayClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onClienteDisplayClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onFornecedorDisplayClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onEstoqueDisplayClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onPagamentoDisplayClicked(MouseEvent mouseEvent) {
    }

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        String nomeCompleto = usuarioLogado.nome();

        String[] nomes = nomeCompleto.split(" ");

        nomeHeaderLabel.setText(String.format("Bem vindo(a) %s %s", nomes[0], nomes[nomes.length - 1]));
        nomeSobrenomeLabel.setText(String.format("%s %s", nomes[0], nomes[nomes.length - 1]));

        cargoLabel.setText(usuarioLogado.cargo());
    }
}

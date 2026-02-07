package sistemaloja.aplicativo.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordTwo;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoRecordTwo;
import sistemaloja.aplicativo.Repository.ClienteRepository;
import sistemaloja.aplicativo.Repository.EstoqueRepository;
import sistemaloja.aplicativo.Repository.FilialRepository;
import sistemaloja.aplicativo.Repository.PagamentoRepository;

import java.util.List;

public class HomeController {
    PagamentoRepository pagamentoRepository = new PagamentoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    FilialRepository filialRepository = new FilialRepository();
    EstoqueRepository estoqueRepository = new EstoqueRepository();

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

        carregarDashboardFinanceiro();
        carregarItensBaixoEstoque();
        //carregarTabelaPagamentosRecentes();
    }

    private void carregarDashboardFinanceiro() {
        new Thread(() -> {
            try {
                Object resultado = (usuarioLogado.cargo().equals("DONO"))
                        ? pagamentoRepository.getAllPagamentoGeneric(2)
                        : pagamentoRepository.getAllPagamentoGenericByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 2);

                List<PagamentoRecordTwo> pagamentos = (resultado instanceof List<?> lista)
                        ? (List<PagamentoRecordTwo>) lista : List.of();

                List<?> clientes = clienteRepository.getAllClientes(3);

                int quantEmpregados = (usuarioLogado.cargo().equals("DONO"))
                        ? (int) filialRepository.getAllFiliais(3).stream()
                        .mapToDouble(item -> Double.parseDouble(((FilialRecordThree) item).quantEmpregados())).sum()
                        : Integer.parseInt(((FilialRecordThree) filialRepository.getFilialById(Integer.parseInt(usuarioLogado.filialId()), 3)).quantEmpregados());

                String[] nomes = usuarioLogado.nome().split(" ");
                String nomeSobrenome = String.join(" ", nomes[0], nomes[nomes.length - 1]);

                double totalPagamentos = pagamentos.stream().mapToDouble(item -> Float.parseFloat(item.precoPago())).sum();

                Platform.runLater(() -> {
                    totalVendasLabel.setText(String.format("R$ %.2f", totalPagamentos));
                    totalEmpregadoLabel.setText(String.valueOf(quantEmpregados));
                    totalClienteLabel.setText(String.valueOf(clientes.size()));
                    nomeHeaderLabel.setText("Bem vindo(a) " + nomeSobrenome);
                    nomeSobrenomeLabel.setText(nomeSobrenome);
                    cargoLabel.setText(usuarioLogado.cargo());
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    /*private void carregarTabelaPagamentosRecentes() {
        new Thread(() -> {
            Object pagamentos = (usuarioLogado.cargo().equals("DONO"))
                    ? pagamentoRepository.getAllPagamentoGeneric(1)
                    : pagamentoRepository.getAllPagamentoGenericByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 1);

            List<PagamentoRecordOne> pagamentosList = (pagamentos instanceof List<?> lista)
                    ? (List<PagamentoRecordOne>) lista
                    : List.of();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDateTime limite = LocalDateTime.now().minusDays(7);

            List<PagamentoRecordOne> listaFinal = pagamentosList.stream().filter(item -> {
                LocalDateTime dataCompra = LocalDateTime.parse(item.dataCompra(), formatter);
                return dataCompra.isAfter(limite);
            }).toList();

            Platform.runLater(() -> {
                System.out.println(listaFinal);
            });
        }).start();
    }*/

    private void carregarItensBaixoEstoque() {
        new Thread(() -> {
            try {
                Object estoques = (usuarioLogado.cargo().equals("DONO"))
                        ? estoqueRepository.getAllEstoques(2)
                        : estoqueRepository.getAllEstoqueByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 2);

                List<EstoqueRecordTwo> estoqueList = (estoques instanceof List<?> lista)
                        ? (List<EstoqueRecordTwo>) lista : List.of();

                List<EstoqueRecordTwo> listaFinal = estoqueList.stream()
                        .filter(e -> Integer.parseInt(e.quantidade()) <= 10).toList();

                Platform.runLater(() -> {
                    boxLowItem.getChildren().clear();
                    listaFinal.forEach(item -> {
                        int qtd = Integer.parseInt(item.quantidade());
                        Label label = new Label(item.nome() + " - " + qtd + " restantes");
                        label.getStyleClass().add("alert");

                        if (qtd < 4) label.setStyle("-fx-text-fill: red");
                        else if (qtd < 6) label.setStyle("-fx-text-fill: orange");
                        else label.setStyle("-fx-text-fill: yellow");

                        HBox box = new HBox(label);
                        box.getStyleClass().add("low-item");

                        boxLowItem.getChildren().add(box);
                    });
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }
}

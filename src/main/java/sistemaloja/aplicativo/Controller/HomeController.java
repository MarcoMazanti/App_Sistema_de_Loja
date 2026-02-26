package sistemaloja.aplicativo.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordTwo;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoRecordOne;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoRecordTwo;
import sistemaloja.aplicativo.Repository.ClienteRepository;
import sistemaloja.aplicativo.Repository.EstoqueRepository;
import sistemaloja.aplicativo.Repository.FilialRepository;
import sistemaloja.aplicativo.Repository.PagamentoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class HomeController {
    PagamentoRepository pagamentoRepository = new PagamentoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    FilialRepository filialRepository = new FilialRepository();
    EstoqueRepository estoqueRepository = new EstoqueRepository();

    private EmpregadoRecordOne usuarioLogado;
    private Alert alerta;

    @FXML
    public Label nomeHeaderLabel;
    @FXML
    public Label totalVendasLabel;
    @FXML
    public Label totalEmpregadoLabel;
    @FXML
    public Label totalClienteLabel;
    @FXML
    public VBox boxLowItem;
    @FXML
    public ListView<String> listaPagamentos;

    @FXML
    private BarraLateralController barraLateralController;

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        barraLateralController.setUsuarioLogado(usuarioLogado);

        carregarDashboardFinanceiro();
        carregarItensBaixoEstoque();

        carregarTabelaPagamentosRecentes(lista -> {
            ObservableList<String> obsList = FXCollections.observableArrayList();
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            lista.forEach(p -> {
                LocalDateTime dataReal = LocalDateTime.parse(p.dataCompra(), parser);

                obsList.add("Código: " + p.codPagamento() + " - Venda: R$ " + p.precoPago()
                        + " - Data: " + dataReal.format(formatter)
                        + " - Status: " + (p.precoTotal().equals(p.precoPago()) ? "PAGO" : "PENDENTE"));
            });

            listaPagamentos.setItems(obsList);

            listaPagamentos.setCellFactory(lv -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        TextFlow flow = new TextFlow();
                        Text textoComum = new Text(item.split("Status:")[0] + "Status: ");
                        textoComum.setStyle("-fx-fill: white");

                        String statusTexto = item.contains("PAGO") ? "PAGO" : "PENDENTE";
                        Text status = new Text(statusTexto);
                        status.getStyleClass().add((statusTexto.equals("PAGO")) ? "pagamento-pago" : "pagamento-pendente");

                        flow.getChildren().addAll(textoComum, status);
                        flow.maxWidthProperty().bind(lv.widthProperty().subtract(40));

                        setGraphic(flow);
                        setText(null);
                    }
                }
            });
        });
    }

    @FXML
    public void adicionarVenda(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/EditObjPage.fxml"));
            Parent root = fxmlLoader.load();

            EditObjController controller = fxmlLoader.getController();
            controller.setCriar(true);
            controller.setTipo("Pagamento");
            controller.setObject(null);
            controller.setUsuarioLogado(usuarioLogado);

            Scene scene = nomeHeaderLabel.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao trocar de página.\n" + e.getMessage());
            alerta.show();
        }
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
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void carregarTabelaPagamentosRecentes(Consumer<List<PagamentoRecordOne>> callback) {
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

            Platform.runLater(() -> callback.accept(listaFinal));
        }).start();
    }

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

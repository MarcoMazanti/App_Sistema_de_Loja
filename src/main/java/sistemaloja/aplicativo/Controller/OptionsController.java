package sistemaloja.aplicativo.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Repository.*;

import java.util.List;
import java.util.function.Consumer;

public class OptionsController {
    FilialRepository filialRepository = new FilialRepository();
    EmpregadoRepository empregadoRepository = new EmpregadoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    FornecedorRepository fornecedorRepository = new FornecedorRepository();
    EstoqueRepository estoqueRepository = new EstoqueRepository();
    PagamentoRepository pagamentoRepository = new PagamentoRepository();

    private EmpregadoRecordOne usuarioLogado;
    private String tipo;
    private Alert alerta;

    @FXML
    public Label tituloPaginaLabel;
    @FXML
    public ListView<String> listaObjetos;

    @FXML
    private BarraLateralController barraLateralController;

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        barraLateralController.setUsuarioLogado(usuarioLogado);

        carregarDadosPagina();
        if (!tipo.isEmpty()) {
            carregarObjectList(list -> {
                ObservableList<String> obsList = FXCollections.observableArrayList();
                list.forEach(objeto -> obsList.add(objeto.toString()));

                listaObjetos.setItems(obsList);

                listaObjetos.setCellFactory(lv -> new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);
                            setGraphic(null);

                            setStyle("-fx-text-fill: white;");
                        }
                    }
                });
            });
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao Obter o Tipo da Página!");
            alerta.show();
        }
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private void carregarDadosPagina() {
        if (usuarioLogado != null) {
            tituloPaginaLabel.setText("Gerenciamento de " + tipo);
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao Obter o Usuário Logado!");
            alerta.show();
        }
    }

    private void carregarObjectList(Consumer<List<?>> callback) {
        new Thread(() -> {
            List<?> objectList;

            if (usuarioLogado.cargo().equals("DONO")) {
                objectList = switch (tipo) {
                    case "Filial" -> filialRepository.getAllFiliais(1);
                    case "Empregado" -> empregadoRepository.getAllEmpregados(1);
                    case "Cliente" -> clienteRepository.getAllClientes(1);
                    case "Fornecedor" -> fornecedorRepository.getAllFornecedores(1);
                    case "Estoque" -> estoqueRepository.getAllEstoques(1);
                    case "Pagamento" -> pagamentoRepository.getAllPagamentoGeneric(1);
                    default -> List.of();
                };
            } else {
                objectList = switch (tipo) {
                    case "Filial" -> filialRepository.getAllFiliais(1);
                    case "Empregado" -> empregadoRepository.getEmpregadoByFilialId(Integer.parseInt(usuarioLogado.filialId()), 1);
                    case "Cliente" -> clienteRepository.getAllClientes(1);
                    case "Fornecedor" -> fornecedorRepository.getAllFornecedores(1);
                    case "Estoque" -> estoqueRepository.getAllEstoqueByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 1);
                    case "Pagamento" -> pagamentoRepository.getAllPagamentoGenericByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 1);
                    default -> List.of();
                };
            }

            Platform.runLater(() -> callback.accept(objectList));
        }).start();
    }
}

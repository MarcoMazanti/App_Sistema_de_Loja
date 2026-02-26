package sistemaloja.aplicativo.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordOne;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoRecordOne;
import sistemaloja.aplicativo.Repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    public Label boxOneLabel;
    @FXML
    public Label boxOneValueLabel;
    @FXML
    public Label boxTwoLabel;
    @FXML
    public Label boxTwoValueLabel;
    @FXML
    public Label boxThreeLabel;
    @FXML
    public Label boxThreeValueLabel;
    @FXML
    public TextField textoPesquisa;
    @FXML
    public ListView<Object> listaObjetos;
    @FXML
    public ImageView boxEdit;
    @FXML
    public ImageView boxDelete;

    @FXML
    private BarraLateralController barraLateralController;

    @FXML
    public void adicionarObj(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/EditObjPage.fxml"));
            Parent root = fxmlLoader.load();

            EditObjController controller = fxmlLoader.getController();
            controller.setTipo(tipo);
            controller.setCriar(true);
            controller.setUsuarioLogado(usuarioLogado);

            Scene scene = tituloPaginaLabel.getScene();
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

    @FXML
    public void editarObj(MouseEvent mouseEvent) {
        if (listaObjetos.getSelectionModel().getSelectedItem() != null) {
            Object object = listaObjetos.getSelectionModel().getSelectedItem();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/EditObjPage.fxml"));
                Parent root = fxmlLoader.load();

                EditObjController controller = fxmlLoader.getController();
                controller.setTipo(tipo);
                controller.setObject(object);
                controller.setUsuarioLogado(usuarioLogado);

                Scene scene = tituloPaginaLabel.getScene();
                scene.setRoot(root);

                Stage stage = (Stage) scene.getWindow();
                stage.setMaximized(true);
                stage.show();
            } catch (Exception e) {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText("Erro ao trocar de página.");
                alerta.show();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR, "Selecione um objeto para editar.", ButtonType.OK);
            alerta.showAndWait();
        }
    }

    @FXML
    public void deletarObj(MouseEvent mouseEvent) {
        if (listaObjetos.getSelectionModel().getSelectedItem() != null) {
            Object object = listaObjetos.getSelectionModel().getSelectedItem();

            alerta = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente deletar esse objeto?", ButtonType.YES, ButtonType.NO);
            alerta.setHeaderText("Confirmação de Exclusão");

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
                switch (tipo) {
                    case "Filial" -> {
                        FilialRecordOne filial = (FilialRecordOne) object;
                        filialRepository.deleteFilial(Integer.parseInt(usuarioLogado.id()), Integer.parseInt(filial.id()), 1);
                    }
                    case "Empregado" -> {
                        EmpregadoRecordOne empregado = (EmpregadoRecordOne) object;
                        empregadoRepository.deleteEmpregado(Integer.parseInt(usuarioLogado.id()), Integer.parseInt(empregado.id()), 1);
                    }
                    case "Cliente" -> {
                        ClienteRecordOne cliente = (ClienteRecordOne) object;
                        clienteRepository.deleteCliente(Integer.parseInt(usuarioLogado.id()), Integer.parseInt(cliente.id()), 1);
                    }
                    case "Fornecedor" -> {
                        FornecedorRecordOne fornecedor = (FornecedorRecordOne) object;
                        fornecedorRepository.deleteFornecedor(Integer.parseInt(usuarioLogado.id()), Integer.parseInt(fornecedor.id()), 1);
                    }
                    case "Estoque" -> {
                        EstoqueRecordOne estoque = (EstoqueRecordOne) object;
                        estoqueRepository.deleteEstoque(Integer.parseInt(estoque.id()), 1);
                    }
                }
                listaObjetos.getItems().clear();
                mostrarObjectList();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR, "Selecione um objeto para deletar.", ButtonType.OK);
            alerta.showAndWait();
        }
    }

    @FXML
    public void pesquisar(MouseEvent mouseEvent) {
        if (!textoPesquisa.getText().isEmpty()) {
            listaObjetos.getItems().forEach(item -> {
                String itemText = item.toString();
                if (itemText.toLowerCase().contains(textoPesquisa.getText().toLowerCase())) {
                    listaObjetos.getSelectionModel().select(item);
                }
            });
        } else {
            mostrarObjectList();
        }
    }

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        barraLateralController.setUsuarioLogado(usuarioLogado);

        carregarDadosPagina();
        if (!tipo.isEmpty()) {
            mostrarObjectList();
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

            boxOneLabel.setText("Total de " + tipo);

            if (tipo.equals("Pagamento")) {
                boxDelete.setVisible(false);
            } else {
                boxDelete.setVisible(true);
            }
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

            Platform.runLater(() -> {
                switch (tipo) {
                    case "Filial" -> {
                        boxTwoLabel.setText("Filial de Destaque");
                        boxTwoValueLabel.setText((objectList.stream()
                                .filter(FilialRecordOne.class::isInstance).map(FilialRecordOne.class::cast)
                                .max(Comparator.comparing(FilialRecordOne::quantEmpregados))
                                .map(f -> String.valueOf(f.id())).orElse("0")));
                        boxThreeLabel.setText("Quantidade de Empregados");
                        boxThreeValueLabel.setText(String.valueOf(objectList.stream()
                                .filter(item -> item instanceof FilialRecordOne)
                                .mapToInt(f -> Integer.parseInt(((FilialRecordOne) f).quantEmpregados()))
                                .sum()));
                    }
                    case "Empregado" -> {
                        boxTwoLabel.setText("Aniversariante Mais Próximo");
                        boxTwoValueLabel.setText(objectList.stream()
                                .filter(EmpregadoRecordOne.class::isInstance)
                                .map(EmpregadoRecordOne.class::cast)
                                .min(Comparator.comparingLong(e -> {
                                    LocalDate hoje = LocalDate.now();
                                    DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    LocalDate nascimento = LocalDate.parse(e.aniversario(), parser);

                                    LocalDate proximoAniv = nascimento.withYear(hoje.getYear());
                                    if (proximoAniv.isBefore(hoje) || proximoAniv.isEqual(hoje)) {
                                        proximoAniv = proximoAniv.plusYears(1);
                                    }

                                    return ChronoUnit.DAYS.between(hoje, proximoAniv);
                                }))
                                .map(EmpregadoRecordOne::nome).orElse("Nenhum"));

                        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                        LocalDate hoje = LocalDate.now();
                        LocalDate admissao = LocalDateTime.parse(usuarioLogado.dataAdimissao(), parser).toLocalDate();

                        Period periodo = Period.between(admissao, hoje);

                        int anos = periodo.getYears();
                        int meses = periodo.getMonths();
                        int dias = periodo.getDays();

                        boxThreeLabel.setText("Seu Tempo de Casa");

                        if (anos > 0) {
                            boxThreeValueLabel.setText(anos + " anos e " + meses + " meses");
                        } else if (meses > 0) {
                            boxThreeValueLabel.setText(meses + " meses de empresa");
                        } else {
                            boxThreeValueLabel.setText(dias + " dias de empresa");
                        }
                    }
                    case "Cliente" -> {
                        boxTwoLabel.setText("Estado com mais Clientes");
                        boxTwoValueLabel.setText(objectList.stream()
                                .filter(ClienteRecordOne.class::isInstance).map(ClienteRecordOne.class::cast)
                                .collect(Collectors.groupingBy(ClienteRecordOne::codEstado, Collectors.counting()))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                                .orElse("Nenhum"));

                        try {
                            List<PagamentoRecordOne> listaPagamento = (List<PagamentoRecordOne>) pagamentoRepository.getAllPagamentoGeneric(1);
                            int idClienteFavorito = Math.toIntExact(Long.parseLong(listaPagamento.stream()
                                    .collect(Collectors.groupingBy(PagamentoRecordOne::idCliente, Collectors.counting()))
                                    .entrySet().stream()
                                    .max(Map.Entry.comparingByValue())
                                    .map(Map.Entry::getKey)
                                    .orElse(String.valueOf(0))));

                            boxThreeLabel.setText("Cliente Favorito");
                            boxThreeValueLabel.setText(objectList.stream()
                                    .filter(ClienteRecordOne.class::isInstance)
                                    .map(ClienteRecordOne.class::cast)
                                    .filter(c -> Integer.parseInt(c.id()) == idClienteFavorito)
                                    .map(ClienteRecordOne::nome)
                                    .findFirst()
                                    .orElse("Nenhum"));
                        } catch(Exception e) {
                            alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setContentText("Erro ao Obter os Pagamentos!");
                            alerta.show();
                        }
                    }
                    case "Fornecedor" -> {
                        boxTwoLabel.setText("Estado com mais Fornecedores");
                        boxTwoValueLabel.setText(objectList.stream()
                                .filter(FornecedorRecordOne.class::isInstance).map(FornecedorRecordOne.class::cast)
                                .collect(Collectors.groupingBy(FornecedorRecordOne::codEstado, Collectors.counting()))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                                .orElse("Nenhum"));


                        try {
                            List<EstoqueRecordOne> listaEstoque = (List<EstoqueRecordOne>) estoqueRepository.getAllEstoques(1);
                            int idFornecedorFavorito = Math.toIntExact(Long.parseLong(listaEstoque.stream()
                                    .collect(Collectors.groupingBy(EstoqueRecordOne::idFornecedor, Collectors.counting()))
                                    .entrySet().stream()
                                    .max(Map.Entry.comparingByValue())
                                    .map(Map.Entry::getKey)
                                    .orElse(String.valueOf(0))));

                            boxThreeLabel.setText("Fornecedor Favorito");
                            boxThreeValueLabel.setText(objectList.stream()
                                    .filter(FornecedorRecordOne.class::isInstance)
                                    .map(FornecedorRecordOne.class::cast)
                                    .filter(c -> Integer.parseInt(c.id()) == idFornecedorFavorito)
                                    .map(FornecedorRecordOne::nome)
                                    .findFirst()
                                    .orElse("Nenhum"));
                        } catch(Exception e) {
                            alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setContentText("Erro ao Obter os Pagamentos!");
                            alerta.show();
                        }
                    }
                    case "Estoque" -> {
                        boxTwoLabel.setText("Itens Baixos em Estoque");
                        boxTwoValueLabel.setText(String.valueOf(objectList.stream()
                                .filter(item -> item instanceof EstoqueRecordOne e && e.emBaixoEstoque())
                                .count()));
                        boxThreeLabel.setText("Itens sem Estoque");
                        boxThreeValueLabel.setText(String.valueOf(objectList.stream()
                                .filter(item -> item instanceof EstoqueRecordOne e && e.emFalta())
                                .count()));
                    }
                    case "Pagamento" -> {
                        boxTwoLabel.setText("Total Pago");
                        boxTwoValueLabel.setText(String.format("R$ %.2f", objectList.stream()
                                .mapToDouble(item -> Float.parseFloat(((PagamentoRecordOne) item).precoPago())).sum()));
                        boxThreeLabel.setText("Pagamentos em Aberto");
                        boxThreeValueLabel.setText(String.valueOf(objectList.stream()
                                .filter(item -> item instanceof PagamentoRecordOne p && !p.precoTotal().equals(p.precoPago()))
                                .count()));
                    }
                }

                boxOneValueLabel.setText(String.valueOf(objectList.size()));
                callback.accept(objectList);
            });
        }).start();
    }

    public void mostrarObjectList() {
        carregarObjectList(list -> {
            ObservableList<Object> obsList = FXCollections.observableArrayList(list);
            listaObjetos.setItems(obsList);

            listaObjetos.setCellFactory(lv -> new ListCell<Object>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);

                        HBox hBox = new HBox();
                        VBox boxEsquerda = new VBox();
                        Separator separador = new Separator();
                        VBox boxCentral = new VBox();
                        VBox boxDireita = new VBox();

                        hBox.setSpacing(20);
                        hBox.setAlignment(Pos.CENTER);
                        hBox.setPadding(new Insets(10, 20, 10, 20));

                        separador.setOrientation(Orientation.VERTICAL);

                        boxEsquerda.setAlignment(Pos.CENTER);
                        boxCentral.setAlignment(Pos.CENTER);
                        boxDireita.setAlignment(Pos.CENTER);

                        boxEsquerda.setMinWidth(300);
                        boxCentral.setMinWidth(200);

                        try {
                            switch (tipo) {
                                case "Filial" -> {
                                    FilialRecordOne filial = (FilialRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label cnpjLabel = new Label("CNPJ: ");
                                    Label telefoneLabel = new Label("Telefone: ");
                                    Label quantEmpregadosLabel = new Label("Quantidade de Empregados: ");
                                    Label fullAdressLabel = new Label("Endereço: ");
                                    Label codCountryLabel = new Label("Código do País: ");
                                    Label codEstadoLabel = new Label("Código do Estado: ");
                                    Label codCidadeLabel = new Label("Código da Cidade: ");
                                    Label codFilialLabel = new Label("Código da Filial: ");
                                    idLabel.getStyleClass().add("muted");
                                    cnpjLabel.getStyleClass().add("muted");
                                    telefoneLabel.getStyleClass().add("muted");
                                    quantEmpregadosLabel.getStyleClass().add("muted");
                                    fullAdressLabel.getStyleClass().add("muted");
                                    codCountryLabel.getStyleClass().add("muted");
                                    codEstadoLabel.getStyleClass().add("muted");
                                    codCidadeLabel.getStyleClass().add("muted");
                                    codFilialLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(filial.id());
                                    Label cnpjValorLabel = new Label(filial.cnpj());
                                    Label telefoneValorLabel = new Label(filial.telefone());
                                    Label quantEmpregadosValorLabel = new Label(filial.quantEmpregados());
                                    Label fullAdressValorLabel = new Label(filial.fullAdress());
                                    Label codCountryValorLabel = new Label(filial.codCountry());
                                    Label codEstadoValorLabel = new Label(filial.codEstado());
                                    Label codCidadeValorLabel = new Label(filial.codCidade());
                                    Label codFilialValorLabel = new Label(filial.codFilial());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    cnpjValorLabel.setStyle("-fx-text-fill: white;");
                                    telefoneValorLabel.setStyle("-fx-text-fill: white;");
                                    quantEmpregadosValorLabel.setStyle("-fx-text-fill: white;");
                                    fullAdressValorLabel.setStyle("-fx-text-fill: white;");
                                    codCountryValorLabel.setStyle("-fx-text-fill: white;");
                                    codEstadoValorLabel.setStyle("-fx-text-fill: white;");
                                    codCidadeValorLabel.setStyle("-fx-text-fill: white;");
                                    codFilialValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox cnpjBox = new HBox(cnpjLabel, cnpjValorLabel);
                                    HBox telefoneBox = new HBox(telefoneLabel, telefoneValorLabel);
                                    HBox quantEmpregadosBox = new HBox(quantEmpregadosLabel, quantEmpregadosValorLabel);
                                    HBox fullAdressBox = new HBox(fullAdressLabel, fullAdressValorLabel);
                                    HBox codCountryBox = new HBox(codCountryLabel, codCountryValorLabel);
                                    HBox codEstadoBox = new HBox(codEstadoLabel, codEstadoValorLabel);
                                    HBox codCidadeBox = new HBox(codCidadeLabel, codCidadeValorLabel);
                                    HBox codFilialBox = new HBox(codFilialLabel, codFilialValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, cnpjBox, telefoneBox, quantEmpregadosBox);
                                    boxDireita.getChildren().addAll(fullAdressBox, codCountryBox, codEstadoBox, codCidadeBox, codFilialBox);
                                }
                                case "Empregado" -> {
                                    EmpregadoRecordOne empregado = (EmpregadoRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label nomeLabel = new Label("Nome: ");
                                    Label cnpjLabel = new Label("CPF: ");
                                    Label emailLabel = new Label("Email: ");
                                    Label telefoneLabel = new Label("Telefone: ");
                                    Label salarioLabel = new Label("Salário: R$ ");
                                    Label cargoLabel = new Label("Cargo: ");
                                    Label filialIdLabel = new Label("Filial ID: ");
                                    Label aniversarioLabel = new Label("Aniversário: ");
                                    Label dataAdimissaoLabel = new Label("Data Admissão: ");
                                    Label codEmpregadoLabel = new Label("Código do Empregado: ");
                                    idLabel.getStyleClass().add("muted");
                                    nomeLabel.getStyleClass().add("muted");
                                    cnpjLabel.getStyleClass().add("muted");
                                    emailLabel.getStyleClass().add("muted");
                                    telefoneLabel.getStyleClass().add("muted");
                                    salarioLabel.getStyleClass().add("muted");
                                    cargoLabel.getStyleClass().add("muted");
                                    filialIdLabel.getStyleClass().add("muted");
                                    aniversarioLabel.getStyleClass().add("muted");
                                    dataAdimissaoLabel.getStyleClass().add("muted");
                                    codEmpregadoLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(empregado.id());
                                    Label nomeValorLabel = new Label(empregado.nome());
                                    Label cnpjValorLabel = new Label(empregado.cpf());
                                    Label emailValorLabel = new Label(empregado.email());
                                    Label telefoneValorLabel = new Label(empregado.telefone());
                                    Label salarioValorLabel = new Label(empregado.salario());
                                    Label cargoValorLabel = new Label(empregado.cargo());
                                    Label filialIdValorLabel = new Label(empregado.filialId());
                                    Label aniversarioValorLabel = new Label(empregado.aniversario());
                                    Label dataAdimissaoValorLabel = new Label(empregado.dataAdimissao());
                                    Label codEmpregadoValorLabel = new Label(empregado.codEmpregado());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    nomeValorLabel.setStyle("-fx-text-fill: white;");
                                    cnpjValorLabel.setStyle("-fx-text-fill: white;");
                                    emailValorLabel.setStyle("-fx-text-fill: white;");
                                    telefoneValorLabel.setStyle("-fx-text-fill: white;");
                                    salarioValorLabel.setStyle("-fx-text-fill: white;");
                                    cargoValorLabel.setStyle("-fx-text-fill: white;");
                                    filialIdValorLabel.setStyle("-fx-text-fill: white;");
                                    aniversarioValorLabel.setStyle("-fx-text-fill: white;");
                                    dataAdimissaoValorLabel.setStyle("-fx-text-fill: white;");
                                    codEmpregadoValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox nomeBox = new HBox(nomeLabel, nomeValorLabel);
                                    HBox cnpjBox = new HBox(cnpjLabel, cnpjValorLabel);
                                    HBox emailBox = new HBox(emailLabel, emailValorLabel);
                                    HBox telefoneBox = new HBox(telefoneLabel, telefoneValorLabel);
                                    HBox salarioBox = new HBox(salarioLabel, salarioValorLabel);
                                    HBox cargoBox = new HBox(cargoLabel, cargoValorLabel);
                                    HBox filialIdBox = new HBox(filialIdLabel, filialIdValorLabel);
                                    HBox aniversarioBox = new HBox(aniversarioLabel, aniversarioValorLabel);
                                    HBox dataAdimissaoBox = new HBox(dataAdimissaoLabel, dataAdimissaoValorLabel);
                                    HBox codEmpregadoBox = new HBox(codEmpregadoLabel, codEmpregadoValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, nomeBox, cnpjBox, emailBox, telefoneBox);
                                    boxCentral.getChildren().addAll(salarioBox, cargoBox, filialIdBox);
                                    boxDireita.getChildren().addAll(aniversarioBox, dataAdimissaoBox, codEmpregadoBox);
                                }
                                case "Cliente" -> {
                                    ClienteRecordOne cliente = (ClienteRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label nomeLabel = new Label("Nome: ");
                                    Label cpfOrCnpjLabel = new Label("CPF ou CNPJ: ");
                                    Label emailLabel = new Label("Email: ");
                                    Label telefoneLabel = new Label("Telefone: ");
                                    Label fullAdressLabel = new Label("Endereço: ");
                                    Label codCountryLabel = new Label("Código do País: ");
                                    Label codEstadoLabel = new Label("Código do Estado: ");
                                    Label codCidadeLabel = new Label("Código da Cidade: ");
                                    Label codClienteLabel = new Label("Código do CLiente: ");
                                    idLabel.getStyleClass().add("muted");
                                    nomeLabel.getStyleClass().add("muted");
                                    cpfOrCnpjLabel.getStyleClass().add("muted");
                                    emailLabel.getStyleClass().add("muted");
                                    telefoneLabel.getStyleClass().add("muted");
                                    fullAdressLabel.getStyleClass().add("muted");
                                    codCountryLabel.getStyleClass().add("muted");
                                    codEstadoLabel.getStyleClass().add("muted");
                                    codCidadeLabel.getStyleClass().add("muted");
                                    codClienteLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(cliente.id());
                                    Label nomeValorLabel = new Label(cliente.nome());
                                    Label cpfOrCnpjValorLabel = new Label(cliente.cpfOrCnpj());
                                    Label emailValorLabel = new Label(cliente.email());
                                    Label telefoneValorLabel = new Label(cliente.telefone());
                                    Label fullAdressValorLabel = new Label(cliente.fullAdress());
                                    Label codCountryValorLabel = new Label(cliente.codCountry());
                                    Label codEstadoValorLabel = new Label(cliente.codEstado());
                                    Label codCidadeValorLabel = new Label(cliente.codCidade());
                                    Label codClienteValorLabel = new Label(cliente.codCliente());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    nomeValorLabel.setStyle("-fx-text-fill: white;");
                                    cpfOrCnpjValorLabel.setStyle("-fx-text-fill: white;");
                                    emailValorLabel.setStyle("-fx-text-fill: white;");
                                    telefoneValorLabel.setStyle("-fx-text-fill: white;");
                                    fullAdressValorLabel.setStyle("-fx-text-fill: white;");
                                    codCountryValorLabel.setStyle("-fx-text-fill: white;");
                                    codEstadoValorLabel.setStyle("-fx-text-fill: white;");
                                    codCidadeValorLabel.setStyle("-fx-text-fill: white;");
                                    codClienteValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox nomeBox = new HBox(nomeLabel, nomeValorLabel);
                                    HBox cpfOrCnpjBox = new HBox(cpfOrCnpjLabel, cpfOrCnpjValorLabel);
                                    HBox emailBox = new HBox(emailLabel, emailValorLabel);
                                    HBox telefoneBox = new HBox(telefoneLabel, telefoneValorLabel);
                                    HBox fullAdressBox = new HBox(fullAdressLabel, fullAdressValorLabel);
                                    HBox codCountryBox = new HBox(codCountryLabel, codCountryValorLabel);
                                    HBox codEstadoBox = new HBox(codEstadoLabel, codEstadoValorLabel);
                                    HBox codCidadeBox = new HBox(codCidadeLabel, codCidadeValorLabel);
                                    HBox codClienteBox = new HBox(codClienteLabel, codClienteValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, nomeBox, cpfOrCnpjBox, emailBox, telefoneBox);
                                    boxDireita.getChildren().addAll(fullAdressBox, codCountryBox, codEstadoBox, codCidadeBox, codClienteBox);
                                }
                                case "Fornecedor" -> {
                                    FornecedorRecordOne fornecedor = (FornecedorRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label nomeLabel = new Label("Nome: ");
                                    Label cpfOrCnpjLabel = new Label("CPF ou CNPJ: ");
                                    Label emailLabel = new Label("Email: ");
                                    Label telefoneLabel = new Label("Telefone: ");
                                    Label fullAdressLabel = new Label("Endereço: ");
                                    Label codCountryLabel = new Label("Código do País: ");
                                    Label codEstadoLabel = new Label("Código do Estado: ");
                                    Label codCidadeLabel = new Label("Código da Cidade: ");
                                    Label codFornecedorLabel = new Label("Código do Fornecedor: ");
                                    idLabel.getStyleClass().add("muted");
                                    nomeLabel.getStyleClass().add("muted");
                                    cpfOrCnpjLabel.getStyleClass().add("muted");
                                    emailLabel.getStyleClass().add("muted");
                                    telefoneLabel.getStyleClass().add("muted");
                                    fullAdressLabel.getStyleClass().add("muted");
                                    codCountryLabel.getStyleClass().add("muted");
                                    codEstadoLabel.getStyleClass().add("muted");
                                    codCidadeLabel.getStyleClass().add("muted");
                                    codFornecedorLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(fornecedor.id());
                                    Label nomeValorLabel = new Label(fornecedor.nome());
                                    Label cpfOrCnpjValorLabel = new Label(fornecedor.cpfOrCnpj());
                                    Label emailValorLabel = new Label(fornecedor.email());
                                    Label telefoneValorLabel = new Label(fornecedor.telefone());
                                    Label fullAdressValorLabel = new Label(fornecedor.fullAdress());
                                    Label codCountryValorLabel = new Label(fornecedor.codCountry());
                                    Label codEstadoValorLabel = new Label(fornecedor.codEstado());
                                    Label codCidadeValorLabel = new Label(fornecedor.codCidade());
                                    Label codFornecedorValorLabel = new Label(fornecedor.codFornecedor());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    nomeValorLabel.setStyle("-fx-text-fill: white;");
                                    cpfOrCnpjValorLabel.setStyle("-fx-text-fill: white;");
                                    emailValorLabel.setStyle("-fx-text-fill: white;");
                                    telefoneValorLabel.setStyle("-fx-text-fill: white;");
                                    fullAdressValorLabel.setStyle("-fx-text-fill: white;");
                                    codCountryValorLabel.setStyle("-fx-text-fill: white;");
                                    codEstadoValorLabel.setStyle("-fx-text-fill: white;");
                                    codCidadeValorLabel.setStyle("-fx-text-fill: white;");
                                    codFornecedorValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox nomeBox = new HBox(nomeLabel, nomeValorLabel);
                                    HBox cpfOrCnpjBox = new HBox(cpfOrCnpjLabel, cpfOrCnpjValorLabel);
                                    HBox emailBox = new HBox(emailLabel, emailValorLabel);
                                    HBox telefoneBox = new HBox(telefoneLabel, telefoneValorLabel);
                                    HBox fullAdressBox = new HBox(fullAdressLabel, fullAdressValorLabel);
                                    HBox codCountryBox = new HBox(codCountryLabel, codCountryValorLabel);
                                    HBox codEstadoBox = new HBox(codEstadoLabel, codEstadoValorLabel);
                                    HBox codCidadeBox = new HBox(codCidadeLabel, codCidadeValorLabel);
                                    HBox codFornecedorBox = new HBox(codFornecedorLabel, codFornecedorValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, nomeBox, cpfOrCnpjBox, emailBox, telefoneBox);
                                    boxDireita.getChildren().addAll(fullAdressBox, codCountryBox, codEstadoBox, codCidadeBox, codFornecedorBox);
                                }
                                case "Estoque" -> {
                                    EstoqueRecordOne estoque = (EstoqueRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label nomeLabel = new Label("Nome: ");
                                    Label idFilialLabel = new Label("Filial ID: ");
                                    Label idFornecedorLabel = new Label("Fornecedor ID: ");
                                    Label precoLabel = new Label("Preço: R$ ");
                                    Label quantidadeLabel = new Label("Quantidade: ");
                                    Label descricaoLabel = new Label("Descrição: ");
                                    Label codItemLabel = new Label("Código do Item: ");
                                    idLabel.getStyleClass().add("muted");
                                    nomeLabel.getStyleClass().add("muted");
                                    idFilialLabel.getStyleClass().add("muted");
                                    idFornecedorLabel.getStyleClass().add("muted");
                                    precoLabel.getStyleClass().add("muted");
                                    quantidadeLabel.getStyleClass().add("muted");
                                    descricaoLabel.getStyleClass().add("muted");
                                    codItemLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(estoque.id());
                                    Label nomeValorLabel = new Label(estoque.nome());
                                    Label idFilialValorLabel = new Label(estoque.idFilial());
                                    Label idFornecedorValorLabel = new Label(estoque.idFornecedor());
                                    Label precoValorLabel = new Label(estoque.preco());
                                    Label quantidadeValorLabel = new Label(estoque.quantidade());
                                    Label descricaoValorLabel = new Label(estoque.descricao());
                                    Label codItemValorLabel = new Label(estoque.codItem());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    nomeValorLabel.setStyle("-fx-text-fill: white;");
                                    idFilialValorLabel.setStyle("-fx-text-fill: white;");
                                    idFornecedorValorLabel.setStyle("-fx-text-fill: white;");
                                    precoValorLabel.setStyle("-fx-text-fill: white;");
                                    quantidadeValorLabel.setStyle("-fx-text-fill: white;");
                                    descricaoValorLabel.setStyle("-fx-text-fill: white;");
                                    codItemValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox nomeBox = new HBox(nomeLabel, nomeValorLabel);
                                    HBox idFilialBox = new HBox(idFilialLabel, idFilialValorLabel);
                                    HBox idFornecedorBox = new HBox(idFornecedorLabel, idFornecedorValorLabel);
                                    HBox precoBox = new HBox(precoLabel, precoValorLabel);
                                    HBox quantidadeBox = new HBox(quantidadeLabel, quantidadeValorLabel);
                                    HBox descricaoBox = new HBox(descricaoLabel, descricaoValorLabel);
                                    HBox codItemBox = new HBox(codItemLabel, codItemValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, nomeBox, idFilialBox, idFornecedorBox);
                                    boxDireita.getChildren().addAll(precoBox, quantidadeBox, descricaoBox, codItemBox);
                                }
                                case "Pagamento" -> {
                                    PagamentoRecordOne pagamento = (PagamentoRecordOne) item;

                                    Label idLabel = new Label("ID: ");
                                    Label idClienteLabel = new Label("Cliente ID: ");
                                    Label idFilialLabel = new Label("Filial ID: ");
                                    Label precoTotalLabel = new Label("Preço total: R$ ");
                                    Label precoPagoLabel = new Label("Preço pago: R$ ");
                                    Label dataCompraLabel = new Label("Data da compra: ");
                                    Label codPagamentoLabel = new Label("Código do Pagamento: ");
                                    idLabel.getStyleClass().add("muted");
                                    idClienteLabel.getStyleClass().add("muted");
                                    idFilialLabel.getStyleClass().add("muted");
                                    precoTotalLabel.getStyleClass().add("muted");
                                    precoPagoLabel.getStyleClass().add("muted");
                                    dataCompraLabel.getStyleClass().add("muted");
                                    codPagamentoLabel.getStyleClass().add("muted");

                                    Label idValorLabel = new Label(pagamento.id());
                                    Label idClienteValorLabel = new Label(pagamento.idCliente());
                                    Label idFilialValorLabel = new Label(pagamento.idFilial());
                                    Label precoTotalValorLabel = new Label(pagamento.precoTotal());
                                    Label precoPagoValorLabel = new Label(pagamento.precoPago());
                                    Label dataCompraValorLabel = new Label(pagamento.dataCompra());
                                    Label codPagamentoValorLabel = new Label(pagamento.codPagamento());
                                    idValorLabel.setStyle("-fx-text-fill: white;");
                                    idClienteValorLabel.setStyle("-fx-text-fill: white;");
                                    idFilialValorLabel.setStyle("-fx-text-fill: white;");
                                    precoTotalValorLabel.setStyle("-fx-text-fill: white;");
                                    precoPagoValorLabel.setStyle("-fx-text-fill: white;");
                                    dataCompraValorLabel.setStyle("-fx-text-fill: white;");
                                    codPagamentoValorLabel.setStyle("-fx-text-fill: white;");

                                    HBox idBox = new HBox(idLabel, idValorLabel);
                                    HBox idClienteBox = new HBox(idClienteLabel, idClienteValorLabel);
                                    HBox idFilialBox = new HBox(idFilialLabel, idFilialValorLabel);
                                    HBox precoTotalBox = new HBox(precoTotalLabel, precoTotalValorLabel);
                                    HBox precoPagoBox = new HBox(precoPagoLabel, precoPagoValorLabel);
                                    HBox dataCompraBox = new HBox(dataCompraLabel, dataCompraValorLabel);
                                    HBox codPagamentoBox = new HBox(codPagamentoLabel, codPagamentoValorLabel);

                                    boxEsquerda.getChildren().addAll(idBox, idClienteBox, idFilialBox);
                                    boxDireita.getChildren().addAll(precoTotalBox, precoPagoBox, dataCompraBox, codPagamentoBox);
                                }
                                default -> setText(item.toString());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        hBox.getChildren().clear();

                        if (!boxCentral.getChildren().isEmpty()) {
                            Separator separadorTwo = new Separator();
                            separadorTwo.setOrientation(Orientation.VERTICAL);

                            hBox.getChildren().addAll(boxEsquerda,separador, boxCentral, separadorTwo, boxDireita);
                        } else {
                            hBox.getChildren().addAll(boxEsquerda, separador, boxDireita);
                        }

                        setGraphic(hBox);
                    }
                }
            });
        });
    }
}

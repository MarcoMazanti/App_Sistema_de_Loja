package sistemaloja.aplicativo.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordOne;
import sistemaloja.aplicativo.Entity.Pagamento.ItemPagamentoRecordOne;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoPayloadRecord;
import sistemaloja.aplicativo.Entity.Pagamento.PagamentoRecordOne;
import sistemaloja.aplicativo.Repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class EditObjController {
    FilialRepository filialRepository = new FilialRepository();
    EmpregadoRepository empregadoRepository = new EmpregadoRepository();
    ClienteRepository clienteRepository = new ClienteRepository();
    FornecedorRepository fornecedorRepository = new FornecedorRepository();
    EstoqueRepository estoqueRepository = new EstoqueRepository();
    PagamentoRepository pagamentoRepository = new PagamentoRepository();
    private Map<String, Control> campos = new HashMap<>();

    private EmpregadoRecordOne usuarioLogado;
    private String tipo;
    private Object object;
    private boolean criar = false;
    private List<ItemPagamentoRecordOne> itensPagamento = new ArrayList<>();
    private Alert alerta;

    @FXML
    public Label tituloLabel;
    @FXML
    public HBox mainBox;
    @FXML
    public VBox boxImutavel;
    @FXML
    public VBox boxMutavel;

    @FXML
    private BarraLateralController barraLateralController;

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        barraLateralController.setUsuarioLogado(usuarioLogado);

        gerarFXML();

        tituloLabel.setText(tipo);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setCriar(boolean criar) {
        this.criar = criar;
    }

    @FXML
    public void enviarButton(MouseEvent mouseEvent) {
        salvarObj();
        alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText("Objeto manuseado com sucesso!");
        alerta.showAndWait();
        cancelarButton(mouseEvent);
    }

    @FXML
    public void cancelarButton(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/HomePage.fxml"));
            Parent root = fxmlLoader.load();

            HomeController homeController = fxmlLoader.getController();
            homeController.setUsuarioLogado(usuarioLogado);

            Scene scene = tituloLabel.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao direcionar ao Home!");
            alerta.show();
        }
    }

    private void gerarFXML() {
        if (criar) {
            switch (tipo) {
                case "Filial" -> criarFilial();
                case "Empregado" -> criarEmpregado();
                case "Cliente", "Fornecedor" -> criarClienteFornecedor();
                case "Estoque" -> criarEstoque();
                case "Pagamento" -> criarPagamento();
            }
        } else {
            switch (tipo) {
                case "Filial" -> carregarFilial();
                case "Empregado" -> carregarEmpregado();
                case "Cliente" -> carregarCliente();
                case "Fornecedor" -> carregarFornecedor();
                case "Estoque" -> carregarEstoque();
                case "Pagamento" -> carregarPagamento();
            }
        }
    }

    private void salvarObj() {
        switch (tipo) {
            case "Filial" -> salvarFilial();
            case "Empregado" -> salvarEmpregado();
            case "Cliente" -> salvarCliente();
            case "Fornecedor" -> salvarFornecedor();
            case "Estoque" -> salvarEstoque();
            case "Pagamento" -> salvarPagamento();
        }
    }

    // criar Obj
    private void criarFilial() {
        Label cnpjLabel = new Label("CNPJ: ");
        Label telefoneLabel = new Label("Telafone: ");
        Label quantEmpregadosLabel = new Label("Quantidade de Empregados: ");

        Label fullAdressLabel = new Label("Endereço: ");
        Label codCountryLabel = new Label("Código do País: ");
        Label codEstadoLabel = new Label("Código do Estado: ");
        Label codCidadeLabel = new Label("Código da Cidade: ");

        cnpjLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");
        quantEmpregadosLabel.getStyleClass().add("label-title");

        fullAdressLabel.getStyleClass().add("label-title");
        codCountryLabel.getStyleClass().add("label-title");
        codEstadoLabel.getStyleClass().add("label-title");
        codCidadeLabel.getStyleClass().add("label-title");

        TextField cnpjField = new TextField();
        TextField quantEmpregadosField = new TextField();
        TextField telefoneField = new TextField();

        TextField fullAdressField = new TextField();
        TextField codCountryField = new TextField();
        TextField codEstadoField = new TextField();
        TextField codCidadeField = new TextField();

        campos.put("cnpjField", cnpjField);
        campos.put("quantEmpregadosField", quantEmpregadosField);
        campos.put("telefoneField", telefoneField);

        campos.put("fullAdressField", fullAdressField);
        campos.put("codCountryField", codCountryField);
        campos.put("codEstadoField", codEstadoField);
        campos.put("codCidadeField", codCidadeField);

        cnpjField.getStyleClass().add("input");
        quantEmpregadosField.getStyleClass().add("input");
        telefoneField.getStyleClass().add("input");

        fullAdressField.getStyleClass().add("input");
        codCountryField.getStyleClass().add("input");
        codEstadoField.getStyleClass().add("input");
        codCidadeField.getStyleClass().add("input");

        VBox boxCnpj = new VBox(cnpjLabel, cnpjField);
        VBox boxQuantEmpregados = new VBox(quantEmpregadosLabel, quantEmpregadosField);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);

        VBox boxFullAdress = new VBox(fullAdressLabel, fullAdressField);
        VBox boxCodCountry = new VBox(codCountryLabel, codCountryField);
        VBox boxCodEstado = new VBox(codEstadoLabel, codEstadoField);
        VBox boxCodCidade = new VBox(codCidadeLabel, codCidadeField);

        boxImutavel.getChildren().addAll(boxCnpj, boxQuantEmpregados, boxTelefone);
        boxMutavel.getChildren().addAll(boxFullAdress, boxCodCountry, boxCodEstado, boxCodCidade);
    }

    private void criarEmpregado() {
        Label nomeLabel = new Label("Nome: ");
        Label cpfLabel = new Label("CPF: ");
        Label emailLabel = new Label("Email: ");
        Label senhaLabel = new Label("Senha: ");
        Label telefoneLabel = new Label("Telefone: ");

        Label salarioLabel = new Label("Salário: ");
        Label cargoLabel = new Label("Cargo: ");
        Label filialIdLabel = new Label("Filial ID: ");
        Label aniversarioLabel = new Label("Aniversário: ");

        nomeLabel.getStyleClass().add("label-title");
        cpfLabel.getStyleClass().add("label-title");
        emailLabel.getStyleClass().add("label-title");
        senhaLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");

        salarioLabel.getStyleClass().add("label-title");
        cargoLabel.getStyleClass().add("label-title");
        filialIdLabel.getStyleClass().add("label-title");
        aniversarioLabel.getStyleClass().add("label-title");

        TextField nomeField = new TextField();
        TextField cpfField = new TextField();
        TextField emailField = new TextField();
        PasswordField senhaField = new PasswordField();
        TextField telefoneField = new TextField();

        TextField salarioField = new TextField();
        ComboBox<String> cargoComboBox = new ComboBox<>();
        TextField filialIdField = new TextField();
        DatePicker aniversarioPicker = new DatePicker();

        cargoComboBox.getItems().addAll("DONO", "GERENTE", "EMPREGADO");

        campos.put("nomeField", nomeField);
        campos.put("cpfField", cpfField);
        campos.put("emailField", emailField);
        campos.put("senhaField", senhaField);
        campos.put("telefoneField", telefoneField);

        campos.put("salarioField", salarioField);
        campos.put("cargoComboBox", cargoComboBox);
        campos.put("filialIdField", filialIdField);
        campos.put("aniversarioPicker", aniversarioPicker);

        nomeField.getStyleClass().add("input");
        cpfField.getStyleClass().add("input");
        emailField.getStyleClass().add("input");
        senhaField.getStyleClass().add("input");
        telefoneField.getStyleClass().add("input");

        salarioField.getStyleClass().add("input");
        cargoComboBox.getStyleClass().add("input");
        filialIdField.getStyleClass().add("input");
        aniversarioPicker.getStyleClass().add("input");


        VBox boxNome = new VBox(nomeLabel, nomeField);
        VBox boxCpf = new VBox(cpfLabel, cpfField);
        VBox boxEmail = new VBox(emailLabel, emailField);
        VBox boxSenha = new VBox(senhaLabel, senhaField);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);
        VBox boxSalario = new VBox(salarioLabel, salarioField);
        VBox boxCargo = new VBox(cargoLabel, cargoComboBox);
        VBox boxFilialId = new VBox(filialIdLabel, filialIdField);
        VBox boxAniversario = new VBox(aniversarioLabel, aniversarioPicker);

        boxImutavel.getChildren().addAll(boxNome, boxCpf, boxEmail, boxSenha, boxTelefone);
        boxMutavel.getChildren().addAll(boxSalario, boxCargo, boxFilialId, boxAniversario);
    }

    private void criarClienteFornecedor() {
        Label nomeLabel = new Label("Nome: ");
        Label cpfOrCnpjLabel = new Label("CPF/CNPJ: ");
        Label emailLabel = new Label("Email: ");
        Label telefoneLabel = new Label("Telefone: ");

        Label fullAdressLabel = new Label("Endereço: ");
        Label codCountryLabel = new Label("Código do País: ");
        Label codEstadoLabel = new Label("Código do Estado: ");
        Label codCidadeLabel = new Label("Código da Cidade: ");

        nomeLabel.getStyleClass().add("label-title");
        cpfOrCnpjLabel.getStyleClass().add("label-title");
        emailLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");

        fullAdressLabel.getStyleClass().add("label-title");
        codCountryLabel.getStyleClass().add("label-title");
        codEstadoLabel.getStyleClass().add("label-title");
        codCidadeLabel.getStyleClass().add("label-title");

        TextField nomeField = new TextField();
        TextField cpfOrCnpjField = new TextField();
        TextField emailField = new TextField();
        TextField telefoneField = new TextField();

        TextField fullAdressField = new TextField();
        TextField codCountryField = new TextField();
        TextField codEstadoField = new TextField();
        TextField codCidadeField = new TextField();

        campos.put("nomeField", nomeField);
        campos.put("cpfOrCnpjField", cpfOrCnpjField);
        campos.put("emailField", emailField);
        campos.put("telefoneField", telefoneField);

        campos.put("fullAdressField", fullAdressField);
        campos.put("codCountryField", codCountryField);
        campos.put("codEstadoField", codEstadoField);
        campos.put("codCidadeField", codCidadeField);

        nomeField.getStyleClass().add("input");
        cpfOrCnpjField.getStyleClass().add("input");
        emailField.getStyleClass().add("input");
        telefoneField.getStyleClass().add("input");

        fullAdressField.getStyleClass().add("input");
        codCountryField.getStyleClass().add("input");
        codEstadoField.getStyleClass().add("input");
        codCidadeField.getStyleClass().add("input");

        VBox boxNome = new VBox(nomeLabel, nomeField);
        VBox boxCpfOrCnpj = new VBox(cpfOrCnpjLabel, cpfOrCnpjField);
        VBox boxEmail = new VBox(emailLabel, emailField);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);

        VBox boxFullAdress = new VBox(fullAdressLabel, fullAdressField);
        VBox boxCodCountry = new VBox(codCountryLabel, codCountryField);
        VBox boxCodEstado = new VBox(codEstadoLabel, codEstadoField);
        VBox boxCodCidade = new VBox(codCidadeLabel, codCidadeField);

        boxImutavel.getChildren().addAll(boxNome, boxCpfOrCnpj, boxEmail, boxTelefone);
        boxMutavel.getChildren().addAll(boxFullAdress, boxCodCountry, boxCodEstado, boxCodCidade);
    }

    private void criarEstoque() {
        Label nomeLabel = new Label("Nome: ");
        Label idFilialLabel = new Label("Filial ID: ");
        Label idFornecedorLabel = new Label("Fornecedor ID: ");

        Label precoLabel = new Label("Preço: ");
        Label quantidadeLabel = new Label("Quantidade: ");
        Label descricaoLabel = new Label("Descrição: ");

        nomeLabel.getStyleClass().add("label-title");
        idFilialLabel.getStyleClass().add("label-title");
        idFornecedorLabel.getStyleClass().add("label-title");

        precoLabel.getStyleClass().add("label-title");
        quantidadeLabel.getStyleClass().add("label-title");
        descricaoLabel.getStyleClass().add("label-title");

        TextField nomeField = new TextField();
        TextField idFilialField = new TextField();
        TextField idFornecedorField = new TextField();

        TextField precoField = new TextField();
        TextField quantidadeField = new TextField();
        TextField descricaoField = new TextField();

        campos.put("nomeField", nomeField);
        campos.put("idFilialField", idFilialField);
        campos.put("idFornecedorField", idFornecedorField);

        campos.put("precoField", precoField);
        campos.put("quantidadeField", quantidadeField);
        campos.put("descricaoField", descricaoField);

        nomeField.getStyleClass().add("input");
        idFilialField.getStyleClass().add("input");
        idFornecedorField.getStyleClass().add("input");

        precoField.getStyleClass().add("input");
        quantidadeField.getStyleClass().add("input");
        descricaoField.getStyleClass().add("input");

        VBox boxNome = new VBox(nomeLabel, nomeField);
        VBox boxIdFilial = new VBox(idFilialLabel, idFilialField);
        VBox boxIdFornecedor = new VBox(idFornecedorLabel, idFornecedorField);

        VBox boxPreco = new VBox(precoLabel, precoField);
        VBox boxQuantidade = new VBox(quantidadeLabel, quantidadeField);
        VBox boxDescricao = new VBox(descricaoLabel, descricaoField);

        boxImutavel.getChildren().addAll(boxNome, boxIdFilial, boxIdFornecedor);
        boxMutavel.getChildren().addAll(boxPreco, boxQuantidade, boxDescricao);
    }

    private void criarPagamento() {
        Label idClienteLabel = new Label("Cliente ID: ");
        Label idFilialLabel = new Label("Filial ID: ");
        Label precoTotal = new Label("Preço Total: ");
        Label precoPago = new Label("Preço Pago: ");

        idClienteLabel.getStyleClass().add("label-title");
        idFilialLabel.getStyleClass().add("label-title");
        precoTotal.getStyleClass().add("label-title");
        precoPago.getStyleClass().add("label-title");

        Label precoTotalValorLabel = new Label("R$ 0.00");

        List<ClienteRecordOne> listaCliente = (List<ClienteRecordOne>) clienteRepository.getAllClientes(1);
        List<String> listaIdCliente = listaCliente.stream().map(ClienteRecordOne::id).toList();

        List<FilialRecordOne> listaFilial = (List<FilialRecordOne>) filialRepository.getAllFiliais(1);
        List<String> listaIdFilial = listaFilial.stream().map(FilialRecordOne::id).toList();

        ComboBox<String> idClienteField = new ComboBox<>();
        ComboBox<String> idFilialField = new ComboBox<>();
        TextField precoPagoField = new TextField();

        idClienteField.getItems().addAll(listaIdCliente);
        idFilialField.getItems().addAll(listaIdFilial);

        campos.put("idClienteField", idClienteField);
        campos.put("idFilialField", idFilialField);
        campos.put("precoTotalValorLabel", precoTotalValorLabel);
        campos.put("precoPagoField", precoPagoField);

        idClienteField.getStyleClass().add("input");
        idFilialField.getStyleClass().add("input");
        precoPagoField.getStyleClass().add("input");

        VBox boxIdCliente = new VBox(idClienteLabel, idClienteField);
        VBox boxIdFilial = new VBox(idFilialLabel, idFilialField);
        VBox boxPrecoTotal = new VBox(precoTotal, precoTotalValorLabel);
        VBox boxPrecoPago = new VBox(precoPago, precoPagoField);

        boxImutavel.getChildren().addAll(boxIdCliente, boxIdFilial, boxPrecoTotal, boxPrecoPago);

        // Lado do Objeto de ItemPagamento
        Label idItemLabel = new Label("Item ID: ");
        Label nomeLabel = new Label("Nome do Item: ");
        Label quantidadeLabel = new Label("Quantidade: ");
        Label precoUnitLabel = new Label("Preço Unitário: ");

        idItemLabel.getStyleClass().add("label-title");
        nomeLabel.getStyleClass().add("label-title");
        quantidadeLabel.getStyleClass().add("label-title");
        precoUnitLabel.getStyleClass().add("label-title");

        List<EstoqueRecordOne> estoque = (List<EstoqueRecordOne>) estoqueRepository.getAllEstoqueByIdFilial(Integer.parseInt(usuarioLogado.filialId()), 1);
        List<String> listIdEstoque = estoque.stream().map(EstoqueRecordOne::id).toList();

        ComboBox<String> idItemField = new ComboBox<>();

        Label nomeItemValorLabel = new Label();
        Label precoUnitItemValorLabel = new Label();

        TextField quantidadeItemField = new TextField();

        idItemField.getItems().addAll(listIdEstoque);

        idItemField.setOnAction(event -> {
            String idItem = idItemField.getValue();

            EstoqueRecordOne item = estoque.stream().filter(estoqueRecordOne -> estoqueRecordOne.id().equals(idItem)).findFirst().orElse(null);
            if (item != null) {
                nomeItemValorLabel.setText(item.nome());
                precoUnitItemValorLabel.setText(String.valueOf(item.preco()));
            } else {
                nomeItemValorLabel.setText("");
                precoUnitItemValorLabel.setText("");
            }
        });

        campos.put("idItemField", idItemField);
        campos.put("nomeItemValorLabel", nomeItemValorLabel);
        campos.put("quantidadeItemField", quantidadeItemField);
        campos.put("precoUnitItemValorLabel", precoUnitItemValorLabel);

        idItemField.getStyleClass().add("input");
        quantidadeItemField.getStyleClass().add("input");

        VBox boxIdItem = new VBox(idItemLabel, idItemField);
        VBox boxNomeItem = new VBox(nomeLabel, nomeItemValorLabel);
        VBox boxQuantidadeItem = new VBox(quantidadeLabel, quantidadeItemField);
        VBox boxPrecoUnitItem = new VBox(precoUnitLabel, precoUnitItemValorLabel);

        boxMutavel.getChildren().addAll(boxIdItem, boxNomeItem, boxQuantidadeItem, boxPrecoUnitItem);

        // Adição do ícone de Adição
        Image imageAdd = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/sistemaloja/aplicativo/Images/add.png")
                ).toExternalForm()
        );
        ImageView imageViewAdd = new ImageView(imageAdd);
        imageViewAdd.setFitHeight(35);
        imageViewAdd.setFitWidth(35);

        // Adiciona o Item para Compra
        imageViewAdd.setOnMouseClicked(event -> {
            ComboBox<String> idItem = (ComboBox<String>) campos.get("idItemField");
            TextField quantidadeItem = (TextField) campos.get("quantidadeItemField");

            EstoqueRecordOne item = estoque.stream().filter(estoqueRecordOne -> estoqueRecordOne.id().equals(idItem.getValue())).findFirst().orElse(null);

            itensPagamento.add(new ItemPagamentoRecordOne(null, null, item.id(), item.nome(), quantidadeItem.getText(), item.preco()));
            carregarListaItensPagamento();
        });
        imageViewAdd.setStyle("-fx-cursor: hand;");

        mainBox.getChildren().addLast(imageViewAdd);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);

        mainBox.getChildren().addLast(separator);

        ListView<ItemPagamentoRecordOne> listView = new ListView<>();
        campos.put("listView", listView);
        mainBox.getChildren().addLast(listView);

        // Adição e Configuração do ícone de deleção
        Image imageDelete = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/sistemaloja/aplicativo/Images/delete.png")
                ).toExternalForm()
        );
        ImageView imageViewDelete = new ImageView(imageDelete);
        imageViewDelete.setFitHeight(35);
        imageViewDelete.setFitWidth(35);
        imageViewDelete.setStyle("-fx-cursor: hand;");

        VBox boxDelete = new VBox(imageViewDelete);
        boxDelete.setAlignment(Pos.CENTER);

        // Delete um item do Carrinho
        boxDelete.setOnMouseClicked(event -> {
            ItemPagamentoRecordOne item = listView.getSelectionModel().getSelectedItem();
            if (item != null) {
                itensPagamento.remove(item);
                carregarListaItensPagamento();
            }
        });

        mainBox.getChildren().addLast(boxDelete);
    }

    // Editar Obj
    private void carregarFilial() {
        FilialRecordOne filial = (FilialRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label cnpjLabel = new Label("CNPJ: ");
        Label telefoneLabel = new Label("Telafone: ");
        Label quantEmpregadosLabel = new Label("Quantidade de Empregados: ");
        Label fullAdressLabel = new Label("Endereço: ");
        Label codCountryLabel = new Label("Código do País: ");
        Label codEstadoLabel = new Label("Código do Estado: ");
        Label codCidadeLabel = new Label("Código da Cidade: ");
        Label codFilialLabel = new Label("Código da Filial: ");

        idLabel.getStyleClass().add("label-title");
        cnpjLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");
        quantEmpregadosLabel.getStyleClass().add("label-title");
        fullAdressLabel.getStyleClass().add("label-title");
        codCountryLabel.getStyleClass().add("label-title");
        codEstadoLabel.getStyleClass().add("label-title");
        codCidadeLabel.getStyleClass().add("label-title");
        codFilialLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(filial.id());
        Label cnpjValorLabel = new Label(filial.cnpj());
        Label quantEmpregadosValorLabel = new Label(filial.quantEmpregados());
        Label codFilialValorLabel = new Label(filial.codFilial());

        campos.put("idValorLabel", idValorLabel);
        campos.put("cnpjValorLabel", cnpjValorLabel);
        campos.put("quantEmpregadosValorLabel", quantEmpregadosValorLabel);
        campos.put("codFilialValorLabel", codFilialValorLabel);

        TextField telefoneField = new TextField(filial.telefone());
        TextField fullAdressField = new TextField(filial.fullAdress());
        TextField codCountryField = new TextField(filial.codCountry());
        TextField codEstadoField = new TextField(filial.codEstado());
        TextField codCidadeField = new TextField(filial.codCidade());

        campos.put("telefoneField", telefoneField);
        campos.put("fullAdressField", fullAdressField);
        campos.put("codCountryField", codCountryField);
        campos.put("codEstadoField", codEstadoField);
        campos.put("codCidadeField", codCidadeField);

        telefoneField.getStyleClass().add("input");
        fullAdressField.getStyleClass().add("input");
        codCountryField.getStyleClass().add("input");
        codEstadoField.getStyleClass().add("input");
        codCidadeField.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxCnpj = new VBox(cnpjLabel, cnpjValorLabel);
        VBox boxQuantEmpregados = new VBox(quantEmpregadosLabel, quantEmpregadosValorLabel);
        VBox boxCodFilial = new VBox(codFilialLabel, codFilialValorLabel);

        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);
        VBox boxFullAdress = new VBox(fullAdressLabel, fullAdressField);
        VBox boxCodCountry = new VBox(codCountryLabel, codCountryField);
        VBox boxCodEstado = new VBox(codEstadoLabel, codEstadoField);
        VBox boxCodCidade = new VBox(codCidadeLabel, codCidadeField);

        boxImutavel.getChildren().addAll(boxId, boxCnpj, boxQuantEmpregados, boxCodFilial);
        boxMutavel.getChildren().addAll(boxTelefone, boxFullAdress, boxCodCountry, boxCodEstado, boxCodCidade);
    }

    private void carregarEmpregado() {
        EmpregadoRecordOne empregado = (EmpregadoRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label nomeLabel = new Label("Nome: ");
        Label cpfLabel = new Label("CPF: ");
        Label emailLabel = new Label("Email: ");
        Label telefoneLabel = new Label("Telefone: ");
        Label salarioLabel = new Label("Salário: ");
        Label cargoLabel = new Label("Cargo: ");
        Label filialIdLabel = new Label("Filial ID: ");
        Label aniversarioLabel = new Label("Aniversário: ");
        Label dataAdimissaoLabel = new Label("Data de Admissão: ");
        Label codEmpregadoLabel = new Label("Código do Empregado: ");

        idLabel.getStyleClass().add("label-title");
        nomeLabel.getStyleClass().add("label-title");
        cpfLabel.getStyleClass().add("label-title");
        emailLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");
        salarioLabel.getStyleClass().add("label-title");
        cargoLabel.getStyleClass().add("label-title");
        filialIdLabel.getStyleClass().add("label-title");
        aniversarioLabel.getStyleClass().add("label-title");
        dataAdimissaoLabel.getStyleClass().add("label-title");
        codEmpregadoLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(empregado.id());
        Label nomeValorLabel = new Label(empregado.nome());
        Label cpfValorLabel = new Label(empregado.cpf());
        Label emailValorLabel = new Label(empregado.email());
        Label dataAdimissaoValorLabel = new Label(empregado.dataAdimissao());
        Label codEmpregadoValorLabel = new Label(empregado.codEmpregado());

        campos.put("idValorLabel", idValorLabel);
        campos.put("nomeValorLabel", nomeValorLabel);
        campos.put("cpfValorLabel", cpfValorLabel);
        campos.put("emailValorLabel", emailValorLabel);
        campos.put("dataAdimissaoValorLabel", dataAdimissaoValorLabel);
        campos.put("codEmpregadoValorLabel", codEmpregadoValorLabel);

        TextField telefoneField = new TextField(empregado.telefone());
        TextField salarioField = new TextField(empregado.salario());
        ComboBox<String> cargoComboBox = new ComboBox<>();
        TextField filialIdField = new TextField(empregado.filialId());
        DatePicker aniversarioPicker = new DatePicker();

        cargoComboBox.getItems().addAll("DONO", "GERENTE", "EMPREGADO");
        cargoComboBox.setValue(empregado.cargo());

        if (usuarioLogado.aniversario() != null) {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
            LocalDate aniversario = LocalDate.parse(empregado.aniversario(), formatador);
            aniversarioPicker.setValue(aniversario);
        }

        campos.put("telefoneField", telefoneField);
        campos.put("salarioField", salarioField);
        campos.put("cargoComboBox", cargoComboBox);
        campos.put("filialIdField", filialIdField);
        campos.put("aniversarioPicker", aniversarioPicker);

        telefoneField.getStyleClass().add("input");
        salarioField.getStyleClass().add("input");
        cargoComboBox.getStyleClass().add("input");
        filialIdField.getStyleClass().add("input");
        aniversarioPicker.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxNome = new VBox(nomeLabel, nomeValorLabel);
        VBox boxCpf = new VBox(cpfLabel, cpfValorLabel);
        VBox boxEmail = new VBox(emailLabel, emailValorLabel);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);
        VBox boxSalario = new VBox(salarioLabel, salarioField);
        VBox boxCargo = new VBox(cargoLabel, cargoComboBox);
        VBox boxFilialId = new VBox(filialIdLabel, filialIdField);
        VBox boxAniversario = new VBox(aniversarioLabel, aniversarioPicker);
        VBox boxCodEmpregado = new VBox(codEmpregadoLabel, codEmpregadoValorLabel);


        boxImutavel.getChildren().addAll(boxId, boxNome, boxCpf, boxEmail, boxCodEmpregado);
        boxMutavel.getChildren().addAll(boxTelefone, boxSalario, boxCargo, boxFilialId, boxAniversario);
    }

    private void carregarCliente() {
        ClienteRecordOne cliente = (ClienteRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label nomeLabel = new Label("Nome: ");
        Label cpfOrCnpjLabel = new Label("CPF/CNPJ: ");
        Label emailLabel = new Label("Email: ");
        Label telefoneLabel = new Label("Telefone: ");
        Label fullAdressLabel = new Label("Endereço: ");
        Label codCountryLabel = new Label("Código do País: ");
        Label codEstadoLabel = new Label("Código do Estado: ");
        Label codCidadeLabel = new Label("Código da Cidade: ");
        Label codClienteLabel = new Label("Código do Cliente: ");

        idLabel.getStyleClass().add("label-title");
        nomeLabel.getStyleClass().add("label-title");
        cpfOrCnpjLabel.getStyleClass().add("label-title");
        emailLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");
        fullAdressLabel.getStyleClass().add("label-title");
        codCountryLabel.getStyleClass().add("label-title");
        codEstadoLabel.getStyleClass().add("label-title");
        codCidadeLabel.getStyleClass().add("label-title");
        codClienteLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(cliente.id());
        Label nomeValorLabel = new Label(cliente.nome());
        Label cpfOrCnpjValorLabel = new Label(cliente.cpfOrCnpj());
        Label emailValorLabel = new Label(cliente.email());
        Label codClienteValorLabel = new Label(cliente.codCliente());

        campos.put("idValorLabel", idValorLabel);
        campos.put("nomeValorLabel", nomeValorLabel);
        campos.put("cpfOrCnpjValorLabel", cpfOrCnpjValorLabel);
        campos.put("emailValorLabel", emailValorLabel);
        campos.put("codClienteValorLabel", codClienteValorLabel);

        TextField telefoneField = new TextField(cliente.telefone());
        TextField fullAdressField = new TextField(cliente.fullAdress());
        TextField codCountryField = new TextField(cliente.codCountry());
        TextField codEstadoField = new TextField(cliente.codEstado());
        TextField codCidadeField = new TextField(cliente.codCidade());

        campos.put("telefoneField", telefoneField);
        campos.put("fullAdressField", fullAdressField);
        campos.put("codCountryField", codCountryField);
        campos.put("codEstadoField", codEstadoField);
        campos.put("codCidadeField", codCidadeField);

        telefoneField.getStyleClass().add("input");
        fullAdressField.getStyleClass().add("input");
        codCountryField.getStyleClass().add("input");
        codEstadoField.getStyleClass().add("input");
        codCidadeField.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxNome = new VBox(nomeLabel, nomeValorLabel);
        VBox boxCpfOrCnpj = new VBox(cpfOrCnpjLabel, cpfOrCnpjValorLabel);
        VBox boxEmail = new VBox(emailLabel, emailValorLabel);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);
        VBox boxFullAdress = new VBox(fullAdressLabel, fullAdressField);
        VBox boxCodCountry = new VBox(codCountryLabel, codCountryField);
        VBox boxCodEstado = new VBox(codEstadoLabel, codEstadoField);
        VBox boxCodCidade = new VBox(codCidadeLabel, codCidadeField);
        VBox boxCodCliente = new VBox(codClienteLabel, codClienteValorLabel);

        boxImutavel.getChildren().addAll(boxId, boxNome, boxCpfOrCnpj, boxEmail, boxCodCliente);
        boxMutavel.getChildren().addAll(boxTelefone, boxFullAdress, boxCodCountry, boxCodEstado, boxCodCidade);
    }

    private void carregarFornecedor() {
        FornecedorRecordOne fornecedor = (FornecedorRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label nomeLabel = new Label("Nome: ");
        Label cpfOrCnpjLabel = new Label("CPF/CNPJ: ");
        Label emailLabel = new Label("Email: ");
        Label telefoneLabel = new Label("Telefone: ");
        Label fullAdressLabel = new Label("Endereço: ");
        Label codCountryLabel = new Label("Código do País: ");
        Label codEstadoLabel = new Label("Código do Estado: ");
        Label codCidadeLabel = new Label("Código da Cidade: ");
        Label codFornecedorLabel = new Label("Código do Fornecedor: ");

        idLabel.getStyleClass().add("label-title");
        nomeLabel.getStyleClass().add("label-title");
        cpfOrCnpjLabel.getStyleClass().add("label-title");
        emailLabel.getStyleClass().add("label-title");
        telefoneLabel.getStyleClass().add("label-title");
        fullAdressLabel.getStyleClass().add("label-title");
        codCountryLabel.getStyleClass().add("label-title");
        codEstadoLabel.getStyleClass().add("label-title");
        codCidadeLabel.getStyleClass().add("label-title");
        codFornecedorLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(fornecedor.id());
        Label nomeValorLabel = new Label(fornecedor.nome());
        Label cpfOrCnpjValorLabel = new Label(fornecedor.cpfOrCnpj());
        Label emailValorLabel = new Label(fornecedor.email());
        Label codFornecedorValorLabel = new Label(fornecedor.codFornecedor());

        campos.put("idValorLabel", idValorLabel);
        campos.put("nomeValorLabel", nomeValorLabel);
        campos.put("cpfOrCnpjValorLabel", cpfOrCnpjValorLabel);
        campos.put("emailValorLabel", emailValorLabel);
        campos.put("codFornecedorValorLabel", codFornecedorValorLabel);

        TextField telefoneField = new TextField(fornecedor.telefone());
        TextField fullAdressField = new TextField(fornecedor.fullAdress());
        TextField codCountryField = new TextField(fornecedor.codCountry());
        TextField codEstadoField = new TextField(fornecedor.codEstado());
        TextField codCidadeField = new TextField(fornecedor.codCidade());

        campos.put("telefoneField", telefoneField);
        campos.put("fullAdressField", fullAdressField);
        campos.put("codCountryField", codCountryField);
        campos.put("codEstadoField", codEstadoField);
        campos.put("codCidadeField", codCidadeField);

        telefoneField.getStyleClass().add("input");
        fullAdressField.getStyleClass().add("input");
        codCountryField.getStyleClass().add("input");
        codEstadoField.getStyleClass().add("input");
        codCidadeField.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxNome = new VBox(nomeLabel, nomeValorLabel);
        VBox boxCpfOrCnpj = new VBox(cpfOrCnpjLabel, cpfOrCnpjValorLabel);
        VBox boxEmail = new VBox(emailLabel, emailValorLabel);
        VBox boxTelefone = new VBox(telefoneLabel, telefoneField);
        VBox boxFullAdress = new VBox(fullAdressLabel, fullAdressField);
        VBox boxCodCountry = new VBox(codCountryLabel, codCountryField);
        VBox boxCodEstado = new VBox(codEstadoLabel, codEstadoField);
        VBox boxCodCidade = new VBox(codCidadeLabel, codCidadeField);
        VBox boxCodFornecedor = new VBox(codFornecedorLabel, codFornecedorValorLabel);

        boxImutavel.getChildren().addAll(boxId, boxNome, boxCpfOrCnpj, boxEmail, boxCodFornecedor);
        boxMutavel.getChildren().addAll(boxTelefone, boxFullAdress, boxCodCountry, boxCodEstado, boxCodCidade);
    }

    private void carregarEstoque() {
        EstoqueRecordOne estoque = (EstoqueRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label nomeLabel = new Label("Nome: ");
        Label idFilialLabel = new Label("Filial ID: ");
        Label idFornecedorLabel = new Label("Fornecedor ID: ");
        Label precoLabel = new Label("Preço: ");
        Label quantidadeLabel = new Label("Quantidade: ");
        Label descricaoLabel = new Label("Descrição: ");
        Label codItemLabel = new Label("Código do Item: ");

        idLabel.getStyleClass().add("label-title");
        nomeLabel.getStyleClass().add("label-title");
        idFilialLabel.getStyleClass().add("label-title");
        idFornecedorLabel.getStyleClass().add("label-title");
        precoLabel.getStyleClass().add("label-title");
        quantidadeLabel.getStyleClass().add("label-title");
        descricaoLabel.getStyleClass().add("label-title");
        codItemLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(estoque.id());
        Label nomeValorLabel = new Label(estoque.nome());
        Label idFilialValorLabel = new Label(estoque.idFilial());
        Label idFornecedorValorLabel = new Label(estoque.idFornecedor());
        Label codItemValorLabel = new Label(estoque.codItem());

        campos.put("idValorLabel", idValorLabel);
        campos.put("nomeValorLabel", nomeValorLabel);
        campos.put("idFilialValorLabel", idFilialValorLabel);
        campos.put("idFornecedorValorLabel", idFornecedorValorLabel);
        campos.put("codItemValorLabel", codItemValorLabel);

        TextField precoField = new TextField(estoque.preco());
        TextField quantidadeField = new TextField(estoque.quantidade());
        TextField descricaoField = new TextField(estoque.descricao());

        campos.put("precoField", precoField);
        campos.put("quantidadeField", quantidadeField);
        campos.put("descricaoField", descricaoField);

        precoField.getStyleClass().add("input");
        quantidadeField.getStyleClass().add("input");
        descricaoField.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxNome = new VBox(nomeLabel, nomeValorLabel);
        VBox boxIdFilial = new VBox(idFilialLabel, idFilialValorLabel);
        VBox boxIdFornecedor = new VBox(idFornecedorLabel, idFornecedorValorLabel);
        VBox boxCodItem = new VBox(codItemLabel, codItemValorLabel);
        VBox boxPreco = new VBox(precoLabel, precoField);
        VBox boxQuantidade = new VBox(quantidadeLabel, quantidadeField);
        VBox boxDescricao = new VBox(descricaoLabel, descricaoField);

        boxImutavel.getChildren().addAll(boxId, boxNome, boxIdFilial, boxIdFornecedor, boxCodItem);
        boxMutavel.getChildren().addAll(boxPreco, boxQuantidade, boxDescricao);
    }

    private void carregarPagamento() {
        PagamentoRecordOne pagamento = (PagamentoRecordOne) object;

        Label idLabel = new Label("ID: ");
        Label idClienteLabel = new Label("Cliente ID: ");
        Label idFilialLabel = new Label("Filial ID: ");
        Label precoTotalLabel = new Label("Preço Total: ");
        Label precoPagoLabel = new Label("Preço Pago: ");
        Label dataCompraLabel = new Label("Data da Compra: ");
        Label codPagamentoLabel = new Label("Código do Pagamento: ");

        idLabel.getStyleClass().add("label-title");
        idClienteLabel.getStyleClass().add("label-title");
        idFilialLabel.getStyleClass().add("label-title");
        precoTotalLabel.getStyleClass().add("label-title");
        precoPagoLabel.getStyleClass().add("label-title");
        dataCompraLabel.getStyleClass().add("label-title");
        codPagamentoLabel.getStyleClass().add("label-title");

        Label idValorLabel = new Label(pagamento.id());
        Label idClienteValorLabel = new Label(pagamento.idCliente());
        Label idFilialValorLabel = new Label(pagamento.idFilial());
        Label precoTotalValorLabel = new Label(pagamento.precoTotal());
        Label dataCompraValorLabel = new Label(pagamento.dataCompra());
        Label codPagamentoValorLabel = new Label(pagamento.codPagamento());

        TextField precoPagoField = new TextField(pagamento.precoPago());

        campos.put("idValorLabel", idValorLabel);
        campos.put("idClienteValorLabel", idClienteValorLabel);
        campos.put("idFilialValorLabel", idFilialValorLabel);
        campos.put("precoTotalValorLabel", precoTotalValorLabel);
        campos.put("dataCompraValorLabel", dataCompraValorLabel);
        campos.put("codPagamentoValorLabel", codPagamentoValorLabel);

        campos.put("precoPagoField", precoPagoField);

        precoPagoField.getStyleClass().add("input");

        VBox boxId = new VBox(idLabel, idValorLabel);
        VBox boxIdCliente = new VBox(idClienteLabel, idClienteValorLabel);
        VBox boxIdFilial = new VBox(idFilialLabel, idFilialValorLabel);
        VBox boxPrecoTotal = new VBox(precoTotalLabel, precoTotalValorLabel);
        VBox boxDataCompra = new VBox(dataCompraLabel, dataCompraValorLabel);
        VBox boxCodPagamento = new VBox(codPagamentoLabel, codPagamentoValorLabel);

        VBox boxPrecoPago = new VBox(precoPagoLabel, precoPagoField);

        boxImutavel.getChildren().addAll(boxId, boxIdCliente, boxIdFilial, boxPrecoTotal, boxDataCompra, boxCodPagamento);
        boxMutavel.getChildren().addAll(boxPrecoPago);
    }

    // Salvar Obj
    private void salvarFilial() {
        TextField telefoneField = (TextField) campos.get("telefoneField");
        TextField fullAdressField = (TextField) campos.get("fullAdressField");
        TextField codCountryField = (TextField) campos.get("codCountryField");
        TextField codEstadoField = (TextField) campos.get("codEstadoField");
        TextField codCidadeField = (TextField) campos.get("codCidadeField");

        if (criar) {
            TextField cnpjField = (TextField) campos.get("cnpjField");
            TextField quantEmpregadosField = (TextField) campos.get("quantEmpregadosField");

            FilialRecordOne filial = new FilialRecordOne(null, cnpjField.getText(), telefoneField.getText(), quantEmpregadosField.getText(),
                    fullAdressField.getText(),codCountryField.getText(), codEstadoField.getText(), codCidadeField.getText(), null);

            filialRepository.postFilial(filial, Integer.parseInt(usuarioLogado.id()), 1);
        } else {
            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label cnpjValorLabel = (Label) campos.get("cnpjValorLabel");
            Label quantEmpregadosValorLabel = (Label) campos.get("quantEmpregadosValorLabel");
            Label codFilialValorLabel = (Label) campos.get("codFilialValorLabel");

            FilialRecordOne filial = new FilialRecordOne(idValorLabel.getText(), cnpjValorLabel.getText(),
                    telefoneField.getText(), quantEmpregadosValorLabel.getText(), fullAdressField.getText(),
                    codCountryField.getText(), codEstadoField.getText(), codCidadeField.getText(), codFilialValorLabel.getText());

            filialRepository.putFilial(filial, Integer.parseInt(usuarioLogado.id()), 1);
        }
    }

    private void salvarEmpregado() {
        TextField salarioField = (TextField) campos.get("salarioField");
        ComboBox<String> cargoComboBox = (ComboBox<String>) campos.get("cargoComboBox");
        TextField filialIdField = (TextField) campos.get("filialIdField");
        DatePicker aniversarioPicker = (DatePicker) campos.get("aniversarioPicker");

        String aniversario = (aniversarioPicker.getValue() == null) ? null : aniversarioPicker.getValue().toString();

        if (criar) {
            TextField nomeField = (TextField) campos.get("nomeField");
            TextField cpfField = (TextField) campos.get("cpfField");
            TextField emailField = (TextField) campos.get("emailField");
            TextField senhaField = (TextField) campos.get("senhaField");
            TextField telefoneField = (TextField) campos.get("telefoneField");

            EmpregadoRecordOne empregado = new EmpregadoRecordOne(null, nomeField.getText(), cpfField.getText(), senhaField.getText(),
                    emailField.getText(), telefoneField.getText(), salarioField.getText(), cargoComboBox.getValue(),filialIdField.getText(),
                    aniversario, null, null);

            empregadoRepository.postNovoEmpregado(empregado, Integer.parseInt(usuarioLogado.id()), 1);
        } else {
            EmpregadoRecordOne oldEmpregado = (EmpregadoRecordOne) object;

            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label nomeValorLabel = (Label) campos.get("nomeValorLabel");
            Label cpfValorLabel = (Label) campos.get("cpfValorLabel");
            Label emailValorLabel = (Label) campos.get("emailValorLabel");
            Label dataAdimissaoValorLabel = (Label) campos.get("dataAdimissaoValorLabel");
            Label codEmpregadoValorLabel = (Label) campos.get("codEmpregadoValorLabel");
            TextField telefoneField = (TextField) campos.get("telefoneField");

            EmpregadoRecordOne empregado = new EmpregadoRecordOne(idValorLabel.getText(), nomeValorLabel.getText(),
                    cpfValorLabel.getText(), oldEmpregado.senha(), emailValorLabel.getText(), telefoneField.getText(),
                    salarioField.getText(), cargoComboBox.getValue(), filialIdField.getText(), aniversarioPicker.getValue().toString(),
                    dataAdimissaoValorLabel.getText(), codEmpregadoValorLabel.getText());

            empregadoRepository.putAlterarEmpregado(empregado, Integer.parseInt(usuarioLogado.id()), 1);
        }
    }

    private void salvarCliente() {
        TextField telefoneField = (TextField) campos.get("telefoneField");
        TextField fullAdressField = (TextField) campos.get("fullAdressField");
        TextField codCountryField = (TextField) campos.get("codCountryField");
        TextField codEstadoField = (TextField) campos.get("codEstadoField");
        TextField codCidadeField = (TextField) campos.get("codCidadeField");

        if (criar) {
            TextField nomeField = (TextField) campos.get("nomeField");
            TextField cpfOrCnpjField = (TextField) campos.get("cpfOrCnpjField");
            TextField emailField = (TextField) campos.get("emailField");

            ClienteRecordOne cliente = new ClienteRecordOne(null, nomeField.getText(), cpfOrCnpjField.getText(), emailField.getText(),
                    telefoneField.getText(), fullAdressField.getText(), codCountryField.getText(), codEstadoField.getText(),
                    codCidadeField.getText(), null);

            clienteRepository.postCliente(cliente, Integer.parseInt(usuarioLogado.id()), 1);
        } else {
            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label nomeValorLabel = (Label) campos.get("nomeValorLabel");
            Label cpfOrCnpjValorLabel = (Label) campos.get("cpfOrCnpjValorLabel");
            Label emailValorLabel = (Label) campos.get("emailValorLabel");
            Label codClienteValorLabel = (Label) campos.get("codClienteValorLabel");

            ClienteRecordOne cliente = new ClienteRecordOne(idValorLabel.getText(), nomeValorLabel.getText(),
                    cpfOrCnpjValorLabel.getText(), emailValorLabel.getText(), telefoneField.getText(),
                    fullAdressField.getText(), codCountryField.getText(), codEstadoField.getText(),
                    codCidadeField.getText(), codClienteValorLabel.getText());

            clienteRepository.putAlterarCliente(cliente, Integer.parseInt(usuarioLogado.id()), 1);
        }
    }

    private void salvarFornecedor() {
        TextField telefoneField = (TextField) campos.get("telefoneField");
        TextField fullAdressField = (TextField) campos.get("fullAdressField");
        TextField codCountryField = (TextField) campos.get("codCountryField");
        TextField codEstadoField = (TextField) campos.get("codEstadoField");
        TextField codCidadeField = (TextField) campos.get("codCidadeField");

        if (criar) {
            TextField nomeField = (TextField) campos.get("nomeField");
            TextField cpfOrCnpjField = (TextField) campos.get("cpfOrCnpjField");
            TextField emailField = (TextField) campos.get("emailField");

            FornecedorRecordOne fornecedor = new FornecedorRecordOne(null, nomeField.getText(), cpfOrCnpjField.getText(), emailField.getText(),
                    telefoneField.getText(), fullAdressField.getText(), codCountryField.getText(), codEstadoField.getText(),
                    codCidadeField.getText(), null);

            fornecedorRepository.postFornecedor(fornecedor, Integer.parseInt(usuarioLogado.id()), 1);
        } else {
            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label nomeValorLabel = (Label) campos.get("nomeValorLabel");
            Label cpfOrCnpjValorLabel = (Label) campos.get("cpfOrCnpjValorLabel");
            Label emailValorLabel = (Label) campos.get("emailValorLabel");
            Label codFornecedorValorLabel = (Label) campos.get("codFornecedorValorLabel");

            FornecedorRecordOne fornecedor = new FornecedorRecordOne(idValorLabel.getText(), nomeValorLabel.getText(),
                    cpfOrCnpjValorLabel.getText(), emailValorLabel.getText(), telefoneField.getText(),
                    fullAdressField.getText(), codCountryField.getText(), codEstadoField.getText(),
                    codCidadeField.getText(), codFornecedorValorLabel.getText());

            fornecedorRepository.putAlterarFornecedor(fornecedor, Integer.parseInt(usuarioLogado.id()), 1);
        }
    }

    private void salvarEstoque() {
        TextField precoField = (TextField) campos.get("precoField");
        TextField quantidadeField = (TextField) campos.get("quantidadeField");
        TextField descricaoField = (TextField) campos.get("descricaoField");

        if (criar) {
            TextField nomeField = (TextField) campos.get("nomeField");
            TextField idFilialField = (TextField) campos.get("idFilialField");
            TextField idFornecedorField = (TextField) campos.get("idFornecedorField");

            EstoqueRecordOne estoque = new EstoqueRecordOne(null, nomeField.getText(), idFilialField.getText(), idFornecedorField.getText(),
                    precoField.getText(), quantidadeField.getText(), descricaoField.getText(), null);

            estoqueRepository.postNovoEstoque(estoque, 1);
        } else {
            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label nomeValorLabel = (Label) campos.get("nomeValorLabel");
            Label idFilialValorLabel = (Label) campos.get("idFilialValorLabel");
            Label idFornecedorValorLabel = (Label) campos.get("idFornecedorValorLabel");
            Label codItemValorLabel = (Label) campos.get("codItemValorLabel");

            EstoqueRecordOne estoque = new EstoqueRecordOne(idValorLabel.getText(), nomeValorLabel.getText(),
                    idFilialValorLabel.getText(), idFornecedorValorLabel.getText(), precoField.getText(),
                    quantidadeField.getText(), descricaoField.getText(), codItemValorLabel.getText());

            estoqueRepository.putAlterarEstoque(estoque, 1);
        }
    }

    private void salvarPagamento() {
        TextField precoPagoField = (TextField) campos.get("precoPagoField");

        if (criar) {
            ComboBox<String> idClienteField = (ComboBox<String>) campos.get("idClienteField");
            ComboBox<String> idFilialField = (ComboBox<String>) campos.get("idFilialField");
            Label precoTotalValorLabel = (Label) campos.get("precoTotalValorLabel");

            String precoTotal = precoTotalValorLabel.getText().replace("R$ ", "").replace(",", ".");
            double precoPago = Double.parseDouble(precoPagoField.getText().replace("R$ ", ""));
            BigDecimal precoPagoBigDecimal = new BigDecimal(precoPago);

            PagamentoRecordOne pagamento = new PagamentoRecordOne(null, idClienteField.getValue(), idFilialField.getValue(),
            precoTotal, precoPagoBigDecimal.toString(), null, null);

            PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> payload = new PagamentoPayloadRecord<>(pagamento, itensPagamento);

            pagamentoRepository.postPagamento(payload, 1);
        } else {
            Label idValorLabel = (Label) campos.get("idValorLabel");
            Label idClienteValorLabel = (Label) campos.get("idClienteValorLabel");
            Label idFilialValorLabel = (Label) campos.get("idFilialValorLabel");
            Label precoTotalValorLabel = (Label) campos.get("precoTotalValorLabel");
            Label dataCompraValorLabel = (Label) campos.get("dataCompraValorLabel");
            Label codPagamentoValorLabel = (Label) campos.get("codPagamentoValorLabel");

            PagamentoRecordOne pagamento = new PagamentoRecordOne(idValorLabel.getText(), idClienteValorLabel.getText(),
                    idFilialValorLabel.getText(), precoTotalValorLabel.getText(), precoPagoField.getText(),
                    dataCompraValorLabel.getText(), codPagamentoValorLabel.getText());

            pagamentoRepository.putPagamento(pagamento, 1);
        }
    }

    // funções extras
    private void carregarListaItensPagamento() {
        ListView<ItemPagamentoRecordOne> listView = (ListView<ItemPagamentoRecordOne>) campos.get("listView");
        Label precoTotalValorLabel = (Label) campos.get("precoTotalValorLabel");

        ObservableList<ItemPagamentoRecordOne> obsList = FXCollections.observableArrayList();
        obsList.addAll(itensPagamento);

        listView.setItems(obsList);

        listView.setCellFactory(lv -> new ListCell<ItemPagamentoRecordOne>() {
            @Override
            protected void updateItem(ItemPagamentoRecordOne item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    TextFlow flow = new TextFlow();
                    Text textoComum = new Text(item.toString());
                    textoComum.setStyle("-fx-wrap-text: true; -fx-fill: white;");

                    flow.getChildren().add(textoComum);
                    flow.maxWidthProperty().bind(lv.widthProperty().subtract(40));

                    setGraphic(flow);
                    setText(null);
                }
            }
        });

        double precoTotal = 0;
        for (ItemPagamentoRecordOne item : itensPagamento) {
            precoTotal += Double.parseDouble(item.precoUnit()) * Double.parseDouble(item.quantidade());
        }

        precoTotalValorLabel.setText(String.format("R$ %.2f", precoTotal));
    }
}

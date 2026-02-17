package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserController {
    private EmpregadoRecordOne usuarioLogado;
    private Alert alerta;

    @FXML
    public Label nomeSobrenomeLabel;
    @FXML
    public Label cargoLateralLabel;
    @FXML
    public Label cargoCentralLabel;
    @FXML
    public Label nomeCompletoLabel;
    @FXML
    public Label nomeContatoLabel;
    @FXML
    public Label cpfContatoLabel;
    @FXML
    public Label emailContatoLabel;
    @FXML
    public Label telefoneContatoLabel;
    @FXML
    public Label aniversarioContatoLabel;
    @FXML
    public Label codEmpregadoLabel;
    @FXML
    public Label codFilialLabel;
    @FXML
    public Label salarioLabel;
    @FXML
    public Label admissaoLabel;

    @FXML
    private void sairAplicativo() {
        System.exit(0);
    }

    @FXML
    public void onHomeDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/HomePage.fxml"));
                Parent root = fxmlLoader.load();

                HomeController homeController = fxmlLoader.getController();
                homeController.setUsuarioLogado(usuarioLogado);

                Scene scene = nomeSobrenomeLabel.getScene();
                scene.setRoot(root);

                Stage stage = (Stage) scene.getWindow();
                stage.setMaximized(true);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao efetuar a troca de página!");
            alerta.show();
        }
    }

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        carregarDadosPagina();
    }

    private void carregarDadosPagina() {
        if (usuarioLogado != null) {
            String[] nomes = usuarioLogado.nome().split(" ");
            String nomeSobrenome = String.join(" ", nomes[0], nomes[nomes.length - 1]);

            String dataAniversario = "--/--/----";
            String dataAdmissao = "--/--/----";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (usuarioLogado.aniversario() != null && !usuarioLogado.aniversario().isEmpty()) {
                LocalDate date = LocalDate.parse(usuarioLogado.aniversario(), formatter);
                dataAniversario = date.format(formatter);
            }

            if (usuarioLogado.dataAdimissao() != null && !usuarioLogado.dataAdimissao().isEmpty()) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime dateTime = LocalDateTime.parse(usuarioLogado.dataAdimissao(), inputFormatter);
                dataAdmissao = dateTime.format(formatter);
            }

            nomeSobrenomeLabel.setText(nomeSobrenome);
            cargoLateralLabel.setText(usuarioLogado.cargo());
            cargoCentralLabel.setText(usuarioLogado.cargo());
            nomeCompletoLabel.setText(usuarioLogado.nome());
            nomeContatoLabel.setText(usuarioLogado.nome());
            cpfContatoLabel.setText(usuarioLogado.cpf());
            emailContatoLabel.setText(usuarioLogado.email());
            telefoneContatoLabel.setText((usuarioLogado.telefone() != null) ? usuarioLogado.telefone() : "-------------");
            aniversarioContatoLabel.setText(dataAniversario);
            codEmpregadoLabel.setText(usuarioLogado.codEmpregado());
            codFilialLabel.setText(usuarioLogado.filialId());
            salarioLabel.setText(String.format("R$ %.2f", Double.parseDouble(usuarioLogado.salario())));
            admissaoLabel.setText(dataAdmissao);
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao Obter o Usuário Logado!");
            alerta.show();
        }
    }
}

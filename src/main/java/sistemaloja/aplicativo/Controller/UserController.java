package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserController {
    private EmpregadoRecordOne usuarioLogado;
    private Alert alerta;

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
    private BarraLateralController barraLateralController;

    public void setUsuarioLogado(EmpregadoRecordOne usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        barraLateralController.setUsuarioLogado(usuarioLogado);

        carregarDadosPagina();
    }

    private void carregarDadosPagina() {
        if (usuarioLogado != null) {
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

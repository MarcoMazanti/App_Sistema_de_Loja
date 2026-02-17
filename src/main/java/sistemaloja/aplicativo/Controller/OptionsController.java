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

public class OptionsController {
    private EmpregadoRecordOne usuarioLogado;
    private String tipo;
    private Alert alerta;

    @FXML
    public Label nomeSobrenomeLabel;
    @FXML
    public Label cargoLateralLabel;

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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private void carregarDadosPagina() {
        if (usuarioLogado != null) {
            String[] nomes = usuarioLogado.nome().split(" ");
            String nomeSobrenome = String.join(" ", nomes[0], nomes[nomes.length - 1]);

            nomeSobrenomeLabel.setText(nomeSobrenome);
            cargoLateralLabel.setText(usuarioLogado.cargo());
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao Obter o Usuário Logado!");
            alerta.show();
        }
    }
}

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

public class BarraLateralController {
    private EmpregadoRecordOne usuarioLogado;
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
    public void onUsuarioDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/UserPage.fxml"));
                Parent root = fxmlLoader.load();

                UserController userController = fxmlLoader.getController();
                userController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onFilialDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Filial");
                optionsController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onEmpregadoDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Empregado");
                optionsController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onClienteDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Cliente");
                optionsController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onFornecedorDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Fornecedor");
                optionsController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onEstoqueDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Estoque");
                optionsController.setUsuarioLogado(usuarioLogado);

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

    @FXML
    private void onPagamentoDisplayClicked(MouseEvent mouseEvent) {
        try {
            if (usuarioLogado != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sistemaloja/aplicativo/Views/OptionsPane.fxml"));
                Parent root = fxmlLoader.load();

                OptionsController optionsController = fxmlLoader.getController();
                optionsController.setTipo("Pagamento");
                optionsController.setUsuarioLogado(usuarioLogado);

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

        String[] nomes = usuarioLogado.nome().split(" ");
        String nomeSobrenome = String.join(" ", nomes[0], nomes[nomes.length - 1]);


        nomeSobrenomeLabel.setText(nomeSobrenome);
        cargoLateralLabel.setText(usuarioLogado.cargo());
    }
}

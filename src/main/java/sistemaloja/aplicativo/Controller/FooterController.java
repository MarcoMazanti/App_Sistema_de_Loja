package sistemaloja.aplicativo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class FooterController implements Initializable {
    public ImageView fotoMinha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Circle clip = new Circle(50, 50, 50);
        fotoMinha.setClip(clip);
    }

    @FXML
    private void linkedIn(MouseEvent mouseEvent) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI("www.linkedin.com/in/marco-aurélio-consoni-mazanti");
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gitHub(MouseEvent mouseEvent) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI("https://github.com/MarcoMazanti");
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

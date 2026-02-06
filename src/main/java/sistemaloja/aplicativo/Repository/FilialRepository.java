package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Factory.FilialFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class FilialRepository {
    private static String URL = String.format("%s/api/filial", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    public List<?> getAllFiliais(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FilialFactory filialFactory = new FilialFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> filiais = mapper.readValue(response.body(),
                        mapper.getTypeFactory().constructCollectionType(List.class, filialFactory.retornaClasse(response.body())));

                return filialFactory.decriptFilialList(filiais);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body());
                alerta.show();
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Object getFilialById(int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FilialFactory filialFactory = new FilialFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object filial = mapper.readValue(response.body(), filialFactory.retornaClasse(response.body()));

                return filialFactory.decriptFilial(filial);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object postFilial(Object filial, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FilialFactory filialFactory = new FilialFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            String json = mapper.writeValueAsString(filialFactory.criptFilial(filial));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object filialCriptografado = mapper.readValue(response.body(), filialFactory.retornaClasse(response.body()));

                if (filialCriptografado == null) return null;
                return filialFactory.decriptFilial(filialCriptografado);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object putFilial(Object filial, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FilialFactory filialFactory = new FilialFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            String json = mapper.writeValueAsString(filialFactory.criptFilial(filial));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object filialCriptografado = mapper.readValue(response.body(), filialFactory.retornaClasse(response.body()));

                if (filialCriptografado == null) return null;
                return filialFactory.decriptFilial(filialCriptografado);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean deleteFilial(int id, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body());
                alerta.show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

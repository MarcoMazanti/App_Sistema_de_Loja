package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Factory.FornecedorFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class FornecedorRepository {
    private static String URL = String.format("%s/api/fornecedor", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    public List<?> getAllFornecedores(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FornecedorFactory fornecedorFactory = new FornecedorFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> fornecedorList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, fornecedorFactory.retornaClasse(response.body().toString())));

                if (fornecedorList == null) return null;

                return fornecedorFactory.decriptFornecedorList(fornecedorList);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body().toString());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getClienteById(int idCliente, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FornecedorFactory fornecedorFactory = new FornecedorFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + idCliente))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object fornecedor = mapper.readValue(response.body().toString(), fornecedorFactory.retornaClasse(response.body().toString()));

                if (fornecedor == null) return null;

                return fornecedorFactory.decriptFornecedor(fornecedor);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body().toString());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object postFornecedor(Object fornecedor, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FornecedorFactory fornecedorFactory = new FornecedorFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();
            String json = mapper.writeValueAsString(fornecedorFactory.criptFornecedor(fornecedor));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object fornecedorResponse = mapper.readValue(response.body().toString(), fornecedorFactory.retornaClasse(response.body().toString()));

                if (fornecedorResponse == null) return null;

                return fornecedorFactory.decriptFornecedor(fornecedorResponse);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body().toString());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object putAlterarCliente(Object fornecedor, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            FornecedorFactory fornecedorFactory = new FornecedorFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();
            String json = mapper.writeValueAsString(fornecedorFactory.criptFornecedor(fornecedor));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object fornecedorResponse = mapper.readValue(response.body().toString(), fornecedorFactory.retornaClasse(response.body().toString()));

                if (fornecedorResponse == null) return null;

                return fornecedorFactory.decriptFornecedor(fornecedorResponse);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body().toString());
                alerta.show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean deleteFornecedor(int idRequerinte, int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .DELETE()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(response.body().toString());
                alerta.show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

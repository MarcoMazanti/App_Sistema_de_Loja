package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Factory.ClienteFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class ClienteRepository {
    private static String URL = String.format("%s/api/cliente", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    public List<?> getAllClientes(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            ClienteFactory clienteFactory = new ClienteFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> clienteList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clienteFactory.retornaClasse(response.body().toString())));

                if (clienteList == null) return null;

                return clienteFactory.decriptClienteList(clienteList);
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
            ClienteFactory clienteFactory = new ClienteFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + idCliente))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object cliente = mapper.readValue(response.body().toString(), clienteFactory.retornaClasse(response.body().toString()));

                if (cliente == null) return null;

                return clienteFactory.decriptCliente(cliente);
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

    public Object postCliente(Object cliente, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            ClienteFactory clienteFactory = new ClienteFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();
            String json = mapper.writeValueAsString(clienteFactory.criptCliente(cliente));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object clienteResponse = mapper.readValue(response.body().toString(), clienteFactory.retornaClasse(response.body().toString()));

                if (clienteResponse == null) return null;

                return clienteFactory.decriptCliente(clienteResponse);
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

    public Object putAlterarCliente(Object cliente, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            ClienteFactory clienteFactory = new ClienteFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();
            String json = mapper.writeValueAsString(clienteFactory.criptCliente(cliente));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object clienteResponse = mapper.readValue(response.body().toString(), clienteFactory.retornaClasse(response.body().toString()));

                if (clienteResponse == null) return null;

                return clienteFactory.decriptCliente(clienteResponse);
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

    public Boolean deleteCliente(int idRequerinte, int id, int modelRecord) {
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

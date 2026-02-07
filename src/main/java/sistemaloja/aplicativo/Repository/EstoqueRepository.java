package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Factory.EstoqueFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class EstoqueRepository {
    private static String URL = String.format("%s/api/estoque", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    public List<?> getAllEstoques(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> estoqueList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, estoqueFactory.retornaClasse(response.body().toString())));

                if (estoqueList == null) return null;

                return estoqueFactory.decriptEstoqueList(estoqueList);
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

    public List<?> getAllEstoqueByIdFornecedor(int idFornecedor, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id_fornecedor/" + idFornecedor))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> estoqueList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, estoqueFactory.retornaClasse(response.body().toString())));

                if (estoqueList == null) return null;

                return estoqueFactory.decriptEstoqueList(estoqueList);
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

    public List<?> getAllEstoqueByIdFilial(int idFilial, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id_filial/" + idFilial))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> estoqueList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, estoqueFactory.retornaClasse(response.body().toString())));

                if (estoqueList == null) return null;

                return estoqueFactory.decriptEstoqueList(estoqueList);
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

    public Object getEstoqueById(int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object estoque = mapper.readValue(response.body().toString(), estoqueFactory.retornaClasse(response.body()));

                if (estoque == null) return null;

                return estoqueFactory.decriptEstoque(estoque);
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

    public Object postNovoEstoque(Object estoque, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            String json = mapper.writeValueAsString(estoqueFactory.criptEstoque(estoque));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object estoqueResponse = mapper.readValue(response.body().toString(), estoqueFactory.retornaClasse(response.body()));

                if (estoqueResponse == null) return null;

                return estoqueFactory.decriptEstoque(estoqueResponse);
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

    public Object putAlterarEstoque(Object estoque, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EstoqueFactory estoqueFactory = new EstoqueFactory(secretKey);

            String json = mapper.writeValueAsString(estoqueFactory.criptEstoque(estoque));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object estoqueResponse = mapper.readValue(response.body().toString(), estoqueFactory.retornaClasse(response.body()));

                if (estoqueResponse == null) return null;

                return estoqueFactory.decriptEstoque(estoqueResponse);
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

    public Boolean deleteEstoque(int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
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

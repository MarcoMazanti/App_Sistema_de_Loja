package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Entity.Empregado.TrocarSenha;
import sistemaloja.aplicativo.Factory.EmpregadoFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class EmpregadoRepository {
    private static String URL = String.format("%s/api/empregado", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    public List<?> getAllEmpregados(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> empregadoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, empregadoFactory.retornaClasse(response.body().toString())));

                if (empregadoList == null) return null;

                return empregadoFactory.decriptEmpregadoList(empregadoList);
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

    public Object getEmpregadoById(int idEmpregado, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/id/" + idEmpregado))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object empregado = mapper.readValue(response.body().toString(), empregadoFactory.retornaClasse(response.body().toString()));

                if (empregado == null) return null;

                return empregadoFactory.descriptEmpregado(empregado);
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

    public List<?> getEmpregadoByFilialId(int filialId, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/filial_id/" + filialId))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> empregadoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, empregadoFactory.retornaClasse(response.body().toString())));

                if (empregadoList == null) return null;

                return empregadoFactory.decriptEmpregadoList(empregadoList);
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

    public boolean trocarSenha(TrocarSenha trocarSenha) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            String json = mapper.writeValueAsString(empregadoFactory.criptEmpregado(trocarSenha));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/trocar_senha"))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(1))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
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

    public Object login(Login login, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            String json = mapper.writeValueAsString(empregadoFactory.criptEmpregado(login));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/login"))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object empregado = mapper.readValue(response.body().toString(), empregadoFactory.retornaClasse(response.body().toString()));

                if (empregado == null) return null;

                return empregadoFactory.descriptEmpregado(empregado);
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

    public Object postNovoEmpregado(Object empregado, int idRequerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            String json = mapper.writeValueAsString(empregadoFactory.criptEmpregado(empregado));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + idRequerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object empregadoResponse = mapper.readValue(response.body().toString(), empregadoFactory.retornaClasse(response.body().toString()));

                if (empregadoResponse == null) return null;

                return empregadoFactory.descriptEmpregado(empregado);
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

    public Object putAlterarEmpregado(Object empregado, int requerinte, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            EmpregadoFactory empregadoFactory = new EmpregadoFactory(secretKey);

            String json = mapper.writeValueAsString(empregadoFactory.criptEmpregado(empregado));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "/" + requerinte))
                    .header("Content-Type", "application/json")
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object empregadoResponse = mapper.readValue(response.body().toString(), empregadoFactory.retornaClasse(response.body().toString()));

                if (empregadoResponse == null) return null;

                return empregadoFactory.descriptEmpregado(empregado);
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

    public Boolean deleteEmpregado(int idRequerinte, int id, int modelRecord) {
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

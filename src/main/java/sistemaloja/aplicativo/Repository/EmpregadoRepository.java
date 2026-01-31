package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordThree;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordTwo;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordTwo;
import sistemaloja.aplicativo.Factory.EmpregadoFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class EmpregadoRepository {
    private static String URL = "http://localhost:8081/api/empregado";

    private final ObjectMapper mapper = new ObjectMapper();

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
                Object empregado;

                switch (modelRecord) {
                    case 1 -> empregado = mapper.readValue(response.body().toString(), EmpregadoRecordOne.class);
                    case 2 -> empregado = mapper.readValue(response.body().toString(), EmpregadoRecordTwo.class);
                    case 3 -> empregado = mapper.readValue(response.body().toString(), EmpregadoRecordThree.class);
                    default -> empregado = null;
                }

                if (empregado == null) return null;

                return empregadoFactory.descriptEmpregado(empregado);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

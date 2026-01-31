package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordTwo;
import sistemaloja.aplicativo.Factory.FilialFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class FilialRepository {
    private static String URL = "http://localhost:8081/api/filial";

    private final ObjectMapper mapper = new ObjectMapper();

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
                List<?> filiais;

                switch (modelRecord) {
                    case 1 -> {
                        filiais = mapper.readValue(response.body(),
                                mapper.getTypeFactory().constructCollectionType(List.class, FilialRecordOne.class));
                    }
                    case 2 -> {
                        filiais = mapper.readValue(response.body(),
                                mapper.getTypeFactory().constructCollectionType(List.class, FilialRecordTwo.class));
                    }
                    case 3 -> {
                        filiais = mapper.readValue(response.body(),
                                mapper.getTypeFactory().constructCollectionType(List.class, FilialRecordThree.class));
                    }
                    default -> filiais = Collections.emptyList();
                }

                return (List<?>) filialFactory.decriptFilialList(filiais);
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

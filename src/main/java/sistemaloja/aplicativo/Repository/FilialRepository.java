package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordTwo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FilialRepository {
    private static String URL = "http://localhost:8081/api/filial";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<?> getAllFiliais(int modelRecord) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return switch (modelRecord) {
                    case 1 -> mapper.readValue(response.body(), new TypeReference<List<FilialRecordOne>>() {});
                    case 2 -> mapper.readValue(response.body(), new TypeReference<List<FilialRecordTwo>>() {});
                    case 3 -> mapper.readValue(response.body(), new TypeReference<List<FilialRecordThree>>() {});
                    default -> null;
                };
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter filiais.");
            e.printStackTrace();
            return null;
        }
    }
}

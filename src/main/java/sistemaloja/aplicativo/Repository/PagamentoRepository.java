package sistemaloja.aplicativo.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import sistemaloja.aplicativo.Entity.Pagamento.*;
import sistemaloja.aplicativo.Factory.PagamentoFactory;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static sistemaloja.aplicativo.Launcher.host;
import static sistemaloja.aplicativo.Security.GerarSecretKey.criptSecretKey;
import static sistemaloja.aplicativo.Security.GerarSecretKey.gerarSecretKey;

public class PagamentoRepository {
    private static final String URL = String.format("%s/api/pagamento", host);
    private static final String URL_SPECS = String.format("%s/api/pagamento/specs", host);
    private static final String URL_GENRERIC = String.format("%s/api/pagamento/generic", host);

    private final ObjectMapper mapper = new ObjectMapper();
    private Alert alerta;

    // Requisições para Specs - ItemPagamento
    public List<?> getAllPagamentoSpecs(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_SPECS))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public List<?> getAllPagmentoSpecsByIdPagamento(int idPagamento, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_SPECS + "/id_pagamento/" + idPagamento))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public List<?> getAllPagamentoSpecsByIdItem(int idItem, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_SPECS + "/id_item/" + idItem))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public Object getPagamentoSpecsById(int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_SPECS + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object pagamento = mapper.readValue(response.body().toString(), pagamentoFactory.retornaClasse(response.body()));

                if (pagamento == null) return null;

                return pagamentoFactory.decriptPagamento(pagamento);
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

    // Requisições para Generic - Pagamento
    public List<?> getAllPagamentoGeneric(int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GENRERIC))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public List<?> getAllPagamentoGenericByIdFilial(int idFilial, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GENRERIC + "/id_filial/" + idFilial))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public List<?> getAllPagamentoGenericByIdCliente(int idCliente, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GENRERIC + "/id_clientel/" + idCliente))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<Object> pagamentoList = mapper.readValue(response.body().toString(),
                        mapper.getTypeFactory().constructCollectionType(List.class, pagamentoFactory.retornaClasse(response.body().toString())));

                if (pagamentoList == null) return null;

                return pagamentoFactory.decriptPagamentoList(pagamentoList);
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

    public Object getPagamentoGenericById(int id, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GENRERIC + "/id/" + id))
                    .header("ModelRecord", String.valueOf(modelRecord))
                    .header("secretKey", criptSecretKey(secretKey))
                    .GET()
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Object pagamento = mapper.readValue(response.body().toString(), pagamentoFactory.retornaClasse(response.body()));

                if (pagamento == null) return null;

                return pagamentoFactory.decriptPagamento(pagamento);
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
    
    public Object postPagamento(PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> pagamentoPayloadRecord, int modelRecord) {
        try {
            SecretKey secretKey = gerarSecretKey();
            PagamentoFactory pagamentoFactory = new PagamentoFactory(secretKey);

            String json = mapper.writeValueAsString(pagamentoFactory.criptPagamentoPayload(pagamentoPayloadRecord));
            
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
                PagamentoPayloadRecord<?, ?> pagamentoPayloadResponse = (PagamentoPayloadRecord<?, ?>) mapper.readValue(response.body().toString(), pagamentoFactory.retornaClasse(response.body().toString()));

                if (pagamentoPayloadResponse == null) return null;

                if (pagamentoPayloadResponse.pagamento() instanceof PagamentoRecordOne && pagamentoPayloadResponse.itemPagamentoList().get(0) instanceof ItemPagamentoRecordOne) {
                    return pagamentoFactory.decriptPagamentoPayloadRecordOne((PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne>) pagamentoPayloadResponse);
                } else if (pagamentoPayloadResponse.pagamento() instanceof PagamentoRecordTwo && pagamentoPayloadResponse.itemPagamentoList().get(0) instanceof ItemPagamentoRecordTwo) {
                    return pagamentoFactory.decriptPagamentoPayloadRecordTwo((PagamentoPayloadRecord<PagamentoRecordTwo, ItemPagamentoRecordTwo>) pagamentoPayloadResponse);
                }
                return null;
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
}

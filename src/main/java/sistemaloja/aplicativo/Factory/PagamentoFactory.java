package sistemaloja.aplicativo.Factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordThree;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordTwo;
import sistemaloja.aplicativo.Entity.Pagamento.*;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.List;

public class PagamentoFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public PagamentoFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Object criptPagamento(Object objeto) {
        if (objeto instanceof List<?> lista) {
            if (!lista.isEmpty()) {
                List<Object> pagamentoList = new java.util.ArrayList<>();

                for (Object pagamento : lista) {
                    pagamentoList.add(criptPagamento(pagamento));
                }

                return pagamentoList;
            } else {
                return null;
            }
        } else {
            return criptPagamentoRecord((PagamentoRecordOne) objeto);
        }
    }

    public <T> List<T> decriptPagamentoList(List<T> objeto) {
        List<T> pagamentoList = new java.util.ArrayList<>();

        for (T pagamento : objeto) {
            pagamentoList.add(decriptPagamento(pagamento));
        }

        return pagamentoList;
    }

    public <T> T decriptPagamento(T objeto) {
        return (T) switch (objeto) {
            case PagamentoRecordOne pagamentoRecordOne -> decriptPagamentoRecordOne(pagamentoRecordOne);
            case PagamentoRecordTwo pagamentoRecordTwo -> decriptPagamentoRecordTwo(pagamentoRecordTwo);
            case ItemPagamentoRecordOne itemPagamentoRecordOne -> decriptItemPagamentoRecordOne(itemPagamentoRecordOne);
            case ItemPagamentoRecordTwo itemPagamentoRecordTwo -> decriptItemPagamentoRecordTwo(itemPagamentoRecordTwo);
            default -> null;
        };
    }

    public Class<?> retornaClasse(Object body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;

        if (body instanceof String s) json = mapper.readTree(s);
        else json = mapper.valueToTree(body);

        if (json.isArray() && !json.isEmpty()) json = json.get(0);

        boolean temId  = !json.findPath("id").isMissingNode();
        boolean temPagamento = !json.findPath("pagamento").isMissingNode();
        boolean temItemPagamentoList = !json.findPath("itemPagamentoList").isMissingNode();
        boolean temIdFilial = !json.findPath("idFilial").isMissingNode();
        boolean temDataCompra = !json.findPath("dataCompra").isMissingNode();
        boolean temCodPagamento = !json.findPath("codPagamento").isMissingNode();
        boolean temIdPagamento = !json.findPath("idPagamento").isMissingNode();

        if (temPagamento && temItemPagamentoList) return PagamentoPayloadRecord.class;
        if (temId && temIdFilial) return PagamentoRecordOne.class;
        if (temDataCompra && temCodPagamento) return PagamentoRecordTwo.class;
        if (temIdPagamento && temId) return ItemPagamentoRecordOne.class;
        return ItemPagamentoRecordTwo.class;
    }

    // Decript → Cript (REQUEST)
    private PagamentoRecordOne criptPagamentoRecord(PagamentoRecordOne pagamentoRecordOne) {
        String id = (pagamentoRecordOne.id() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.id()) : null;
        String idCliente = criptografar.criptografar(secretKey, pagamentoRecordOne.idCliente());
        String idFilial = criptografar.criptografar(secretKey, pagamentoRecordOne.idFilial());
        String precoTotal = (pagamentoRecordOne.precoTotal() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.precoTotal()) : null;
        String precoPago = (pagamentoRecordOne.precoPago() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.precoPago()) : null;
        String dataCompra = (pagamentoRecordOne.dataCompra() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.dataCompra()) : null;
        String codPagamento = (pagamentoRecordOne.codPagamento() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.codPagamento()) : null;

        return new PagamentoRecordOne(id, idCliente, idFilial, precoTotal, precoPago, dataCompra, codPagamento);
    }

    private ItemPagamentoRecordOne criptItemPagamentoRecord(ItemPagamentoRecordOne itemPagamentoRecordOne) {
        String id = (itemPagamentoRecordOne.id() != null) ? criptografar.criptografar(secretKey, itemPagamentoRecordOne.id()) : null;
        String idPagamento = (itemPagamentoRecordOne.idPagamento() != null) ? criptografar.criptografar(secretKey, itemPagamentoRecordOne.idPagamento()) : null;
        String idItem = criptografar.criptografar(secretKey, itemPagamentoRecordOne.idItem());
        String nome = criptografar.criptografar(secretKey, itemPagamentoRecordOne.nome());
        String quantidade = criptografar.criptografar(secretKey, itemPagamentoRecordOne.quantidade());
        String precoUnit = criptografar.criptografar(secretKey, itemPagamentoRecordOne.precoUnit());

        return new ItemPagamentoRecordOne(id, idPagamento, idItem, nome, quantidade, precoUnit);
    }

    public PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> criptPagamentoPayload(PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> pagamentoPayloadRecord) {
        PagamentoRecordOne pagamentoRecordOne = criptPagamentoRecord(pagamentoPayloadRecord.pagamento());
        List<ItemPagamentoRecordOne> itemPagamentoList = pagamentoPayloadRecord.itemPagamentoList().stream().map(this::criptItemPagamentoRecord).toList();

        return new PagamentoPayloadRecord<>(pagamentoRecordOne, itemPagamentoList);
    }

    // Cript → Decript (RESPONSE)
    private PagamentoRecordOne decriptPagamentoRecordOne(PagamentoRecordOne pagamentoRecordOne) {
        String id = (pagamentoRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.id()) : null;
        String idCliente = descriptografar.descriptografar(secretKey, pagamentoRecordOne.idCliente());
        String idFilial = descriptografar.descriptografar(secretKey, pagamentoRecordOne.idFilial());
        String precoTotal = (pagamentoRecordOne.precoTotal() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.precoTotal()) : null;
        String precoPago = (pagamentoRecordOne.precoPago() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.precoPago()) : null;
        String dataCompra = (pagamentoRecordOne.dataCompra() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.dataCompra()) : null;
        String codPagamento = (pagamentoRecordOne.codPagamento() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.codPagamento()) : null;

        return new PagamentoRecordOne(id, idCliente, idFilial, precoTotal, precoPago, dataCompra, codPagamento);
    }

    private PagamentoRecordTwo decriptPagamentoRecordTwo(PagamentoRecordTwo pagamentoRecordTwo) {
        String idCliente = descriptografar.descriptografar(secretKey, pagamentoRecordTwo.idCliente());
        String precoTotal = (pagamentoRecordTwo.precoTotal() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.precoTotal()) : null;
        String precoPago = (pagamentoRecordTwo.precoPago() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.precoPago()) : null;
        String dataCompra = (pagamentoRecordTwo.dataCompra() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.dataCompra()) : null;
        String codPagamento = (pagamentoRecordTwo.codPagamento() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.codPagamento()) : null;

        return new PagamentoRecordTwo(idCliente, precoTotal, precoPago, dataCompra, codPagamento);
    }

    private ItemPagamentoRecordOne decriptItemPagamentoRecordOne(ItemPagamentoRecordOne itemPagamentoRecordOne) {
        String id = (itemPagamentoRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.id()) : null;
        String idPagamento = (itemPagamentoRecordOne.idPagamento() != null) ? descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.idPagamento()) : null;
        String idItem = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.idItem());
        String nome = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.nome());
        String quantidade = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.quantidade());
        String precoUnit = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.precoUnit());

        return new ItemPagamentoRecordOne(id, idPagamento, idItem, nome, quantidade, precoUnit);
    }

    private ItemPagamentoRecordTwo decriptItemPagamentoRecordTwo(ItemPagamentoRecordTwo itemPagamentoRecordTwo) {
        String idPagamento = (itemPagamentoRecordTwo.idPagamento() != null) ? descriptografar.descriptografar(secretKey, itemPagamentoRecordTwo.idPagamento()) : null;
        String idItem = descriptografar.descriptografar(secretKey, itemPagamentoRecordTwo.idItem());
        String quantidade = descriptografar.descriptografar(secretKey, itemPagamentoRecordTwo.quantidade());

        return new ItemPagamentoRecordTwo(idPagamento, idItem, quantidade);
    }

    public PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> decriptPagamentoPayloadRecordOne(PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> pagamentoPayloadRecord) {
        PagamentoRecordOne pagamentoRecordOne = decriptPagamentoRecordOne(pagamentoPayloadRecord.pagamento());
        List<ItemPagamentoRecordOne> itemPagamentoList = pagamentoPayloadRecord.itemPagamentoList().stream().map(this::decriptItemPagamentoRecordOne).toList();

        return new PagamentoPayloadRecord<>(pagamentoRecordOne, itemPagamentoList);
    }

    public PagamentoPayloadRecord<PagamentoRecordTwo, ItemPagamentoRecordTwo> decriptPagamentoPayloadRecordTwo(PagamentoPayloadRecord<PagamentoRecordTwo, ItemPagamentoRecordTwo> pagamentoPayloadRecord) {
        PagamentoRecordTwo pagamentoRecordTwo = decriptPagamentoRecordTwo(pagamentoPayloadRecord.pagamento());
        List<ItemPagamentoRecordTwo> itemPagamentoList = pagamentoPayloadRecord.itemPagamentoList().stream().map(this::decriptItemPagamentoRecordTwo).toList();

        return new PagamentoPayloadRecord<>(pagamentoRecordTwo, itemPagamentoList);
    }
}

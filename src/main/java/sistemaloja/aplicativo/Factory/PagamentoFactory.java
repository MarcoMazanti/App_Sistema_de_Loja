package sistemaloja.aplicativo.Factory;

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

    // Decript → Cript (REQUEST)
    public PagamentoRecordOne criptPagamento(PagamentoRecordOne pagamentoRecordOne) {
        String id = (pagamentoRecordOne.id() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.id()) : null;
        String idCliente = criptografar.criptografar(secretKey, pagamentoRecordOne.idCliente());
        String idFilial = criptografar.criptografar(secretKey, pagamentoRecordOne.idFilial());
        String precoTotal = (pagamentoRecordOne.precoTotal() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.precoTotal()) : null;
        String precoPago = (pagamentoRecordOne.precoPago() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.precoPago()) : null;
        String dataCompra = (pagamentoRecordOne.dataCompra() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.dataCompra()) : null;
        String codPagamento = (pagamentoRecordOne.codPagamento() != null) ? criptografar.criptografar(secretKey, pagamentoRecordOne.codPagamento()) : null;

        return new PagamentoRecordOne(id, idCliente, idFilial, precoTotal, precoPago, dataCompra, codPagamento);
    }

    public ItemPagamentoRecordOne criptItemPagamento(ItemPagamentoRecordOne itemPagamentoRecordOne) {
        String id = (itemPagamentoRecordOne.id() != null) ? criptografar.criptografar(secretKey, itemPagamentoRecordOne.id()) : null;
        String idPagamento = (itemPagamentoRecordOne.idPagamento() != null) ? criptografar.criptografar(secretKey, itemPagamentoRecordOne.idPagamento()) : null;
        String idItem = criptografar.criptografar(secretKey, itemPagamentoRecordOne.idItem());
        String nome = criptografar.criptografar(secretKey, itemPagamentoRecordOne.nome());
        String quantidade = criptografar.criptografar(secretKey, itemPagamentoRecordOne.quantidade());
        String precoUnit = criptografar.criptografar(secretKey, itemPagamentoRecordOne.precoUnit());

        return new ItemPagamentoRecordOne(id, idPagamento, idItem, nome, quantidade, precoUnit);
    }

    public PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> criptPagamentoPayload(PagamentoPayloadRecord<PagamentoRecordOne, ItemPagamentoRecordOne> pagamentoPayloadRecord) {
        PagamentoRecordOne pagamentoRecordOne = criptPagamento(pagamentoPayloadRecord.pagamento());
        List<ItemPagamentoRecordOne> itemPagamentoList = pagamentoPayloadRecord.itemPagamentoList().stream().map(this::criptItemPagamento).toList();

        return new PagamentoPayloadRecord<>(pagamentoRecordOne, itemPagamentoList);
    }

    // Cript → Decript (RESPONSE)
    public PagamentoRecordOne decriptPagamentoRecordOne(PagamentoRecordOne pagamentoRecordOne) {
        String id = (pagamentoRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.id()) : null;
        String idCliente = descriptografar.descriptografar(secretKey, pagamentoRecordOne.idCliente());
        String idFilial = descriptografar.descriptografar(secretKey, pagamentoRecordOne.idFilial());
        String precoTotal = (pagamentoRecordOne.precoTotal() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.precoTotal()) : null;
        String precoPago = (pagamentoRecordOne.precoPago() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.precoPago()) : null;
        String dataCompra = (pagamentoRecordOne.dataCompra() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.dataCompra()) : null;
        String codPagamento = (pagamentoRecordOne.codPagamento() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordOne.codPagamento()) : null;

        return new PagamentoRecordOne(id, idCliente, idFilial, precoTotal, precoPago, dataCompra, codPagamento);
    }

    public PagamentoRecordTwo decriptPagamentoRecordTwo(PagamentoRecordTwo pagamentoRecordTwo) {
        String idCliente = descriptografar.descriptografar(secretKey, pagamentoRecordTwo.idCliente());
        String precoTotal = (pagamentoRecordTwo.precoTotal() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.precoTotal()) : null;
        String precoPago = (pagamentoRecordTwo.precoPago() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.precoPago()) : null;
        String dataCompra = (pagamentoRecordTwo.dataCompra() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.dataCompra()) : null;
        String codPagamento = (pagamentoRecordTwo.codPagamento() != null) ? descriptografar.descriptografar(secretKey, pagamentoRecordTwo.codPagamento()) : null;

        return new PagamentoRecordTwo(idCliente, precoTotal, precoPago, dataCompra, codPagamento);
    }

    public ItemPagamentoRecordOne decriptItemPagamentoRecordOne(ItemPagamentoRecordOne itemPagamentoRecordOne) {
        String id = (itemPagamentoRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.id()) : null;
        String idPagamento = (itemPagamentoRecordOne.idPagamento() != null) ? descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.idPagamento()) : null;
        String idItem = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.idItem());
        String nome = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.nome());
        String quantidade = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.quantidade());
        String precoUnit = descriptografar.descriptografar(secretKey, itemPagamentoRecordOne.precoUnit());

        return new ItemPagamentoRecordOne(id, idPagamento, idItem, nome, quantidade, precoUnit);
    }

    public ItemPagamentoRecordTwo decriptItemPagamentoRecordTwo(ItemPagamentoRecordTwo itemPagamentoRecordTwo) {
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

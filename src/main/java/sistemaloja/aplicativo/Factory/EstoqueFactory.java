package sistemaloja.aplicativo.Factory;

import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordThree;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;

public class EstoqueFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public EstoqueFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    // Decript → Cript (REQUEST)
    public EstoqueRecordOne criptEstoque(EstoqueRecordOne estoqueRecordOne) {
        String id = (estoqueRecordOne.id() != null) ? criptografar.criptografar(secretKey, estoqueRecordOne.id()) : null;
        String nome = criptografar.criptografar(secretKey, estoqueRecordOne.nome());
        String idFilial = criptografar.criptografar(secretKey, estoqueRecordOne.idFilial());
        String idFornecedor = criptografar.criptografar(secretKey, estoqueRecordOne.idFornecedor());
        String preco = criptografar.criptografar(secretKey, estoqueRecordOne.preco());
        String quantidade = (estoqueRecordOne.quantidade() != null) ? criptografar.criptografar(secretKey, estoqueRecordOne.quantidade()) : null;
        String descricao = (estoqueRecordOne.descricao() != null) ? criptografar.criptografar(secretKey, estoqueRecordOne.descricao()) : null;
        String codItem = (estoqueRecordOne.codItem() != null) ? criptografar.criptografar(secretKey, estoqueRecordOne.codItem()) : null;

        return new EstoqueRecordOne(id, nome, idFilial, idFornecedor, preco, quantidade, descricao, codItem);
    }

    // Cript → Decript (RESPONSE)
    public EstoqueRecordOne decriptEstoqueRecordOne(EstoqueRecordOne estoqueRecordOne) {
        String id = (estoqueRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordOne.id()) : null;
        String nome = descriptografar.descriptografar(secretKey, estoqueRecordOne.nome());
        String idFilial = descriptografar.descriptografar(secretKey, estoqueRecordOne.idFilial());
        String idFornecedor = descriptografar.descriptografar(secretKey, estoqueRecordOne.idFornecedor());
        String preco = descriptografar.descriptografar(secretKey, estoqueRecordOne.preco());
        String quantidade = (estoqueRecordOne.quantidade() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordOne.quantidade()) : null;
        String descricao = (estoqueRecordOne.descricao() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordOne.descricao()) : null;
        String codItem = (estoqueRecordOne.codItem() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordOne.codItem()) : null;

        return new EstoqueRecordOne(id, nome, idFilial, idFornecedor, preco, quantidade, descricao, codItem);
    }

    public EstoqueRecordTwo decriptEstoqueRecordTwo(EstoqueRecordTwo estoqueRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, estoqueRecordTwo.nome());
        String idFornecedor = descriptografar.descriptografar(secretKey, estoqueRecordTwo.idFornecedor());
        String preco = descriptografar.descriptografar(secretKey, estoqueRecordTwo.preco());
        String quantidade = (estoqueRecordTwo.quantidade() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.quantidade()) : null;
        String descricao = (estoqueRecordTwo.descricao() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.descricao()) : null;
        String codItem = (estoqueRecordTwo.codItem() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.codItem()) : null;

        return new EstoqueRecordTwo(nome, idFornecedor, preco, quantidade, descricao, codItem);
    }

    public EstoqueRecordThree decriptEstoqueRecordThree(EstoqueRecordThree estoqueRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, estoqueRecordThree.nome());
        String preco = descriptografar.descriptografar(secretKey, estoqueRecordThree.preco());
        String descricao = (estoqueRecordThree.descricao() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordThree.descricao()) : null;
        String codItem = (estoqueRecordThree.codItem() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordThree.codItem()) : null;

        return new EstoqueRecordThree(nome, preco, descricao, codItem);
    }
}

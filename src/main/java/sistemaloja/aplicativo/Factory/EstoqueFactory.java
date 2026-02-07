package sistemaloja.aplicativo.Factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordThree;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordTwo;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordOne;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordThree;
import sistemaloja.aplicativo.Entity.Estoque.EstoqueRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.List;

public class EstoqueFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public EstoqueFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Object criptEstoque(Object objeto) {
        if (objeto instanceof List<?> lista) {
            List<EstoqueRecordOne> estoqueRecordOneList = new java.util.ArrayList<>();

            for (Object estoque : lista) {
                estoqueRecordOneList.add(criptEstoqueRecord((EstoqueRecordOne) estoque));
            }

            return estoqueRecordOneList;
        } else {
            return criptEstoqueRecord((EstoqueRecordOne) objeto);
        }
    }

    public <T> List<T> decriptEstoqueList(List<T> objeto) {
        List<T> estoqueList = new java.util.ArrayList<>();

        for (T estoque : objeto) {
            estoqueList.add(decriptEstoque(estoque));
        }

        return estoqueList;
    }

    public <T> T decriptEstoque(T objeto) {
        return (T) switch (objeto) {
            case EstoqueRecordOne estoqueRecordOne -> decriptEstoqueRecordOne(estoqueRecordOne);
            case EstoqueRecordTwo estoqueRecordTwo -> decriptEstoqueRecordTwo(estoqueRecordTwo);
            case EstoqueRecordThree estoqueRecordThree -> decriptEstoqueRecordThree(estoqueRecordThree);
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
        boolean temQuant = !json.findPath("quantidade").isMissingNode();

        if (temId) return EstoqueRecordOne.class;
        if (temQuant) return EstoqueRecordTwo.class;
        return EstoqueRecordThree.class;
    }

    // Decript → Cript (REQUEST)
    private EstoqueRecordOne criptEstoqueRecord(EstoqueRecordOne estoqueRecordOne) {
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
    private EstoqueRecordOne decriptEstoqueRecordOne(EstoqueRecordOne estoqueRecordOne) {
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

    private EstoqueRecordTwo decriptEstoqueRecordTwo(EstoqueRecordTwo estoqueRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, estoqueRecordTwo.nome());
        String idFornecedor = descriptografar.descriptografar(secretKey, estoqueRecordTwo.idFornecedor());
        String preco = descriptografar.descriptografar(secretKey, estoqueRecordTwo.preco());
        String quantidade = (estoqueRecordTwo.quantidade() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.quantidade()) : null;
        String descricao = (estoqueRecordTwo.descricao() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.descricao()) : null;
        String codItem = (estoqueRecordTwo.codItem() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordTwo.codItem()) : null;

        return new EstoqueRecordTwo(nome, idFornecedor, preco, quantidade, descricao, codItem);
    }

    private EstoqueRecordThree decriptEstoqueRecordThree(EstoqueRecordThree estoqueRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, estoqueRecordThree.nome());
        String preco = descriptografar.descriptografar(secretKey, estoqueRecordThree.preco());
        String descricao = (estoqueRecordThree.descricao() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordThree.descricao()) : null;
        String codItem = (estoqueRecordThree.codItem() != null) ? descriptografar.descriptografar(secretKey, estoqueRecordThree.codItem()) : null;

        return new EstoqueRecordThree(nome, preco, descricao, codItem);
    }
}

package sistemaloja.aplicativo.Factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordThree;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordTwo;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordOne;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordThree;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

public class FornecedorFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public FornecedorFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public <T> Object criptFornecedor(Object objeto) {
        if (objeto instanceof List<?> lista) {
            List<FornecedorRecordOne> fornecedorRecordOneList = new ArrayList<>();

            for (Object fornecedor : lista) {
                fornecedorRecordOneList.add(criptFornecedorRecord((FornecedorRecordOne) fornecedor));
            }

            return fornecedorRecordOneList;
        } else {
            return criptFornecedorRecord((FornecedorRecordOne) objeto);
        }
    }

    public <T> List<T> decriptFornecedorList(List<T> objeto) {
        List<T> fornecedorList = new ArrayList<>();

        for (T fornecedor : objeto) {
            fornecedorList.add(decriptFornecedor(fornecedor));
        }

        return fornecedorList;
    }

    public <T> T decriptFornecedor(T objeto) {
        return (T) switch (objeto) {
            case FornecedorRecordOne fornecedorRecordOne -> decriptFornecedorRecordOne(fornecedorRecordOne);
            case FornecedorRecordTwo fornecedorRecordTwo -> decriptFornecedorRecordTwo(fornecedorRecordTwo);
            case FornecedorRecordThree fornecedorRecordThree -> decriptFornecedorRecordThree(fornecedorRecordThree);
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
        boolean temCpfOrCnpj = !json.findPath("cpfOrCnpj").isMissingNode();

        if (temId) return FornecedorRecordOne.class;
        if (temCpfOrCnpj) return FornecedorRecordTwo.class;
        return FornecedorRecordThree.class;
    }

    // Decript → Cript (REQUEST)
    private FornecedorRecordOne criptFornecedorRecord(FornecedorRecordOne fornecedorRecordOne) {
        String id = (fornecedorRecordOne.id() != null) ? criptografar.criptografar(secretKey, fornecedorRecordOne.id()) : null;
        String nome = criptografar.criptografar(secretKey, fornecedorRecordOne.nome());
        String cpfOrCnpj = criptografar.criptografar(secretKey, fornecedorRecordOne.cpfOrCnpj());
        String email = criptografar.criptografar(secretKey, fornecedorRecordOne.email());
        String telefone = (fornecedorRecordOne.telefone() != null) ? criptografar.criptografar(secretKey, fornecedorRecordOne.telefone()) : null;
        String fullAdress = (fornecedorRecordOne.fullAdress() != null) ? criptografar.criptografar(secretKey, fornecedorRecordOne.fullAdress()) : null;
        String codCountry = criptografar.criptografar(secretKey, fornecedorRecordOne.codCountry());
        String codEstado = criptografar.criptografar(secretKey, fornecedorRecordOne.codEstado());
        String codCidade = criptografar.criptografar(secretKey, fornecedorRecordOne.codCidade());
        String codFornecedor = (fornecedorRecordOne.codFornecedor() != null) ? criptografar.criptografar(secretKey, fornecedorRecordOne.codFornecedor()) : null;

        return new FornecedorRecordOne(id, nome, cpfOrCnpj, email, telefone, fullAdress, codCountry, codEstado, codCidade, codFornecedor);
    }

    // Cript → Decript (RESPONSE)
    private FornecedorRecordOne decriptFornecedorRecordOne(FornecedorRecordOne fornecedorRecordOne) {
        String id = (fornecedorRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordOne.id()) : null;
        String nome = descriptografar.descriptografar(secretKey, fornecedorRecordOne.nome());
        String cpfOrCnpj = descriptografar.descriptografar(secretKey, fornecedorRecordOne.cpfOrCnpj());
        String email = descriptografar.descriptografar(secretKey, fornecedorRecordOne.email());
        String telefone = (fornecedorRecordOne.telefone() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordOne.telefone()) : null;
        String fullAdress = (fornecedorRecordOne.fullAdress() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordOne.fullAdress()) : null;
        String codCountry = descriptografar.descriptografar(secretKey, fornecedorRecordOne.codCountry());
        String codEstado = descriptografar.descriptografar(secretKey, fornecedorRecordOne.codEstado());
        String codCidade = descriptografar.descriptografar(secretKey, fornecedorRecordOne.codCidade());
        String codFornecedor = (fornecedorRecordOne.codFornecedor() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordOne.codFornecedor()) : null;

        return new FornecedorRecordOne(id, nome, cpfOrCnpj, email, telefone, fullAdress, codCountry, codEstado, codCidade, codFornecedor);
    }

    private FornecedorRecordTwo decriptFornecedorRecordTwo(FornecedorRecordTwo fornecedorRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.nome());
        String cpfOrCnpj = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.cpfOrCnpj());
        String email = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.email());
        String telefone = (fornecedorRecordTwo.telefone() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.telefone()) : null;
        String fullAdress = (fornecedorRecordTwo.fullAdress() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.fullAdress()) : null;
        String codFornecedor = (fornecedorRecordTwo.codFornecedor() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.codFornecedor()) : null;

        return new FornecedorRecordTwo(nome, cpfOrCnpj, email, telefone, fullAdress, codFornecedor);
    }

    private FornecedorRecordThree decriptFornecedorRecordThree(FornecedorRecordThree fornecedorRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, fornecedorRecordThree.nome());
        String email = descriptografar.descriptografar(secretKey, fornecedorRecordThree.email());
        String telefone = (fornecedorRecordThree.telefone() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordThree.telefone()) : null;
        String codFornecedor = (fornecedorRecordThree.codFornecedor() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordThree.codFornecedor()) : null;

        return new FornecedorRecordThree(nome, email, telefone, codFornecedor);
    }
}

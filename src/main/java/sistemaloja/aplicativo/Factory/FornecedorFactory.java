package sistemaloja.aplicativo.Factory;

import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordOne;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordThree;
import sistemaloja.aplicativo.Entity.Fornecedor.FornecedorRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;

public class FornecedorFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public FornecedorFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    // Decript → Cript (REQUEST)
    public FornecedorRecordOne criptFornecedor(FornecedorRecordOne fornecedorRecordOne) {
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
    public FornecedorRecordOne decriptFornecedorRecordOne(FornecedorRecordOne fornecedorRecordOne) {
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

    public FornecedorRecordTwo decriptFornecedorRecordTwo(FornecedorRecordTwo fornecedorRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.nome());
        String cpfOrCnpj = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.cpfOrCnpj());
        String email = descriptografar.descriptografar(secretKey, fornecedorRecordTwo.email());
        String telefone = (fornecedorRecordTwo.telefone() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.telefone()) : null;
        String fullAdress = (fornecedorRecordTwo.fullAdress() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.fullAdress()) : null;
        String codFornecedor = (fornecedorRecordTwo.codFornecedor() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordTwo.codFornecedor()) : null;

        return new FornecedorRecordTwo(nome, cpfOrCnpj, email, telefone, fullAdress, codFornecedor);
    }

    public FornecedorRecordThree decriptFornecedorRecordThree(FornecedorRecordThree fornecedorRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, fornecedorRecordThree.nome());
        String email = descriptografar.descriptografar(secretKey, fornecedorRecordThree.email());
        String telefone = (fornecedorRecordThree.telefone() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordThree.telefone()) : null;
        String codFornecedor = (fornecedorRecordThree.codFornecedor() != null) ? descriptografar.descriptografar(secretKey, fornecedorRecordThree.codFornecedor()) : null;

        return new FornecedorRecordThree(nome, email, telefone, codFornecedor);
    }
}

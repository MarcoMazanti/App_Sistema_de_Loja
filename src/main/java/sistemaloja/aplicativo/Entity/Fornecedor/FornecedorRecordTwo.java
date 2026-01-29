package sistemaloja.aplicativo.Entity.Fornecedor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FornecedorRecordTwo(String nome, String cpfOrCnpj, String email, String telefone, String fullAdress, String codFornecedor) {
    @JsonCreator
    public FornecedorRecordTwo(@JsonProperty("nome") String nome,
                               @JsonProperty("cpfOrCnpj") String cpfOrCnpj,
                               @JsonProperty("email") String email,
                               @JsonProperty("telefone") String telefone,
                               @JsonProperty("fullAdress") String fullAdress,
                               @JsonProperty("codFornecedor") String codFornecedor) {
        this.nome = nome;
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email;
        this.telefone = telefone;
        this.fullAdress = fullAdress;
        this.codFornecedor = codFornecedor;
    }
}
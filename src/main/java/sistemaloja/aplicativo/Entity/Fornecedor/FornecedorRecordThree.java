package sistemaloja.aplicativo.Entity.Fornecedor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FornecedorRecordThree(String nome, String email, String telefone, String codFornecedor) {
    @JsonCreator
    public FornecedorRecordThree(@JsonProperty("nome") String nome,
                                 @JsonProperty("email") String email,
                                 @JsonProperty("telefone") String telefone,
                                 @JsonProperty("codFornecedor") String codFornecedor) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.codFornecedor = codFornecedor;
    }
}
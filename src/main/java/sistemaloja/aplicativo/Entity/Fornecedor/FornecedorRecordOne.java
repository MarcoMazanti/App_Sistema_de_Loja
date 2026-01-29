package sistemaloja.aplicativo.Entity.Fornecedor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FornecedorRecordOne(String id, String nome, String cpfOrCnpj, String email, String telefone, String fullAdress,
                                  String codCountry, String codEstado, String codCidade, String codFornecedor) {
    @JsonCreator
    public FornecedorRecordOne(@JsonProperty("id") String id,
                               @JsonProperty("nome") String nome,
                               @JsonProperty("cpfOrCnpj") String cpfOrCnpj,
                               @JsonProperty("email") String email,
                               @JsonProperty("telefone") String telefone,
                               @JsonProperty("fullAdress") String fullAdress,
                               @JsonProperty("codCountry") String codCountry,
                               @JsonProperty("codEstado") String codEstado,
                               @JsonProperty("codCidade") String codCidade,
                               @JsonProperty("codFornecedor") String codFornecedor) {
        this.id = id;
        this.nome = nome;
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email;
        this.telefone = telefone;
        this.fullAdress = fullAdress;
        this.codCountry = codCountry;
        this.codEstado = codEstado;
        this.codCidade = codCidade;
        this.codFornecedor = codFornecedor;
    }
}
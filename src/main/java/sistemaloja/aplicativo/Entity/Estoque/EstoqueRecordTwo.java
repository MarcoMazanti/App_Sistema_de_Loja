package sistemaloja.aplicativo.Entity.Estoque;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EstoqueRecordTwo(String nome, String idFornecedor, String preco, String quantidade, String descricao, String codItem) {
    @JsonCreator
    public EstoqueRecordTwo(@JsonProperty("nome") String nome,
                            @JsonProperty("idFornecedor") String idFornecedor,
                            @JsonProperty("preco") String preco,
                            @JsonProperty("quantidade") String quantidade,
                            @JsonProperty("descricao") String descricao,
                            @JsonProperty("codItem") String codItem) {
        this.nome = nome;
        this.idFornecedor = idFornecedor;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.codItem = codItem;
    }
}
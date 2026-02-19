package sistemaloja.aplicativo.Entity.Estoque;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EstoqueRecordOne(String id, String nome, String idFilial, String idFornecedor, String preco, String quantidade, String descricao, String codItem) {
    @JsonCreator
    public EstoqueRecordOne(@JsonProperty("id") String id,
                            @JsonProperty("nome") String nome,
                            @JsonProperty("idFilial") String idFilial,
                            @JsonProperty("idFornecedor") String idFornecedor,
                            @JsonProperty("preco") String preco,
                            @JsonProperty("quantidade") String quantidade,
                            @JsonProperty("descricao") String descricao,
                            @JsonProperty("codItem") String codItem) {
        this.id = id;
        this.nome = nome;
        this.idFilial = idFilial;
        this.idFornecedor = idFornecedor;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.codItem = codItem;
    }

    public boolean emBaixoEstoque() {
        return quantidade != null && Integer.parseInt(quantidade) <= 10;
    }

    public boolean emFalta() {
        return quantidade != null && Integer.parseInt(quantidade) <= 0;
    }
}
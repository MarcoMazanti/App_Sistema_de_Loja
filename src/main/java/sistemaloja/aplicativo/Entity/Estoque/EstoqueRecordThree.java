package sistemaloja.aplicativo.Entity.Estoque;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EstoqueRecordThree(String nome, String preco, String descricao, String codItem) {
    @JsonCreator
    public EstoqueRecordThree(@JsonProperty("nome") String nome,
                              @JsonProperty("preco") String preco,
                              @JsonProperty("descricao") String descricao,
                              @JsonProperty("codItem") String codItem) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.codItem = codItem;
    }
}
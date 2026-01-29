package sistemaloja.aplicativo.Entity.Pagamento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemPagamentoRecordTwo(String idPagamento, String idItem, String quantidade) {
    @JsonCreator
    public ItemPagamentoRecordTwo(@JsonProperty("idPagamento") String idPagamento,
                                  @JsonProperty("idItem") String idItem,
                                  @JsonProperty("quantidade") String quantidade) {
        this.idPagamento = idPagamento;
        this.idItem = idItem;
        this.quantidade = quantidade;
    }
}
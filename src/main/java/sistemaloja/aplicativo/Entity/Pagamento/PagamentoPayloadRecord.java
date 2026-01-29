package sistemaloja.aplicativo.Entity.Pagamento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PagamentoPayloadRecord<T, IT>(T pagamento, List<IT> itemPagamentoList) {
    @JsonCreator
    public PagamentoPayloadRecord(@JsonProperty("pagamento") T pagamento,
                                  @JsonProperty("itemPagamentoList") List<IT> itemPagamentoList) {
        this.pagamento = pagamento;
        this.itemPagamentoList = itemPagamentoList;
    }
}
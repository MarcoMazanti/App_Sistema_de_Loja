package sistemaloja.aplicativo.Entity.Pagamento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoRecordOne(String id, String idCliente, String idFilial, String precoTotal, String precoPago, String dataCompra, String codPagamento) {
    @JsonCreator
    public PagamentoRecordOne(@JsonProperty("id") String id,
                              @JsonProperty("idCliente") String idCliente,
                              @JsonProperty("idFilial") String idFilial,
                              @JsonProperty("precoTotal") String precoTotal,
                              @JsonProperty("precoPago") String precoPago,
                              @JsonProperty("dataCompra") String dataCompra,
                              @JsonProperty("codPagamento") String codPagamento) {
        this.id = id;
        this.idCliente = idCliente;
        this.idFilial = idFilial;
        this.precoTotal = precoTotal;
        this.precoPago = precoPago;
        this.dataCompra = dataCompra;
        this.codPagamento = codPagamento;
    }
}
package sistemaloja.aplicativo.Entity.Pagamento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoRecordTwo(String idCliente, String precoTotal, String precoPago, String dataCompra, String codPagamento) {
    @JsonCreator
    public PagamentoRecordTwo(@JsonProperty("idCliente") String idCliente,
                              @JsonProperty("precoTotal") String precoTotal,
                              @JsonProperty("precoPago") String precoPago,
                              @JsonProperty("dataCompra") String dataCompra,
                              @JsonProperty("codPagamento") String codPagamento) {
        this.idCliente = idCliente;
        this.precoTotal = precoTotal;
        this.precoPago = precoPago;
        this.dataCompra = dataCompra;
        this.codPagamento = codPagamento;
    }
}
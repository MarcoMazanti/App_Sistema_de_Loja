package sistemaloja.aplicativo.Entity.Filial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FilialRecordOne(String id, String cnpj, String telefone, String quantEmpregados, String fullAdress, String codCountry,
                              String codEstado, String codCidade, String codFilial) {
    @JsonCreator
    public FilialRecordOne(@JsonProperty("id") String id,
                           @JsonProperty("cnpj") String cnpj,
                           @JsonProperty("telefone") String telefone,
                           @JsonProperty("quantEmpregados") String quantEmpregados,
                           @JsonProperty("fullAdress") String fullAdress,
                           @JsonProperty("codCountry") String codCountry,
                           @JsonProperty("codEstado") String codEstado,
                           @JsonProperty("codCidade") String codCidade,
                           @JsonProperty("codFilial") String codFilial) {
        this.id = id;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.quantEmpregados = quantEmpregados;
        this.fullAdress = fullAdress;
        this.codCountry = codCountry;
        this.codEstado = codEstado;
        this.codCidade = codCidade;
        this.codFilial = codFilial;
    }
}
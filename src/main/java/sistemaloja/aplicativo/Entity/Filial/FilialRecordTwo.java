package sistemaloja.aplicativo.Entity.Filial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FilialRecordTwo(String cnpj, String telefone, String quantEmpregados, String fullAdress, String codFilial) {
    @JsonCreator
    public FilialRecordTwo(@JsonProperty("cnpj") String cnpj,
                           @JsonProperty("telefone") String telefone,
                           @JsonProperty("quantEmpregados") String quantEmpregados,
                           @JsonProperty("fullAdress") String fullAdress,
                           @JsonProperty("codFilial") String codFilial) {
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.quantEmpregados = quantEmpregados;
        this.fullAdress = fullAdress;
        this.codFilial = codFilial;
    }
}
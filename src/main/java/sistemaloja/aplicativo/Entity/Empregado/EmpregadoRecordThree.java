package sistemaloja.aplicativo.Entity.Empregado;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmpregadoRecordThree(String nome, String email, String telefone, String codEmpregado) {
    @JsonCreator
    public EmpregadoRecordThree(@JsonProperty("nome") String nome,
                                @JsonProperty("email") String email,
                                @JsonProperty("telefone") String telefone,
                                @JsonProperty("codEmpregado") String codEmpregado) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.codEmpregado = codEmpregado;
    }
}
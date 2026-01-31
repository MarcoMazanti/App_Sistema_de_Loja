package sistemaloja.aplicativo.Entity.Empregado;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Login(String cpf, String senha) {
    @JsonCreator
    public Login(@JsonProperty("cpf") String cpf, @JsonProperty("senha") String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }
}

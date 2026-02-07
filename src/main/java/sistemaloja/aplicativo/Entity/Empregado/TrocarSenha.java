package sistemaloja.aplicativo.Entity.Empregado;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TrocarSenha(String cpf, String email, String senha) {
    @JsonCreator
    public TrocarSenha(@JsonProperty("cpf") String cpf,
                       @JsonProperty("email") String email,
                       @JsonProperty("senha") String senha) {
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
}
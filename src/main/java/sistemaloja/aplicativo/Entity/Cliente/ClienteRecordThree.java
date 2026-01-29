package sistemaloja.aplicativo.Entity.Cliente;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ClienteRecordThree(String nome, String email, String telefone, String codCliente) {
    @JsonCreator
    public ClienteRecordThree(@JsonProperty("nome") String nome,
                              @JsonProperty("email") String email,
                              @JsonProperty("telefone") String telefone,
                              @JsonProperty("codCliente") String codCliente) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.codCliente = codCliente;
    }
}
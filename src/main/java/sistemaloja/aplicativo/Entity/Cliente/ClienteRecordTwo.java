package sistemaloja.aplicativo.Entity.Cliente;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ClienteRecordTwo(String nome, String cpfOrCnpj, String email, String telefone, String fullAdress, String codCliente) {
    @JsonCreator
    public ClienteRecordTwo(@JsonProperty("nome") String nome,
                            @JsonProperty("cpfOrCnpj") String cpfOrCnpj,
                            @JsonProperty("email") String email,
                            @JsonProperty("telefone") String telefone,
                            @JsonProperty("fullAdress") String fullAdress,
                            @JsonProperty("codCliente") String codCliente) {
        this.nome = nome;
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email;
        this.telefone = telefone;
        this.fullAdress = fullAdress;
        this.codCliente = codCliente;
    }
}
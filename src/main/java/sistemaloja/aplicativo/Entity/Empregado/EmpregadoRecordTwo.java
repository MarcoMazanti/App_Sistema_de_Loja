package sistemaloja.aplicativo.Entity.Empregado;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmpregadoRecordTwo(String nome, String cpf, String email, String telefone, String salario, String cargo,
                                 String dataAdimissao, String codEmpregado) {
    @JsonCreator
    public EmpregadoRecordTwo(@JsonProperty("nome") String nome,
                              @JsonProperty("cpf") String cpf,
                              @JsonProperty("email") String email,
                              @JsonProperty("telefone") String telefone,
                              @JsonProperty("salario") String salario,
                              @JsonProperty("cargo") String cargo,
                              @JsonProperty("dataAdimissao") String dataAdimissao,
                              @JsonProperty("codEmpregado") String codEmpregado) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.salario = salario;
        this.cargo = cargo;
        this.dataAdimissao = dataAdimissao;
        this.codEmpregado = codEmpregado;
    }
}
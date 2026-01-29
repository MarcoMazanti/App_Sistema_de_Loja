package sistemaloja.aplicativo.Entity.Empregado;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmpregadoRecordOne(String id, String nome, String cpf, String senha, String email, String telefone, String salario,
                                 String cargo, String filialId, String aniversario, String dataAdimissao, String codEmpregado) {
    @JsonCreator
    public EmpregadoRecordOne(@JsonProperty("id") String id,
                              @JsonProperty("nome") String nome,
                              @JsonProperty("cpf") String cpf,
                              @JsonProperty("senha") String senha,
                              @JsonProperty("email") String email,
                              @JsonProperty("telefone") String telefone,
                              @JsonProperty("salario") String salario,
                              @JsonProperty("cargo") String cargo,
                              @JsonProperty("filialId") String filialId,
                              @JsonProperty("aniversario") String aniversario,
                              @JsonProperty("dataAdimissao") String dataAdimissao,
                              @JsonProperty("codEmpregado") String codEmpregado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.salario = salario;
        this.cargo = cargo;
        this.filialId = filialId;
        this.aniversario = aniversario;
        this.dataAdimissao = dataAdimissao;
        this.codEmpregado = codEmpregado;
    }
}
package sistemaloja.aplicativo.Factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordOne;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordThree;
import sistemaloja.aplicativo.Entity.Empregado.EmpregadoRecordTwo;
import sistemaloja.aplicativo.Entity.Empregado.Login;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.List;

public class EmpregadoFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public EmpregadoFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Object criptEmpregado(Object objeto) {
        if (objeto instanceof List<?> lista) {
            if (!lista.isEmpty()) {
                List<Object> empregadoList = new java.util.ArrayList<>();

                for (Object empregado : lista) {
                    empregadoList.add(criptEmpregadoUnit(empregado));
                }

                return empregadoList;
            } else {
                return null;
            }
        } else {
            return criptEmpregadoUnit(objeto);
        }
    }

    private Object criptEmpregadoUnit(Object object) {
        return switch (object) {
            case EmpregadoRecordOne empregadoRecordOne -> criptEmpregadoRecord(empregadoRecordOne);
            case Login login -> criptLogin(login);
            default -> null;
        };
    }

    public <T> List<T> decriptEmpregadoList(List<T> objeto) {
        List<T> empregadoList = new java.util.ArrayList<>();

        for (T empregado : objeto) {
            empregadoList.add(descriptEmpregado(empregado));
        }

        return empregadoList;
    }

    public <T> T descriptEmpregado(T objeto) {
        return (T) switch (objeto) {
            case EmpregadoRecordOne empregadoRecordOne -> decriptEmpregadoRecordOne(empregadoRecordOne);
            case EmpregadoRecordTwo empregadoRecordTwo -> decriptEmpregadoRecordTwo(empregadoRecordTwo);
            case EmpregadoRecordThree empregadoRecordThree -> decriptEmpregadoRecordThree(empregadoRecordThree);
            default -> null;
        };
    }

    public Class<?> retornaClasse(Object body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;

        if (body instanceof String s) json = mapper.readTree(s);
        else json = mapper.valueToTree(body);

        if (json.isArray() && !json.isEmpty()) json = json.get(0);

        boolean temId  = !json.findPath("id").isMissingNode();
        boolean temCpf = !json.findPath("cpf").isMissingNode();

        if (temId && temCpf) return EmpregadoRecordOne.class;
        if (temCpf) return EmpregadoRecordTwo.class;
        return EmpregadoRecordThree.class;
    }

    // Decript → Cript (REQUEST)
    private EmpregadoRecordOne criptEmpregadoRecord(EmpregadoRecordOne empregadoRecordOne) {
        String id = (empregadoRecordOne.id() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.id()) : null;
        String nome = criptografar.criptografar(secretKey, empregadoRecordOne.nome());
        String cpf = criptografar.criptografar(secretKey, empregadoRecordOne.cpf());
        String senha = criptografar.criptografar(secretKey, empregadoRecordOne.senha());
        String email = criptografar.criptografar(secretKey, empregadoRecordOne.email());
        String telefone = (empregadoRecordOne.telefone() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.telefone()) : null;
        String salario = criptografar.criptografar(secretKey, empregadoRecordOne.salario());
        String cargo = (empregadoRecordOne.cargo() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.cargo()) : null;
        String filialId = criptografar.criptografar(secretKey, empregadoRecordOne.filialId());
        String aniversario = (empregadoRecordOne.aniversario() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.aniversario()) : null;
        String dataAdimissao = (empregadoRecordOne.dataAdimissao() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.dataAdimissao()) : null;
        String codEmpregado = (empregadoRecordOne.codEmpregado() != null) ? criptografar.criptografar(secretKey, empregadoRecordOne.codEmpregado()) : null;

        return new EmpregadoRecordOne(id, nome, cpf, senha, email, telefone, salario, cargo, filialId, aniversario, dataAdimissao, codEmpregado);
    }

    private Login criptLogin(Login login) {
        String cpf = criptografar.criptografar(secretKey, login.cpf());
        String senha = criptografar.criptografar(secretKey, login.senha());

        return new Login(cpf, senha);
    }

    // Cript → Decript (RESPONSE)
    private EmpregadoRecordOne decriptEmpregadoRecordOne(EmpregadoRecordOne empregadoRecordOne) {
        String id = (empregadoRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.id()) : null;
        String nome = descriptografar.descriptografar(secretKey, empregadoRecordOne.nome());
        String cpf = descriptografar.descriptografar(secretKey, empregadoRecordOne.cpf());
        String senha = descriptografar.descriptografar(secretKey, empregadoRecordOne.senha());
        String email = descriptografar.descriptografar(secretKey, empregadoRecordOne.email());
        String telefone = (empregadoRecordOne.telefone() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.telefone()) : null;
        String salario = descriptografar.descriptografar(secretKey, empregadoRecordOne.salario());
        String cargo = (empregadoRecordOne.cargo() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.cargo()) : null;
        String filialId = descriptografar.descriptografar(secretKey, empregadoRecordOne.filialId());
        String aniversario = (empregadoRecordOne.aniversario() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.aniversario()) : null;
        String dataAdimissao = (empregadoRecordOne.dataAdimissao() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.dataAdimissao()) : null;
        String codEmpregado = (empregadoRecordOne.codEmpregado() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordOne.codEmpregado()) : null;

        return new EmpregadoRecordOne(id, nome, cpf, senha, email, telefone, salario, cargo, filialId, aniversario, dataAdimissao, codEmpregado);
    }

    private EmpregadoRecordTwo decriptEmpregadoRecordTwo(EmpregadoRecordTwo empregadoRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, empregadoRecordTwo.nome());
        String cpf = descriptografar.descriptografar(secretKey, empregadoRecordTwo.cpf());
        String email = descriptografar.descriptografar(secretKey, empregadoRecordTwo.email());
        String telefone = (empregadoRecordTwo.telefone() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordTwo.telefone()) : null;
        String salario = descriptografar.descriptografar(secretKey, empregadoRecordTwo.salario());
        String cargo = (empregadoRecordTwo.cargo() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordTwo.cargo()) : null;
        String dataAdimissao = (empregadoRecordTwo.dataAdimissao() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordTwo.dataAdimissao()) : null;
        String codEmpregado = (empregadoRecordTwo.codEmpregado() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordTwo.codEmpregado()) : null;

        return new EmpregadoRecordTwo(nome, cpf, email, telefone, salario, cargo, dataAdimissao, codEmpregado);
    }

    private EmpregadoRecordThree decriptEmpregadoRecordThree(EmpregadoRecordThree empregadoRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, empregadoRecordThree.nome());
        String email = descriptografar.descriptografar(secretKey, empregadoRecordThree.email());
        String telefone = (empregadoRecordThree.telefone() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordThree.telefone()) : null;
        String codEmpregado = (empregadoRecordThree.codEmpregado() != null) ? descriptografar.descriptografar(secretKey, empregadoRecordThree.codEmpregado()) : null;

        return new EmpregadoRecordThree(nome, email, telefone, codEmpregado);
    }
}

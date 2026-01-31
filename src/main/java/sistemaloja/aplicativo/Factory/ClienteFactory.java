package sistemaloja.aplicativo.Factory;

import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordOne;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordThree;
import sistemaloja.aplicativo.Entity.Cliente.ClienteRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.List;

public class ClienteFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public ClienteFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Object criptCliente(Object objeto) {
        if (objeto instanceof List<?> lista) {
            if (!lista.isEmpty()) {
                List<Object> clienteList = new java.util.ArrayList<>();

                for (Object cliente : lista) {
                    clienteList.add(criptCliente(cliente));
                }

                return clienteList;
            } else {
                return null;
            }
        } else {
            return criptClienteRecord((ClienteRecordOne) objeto);
        }
    }

    public <T> List<T> decriptClienteList(List<T> objeto) {
        List<T> clienteList = new java.util.ArrayList<>();

        for (T cliente : objeto) {
            clienteList.add(decriptCliente(cliente));
        }

        return clienteList;
    }

    public <T> T decriptCliente(T objeto) {
        return (T) switch (objeto) {
            case ClienteRecordOne clienteRecordOne -> decriptClienteRecordOne(clienteRecordOne);
            case ClienteRecordTwo clienteRecordTwo -> decriptClienteRecordTwo(clienteRecordTwo);
            case ClienteRecordThree clienteRecordThree -> decriptClienteRecordThree(clienteRecordThree);
            default -> null;
        };
    }

    // Decript → Cript (REQUEST)
    private ClienteRecordOne criptClienteRecord(ClienteRecordOne clienteRecordOne) {
        String id = (clienteRecordOne.id() != null) ? criptografar.criptografar(secretKey, clienteRecordOne.id()) : null;
        String nome = criptografar.criptografar(secretKey, clienteRecordOne.nome());
        String cpfOrCnpj = criptografar.criptografar(secretKey, clienteRecordOne.cpfOrCnpj());
        String email = criptografar.criptografar(secretKey, clienteRecordOne.email());
        String telefone = criptografar.criptografar(secretKey, clienteRecordOne.telefone());
        String fullAdress = criptografar.criptografar(secretKey, clienteRecordOne.fullAdress());
        String codCountry = criptografar.criptografar(secretKey, clienteRecordOne.codCountry());
        String codEstado = criptografar.criptografar(secretKey, clienteRecordOne.codEstado());
        String codCidade = criptografar.criptografar(secretKey, clienteRecordOne.codCidade());
        String codCliente = criptografar.criptografar(secretKey, clienteRecordOne.codCliente());

        return new ClienteRecordOne(id, nome, cpfOrCnpj, email, telefone, fullAdress, codCountry, codEstado, codCidade, codCliente);
    }

    // Cript → Decript (RESPONSE)
    private ClienteRecordOne decriptClienteRecordOne(ClienteRecordOne clienteRecordOne) {
        String id = (clienteRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, clienteRecordOne.id()) : null;
        String nome = descriptografar.descriptografar(secretKey, clienteRecordOne.nome());
        String cpfOrCnpj = descriptografar.descriptografar(secretKey, clienteRecordOne.cpfOrCnpj());
        String email = descriptografar.descriptografar(secretKey, clienteRecordOne.email());
        String telefone = descriptografar.descriptografar(secretKey, clienteRecordOne.telefone());
        String fullAdress = descriptografar.descriptografar(secretKey, clienteRecordOne.fullAdress());
        String codCountry = descriptografar.descriptografar(secretKey, clienteRecordOne.codCountry());
        String codEstado = descriptografar.descriptografar(secretKey, clienteRecordOne.codEstado());
        String codCidade = descriptografar.descriptografar(secretKey, clienteRecordOne.codCidade());
        String codCliente = descriptografar.descriptografar(secretKey, clienteRecordOne.codCliente());

        return new ClienteRecordOne(id, nome, cpfOrCnpj, email, telefone, fullAdress, codCountry, codEstado, codCidade, codCliente);
    }

    private ClienteRecordTwo decriptClienteRecordTwo(ClienteRecordTwo clienteRecordTwo) {
        String nome = descriptografar.descriptografar(secretKey, clienteRecordTwo.nome());
        String cpfOrCnpj = descriptografar.descriptografar(secretKey, clienteRecordTwo.cpfOrCnpj());
        String email = descriptografar.descriptografar(secretKey, clienteRecordTwo.email());
        String telefone = descriptografar.descriptografar(secretKey, clienteRecordTwo.telefone());
        String fullAdress = descriptografar.descriptografar(secretKey, clienteRecordTwo.fullAdress());
        String codCliente = descriptografar.descriptografar(secretKey, clienteRecordTwo.codCliente());

        return new ClienteRecordTwo(nome, cpfOrCnpj, email, telefone, fullAdress, codCliente);
    }

    private ClienteRecordThree decriptClienteRecordThree(ClienteRecordThree clienteRecordThree) {
        String nome = descriptografar.descriptografar(secretKey, clienteRecordThree.nome());
        String email = descriptografar.descriptografar(secretKey, clienteRecordThree.email());
        String telefone = descriptografar.descriptografar(secretKey, clienteRecordThree.telefone());
        String codCliente = descriptografar.descriptografar(secretKey, clienteRecordThree.codCliente());

        return new ClienteRecordThree(nome, email, telefone, codCliente);
    }
}

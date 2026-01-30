package sistemaloja.aplicativo.Factory;

import sistemaloja.aplicativo.Entity.Filial.FilialRecordOne;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordThree;
import sistemaloja.aplicativo.Entity.Filial.FilialRecordTwo;
import sistemaloja.aplicativo.Security.Cript.Criptografar;
import sistemaloja.aplicativo.Security.Decript.Descriptografar;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

public class FilialFactory {
    private final Criptografar criptografar = new Criptografar();
    private final Descriptografar descriptografar = new Descriptografar();
    private final SecretKey secretKey;

    public FilialFactory(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Object criptFilial(Object objeto) {
        if ( objeto instanceof List<?> lista) {
            List<FilialRecordOne> filialRecordOneList = new ArrayList<>();

            for (Object filial : lista) {
                filialRecordOneList.add(criptFilialRecord((FilialRecordOne) filial));
            }

            return filialRecordOneList;
        } else {
            return criptFilialRecord((FilialRecordOne) objeto);
        }
    }

    public <T> List<T> decriptFilialList(List<T> objeto) {
        List<T> filialList = new ArrayList<>();

        for (T filial : objeto) {
            filialList.add(decriptFilial(filial));
        }

        return filialList;
    }

    public <T> T decriptFilial(T objeto) {
        return (T) switch (objeto) {
            case FilialRecordOne filialRecordOne -> decriptFilialRecordOne(filialRecordOne);
            case FilialRecordTwo filialRecordTwo -> decriptFilialRecordTwo(filialRecordTwo);
            case FilialRecordThree filialRecordThree -> decriptFilialRecordThree(filialRecordThree);
            default -> null;
        };
    }

    // Decript → Cript (REQUEST)
    private FilialRecordOne criptFilialRecord(FilialRecordOne filialRecordOne) {
        String id = (filialRecordOne.id() != null) ? criptografar.criptografar(secretKey, filialRecordOne.id()) : null;
        String cnpj = criptografar.criptografar(secretKey, filialRecordOne.cnpj());
        String telefone = criptografar.criptografar(secretKey, filialRecordOne.telefone());
        String quantEmpregados = (filialRecordOne.quantEmpregados() != null) ? criptografar.criptografar(secretKey, filialRecordOne.quantEmpregados()) : null;
        String fullAdress = (filialRecordOne.fullAdress() != null) ? criptografar.criptografar(secretKey, filialRecordOne.fullAdress()) : null;
        String codCountry = criptografar.criptografar(secretKey, filialRecordOne.codCountry());
        String codEstado = criptografar.criptografar(secretKey, filialRecordOne.codEstado());
        String codCidade = criptografar.criptografar(secretKey, filialRecordOne.codCidade());
        String codFilial = (filialRecordOne.codFilial() != null) ? criptografar.criptografar(secretKey, filialRecordOne.codFilial()) : null;

        return new FilialRecordOne(id, cnpj, telefone, quantEmpregados, fullAdress, codCountry, codEstado, codCidade, codFilial);
    }

    // Cript → Decript (RESPONSE)
    private FilialRecordOne decriptFilialRecordOne(FilialRecordOne filialRecordOne) {
        String id = (filialRecordOne.id() != null) ? descriptografar.descriptografar(secretKey, filialRecordOne.id()) : null;
        String cnpj = descriptografar.descriptografar(secretKey, filialRecordOne.cnpj());
        String telefone = descriptografar.descriptografar(secretKey, filialRecordOne.telefone());
        String quantEmpregados = (filialRecordOne.quantEmpregados() != null) ? descriptografar.descriptografar(secretKey, filialRecordOne.quantEmpregados()) : null;
        String fullAdress = (filialRecordOne.fullAdress() != null) ? descriptografar.descriptografar(secretKey, filialRecordOne.fullAdress()) : null;
        String codCountry = descriptografar.descriptografar(secretKey, filialRecordOne.codCountry());
        String codEstado = descriptografar.descriptografar(secretKey, filialRecordOne.codEstado());
        String codCidade = descriptografar.descriptografar(secretKey, filialRecordOne.codCidade());
        String codFilial = (filialRecordOne.codFilial() != null) ? descriptografar.descriptografar(secretKey, filialRecordOne.codFilial()) : null;

        return new FilialRecordOne(id, cnpj, telefone, quantEmpregados, fullAdress, codCountry, codEstado, codCidade, codFilial);
    }

    private FilialRecordTwo decriptFilialRecordTwo(FilialRecordTwo filialRecordTwo) {
        String cnpj = descriptografar.descriptografar(secretKey, filialRecordTwo.cnpj());
        String telefone = descriptografar.descriptografar(secretKey, filialRecordTwo.telefone());
        String quantEmpregados = (filialRecordTwo.quantEmpregados() != null) ? descriptografar.descriptografar(secretKey, filialRecordTwo.quantEmpregados()) : null;
        String fullAdress = (filialRecordTwo.fullAdress() != null) ? descriptografar.descriptografar(secretKey, filialRecordTwo.fullAdress()) : null;
        String codFilial = (filialRecordTwo.codFilial() != null) ? descriptografar.descriptografar(secretKey, filialRecordTwo.codFilial()) : null;

        return new FilialRecordTwo(cnpj, telefone, quantEmpregados, fullAdress, codFilial);
    }

    private FilialRecordThree decriptFilialRecordThree(FilialRecordThree filialRecordThree) {
        String telefone = descriptografar.descriptografar(secretKey, filialRecordThree.telefone());
        String quantEmpregados = (filialRecordThree.quantEmpregados() != null) ? descriptografar.descriptografar(secretKey, filialRecordThree.quantEmpregados()) : null;
        String codFilial = (filialRecordThree.codFilial() != null) ? descriptografar.descriptografar(secretKey, filialRecordThree.codFilial()) : null;

        return new FilialRecordThree(telefone, quantEmpregados, codFilial);
    }
}

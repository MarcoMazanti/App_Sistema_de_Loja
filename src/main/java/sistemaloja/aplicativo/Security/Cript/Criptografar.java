package sistemaloja.aplicativo.Security.Cript;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Criptografar {
    public String criptografar(SecretKey secretKeyLimpo, String texto) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeyLimpo);

            return Base64.getEncoder().encodeToString(cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criptografar texto.");
            return null;
        }
    }
}

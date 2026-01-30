package sistemaloja.aplicativo.Security.Decript;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Descriptografar {
    public String descriptografar(SecretKey secretKeyLimpo, String texto) {
        try {
            System.out.println(Base64.getEncoder().encodeToString(secretKeyLimpo.getEncoded()));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeyLimpo);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(texto));

            return Base64.getEncoder().encodeToString(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criptografar texto.");
            return null;
        }
    }
}

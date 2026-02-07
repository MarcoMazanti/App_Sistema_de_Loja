package sistemaloja.aplicativo.Security.Decript;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Descriptografar {
    public String descriptografar(SecretKey secretKeyLimpo, String textoCriptografado) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeyLimpo);

            byte[] bytesCriptografados = Base64.getDecoder().decode(textoCriptografado);
            byte[] decryptedBytes = cipher.doFinal(bytesCriptografados);

            String textoDescriptografado = new String(decryptedBytes, StandardCharsets.UTF_8);
            if (textoDescriptografado.equals("null")) return null;
            return textoDescriptografado;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao descriptografar texto.");
            return null;
        }
    }
}

package sistemaloja.aplicativo.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GerarSecretKey {
    private static String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsJVGKhVeWZbGmlQXh5OLu/M3+XCrQjQsWCb2MBBzNwdDJcyRxZJ+2Lk4czVch1X/+HN38rswExrCY1WOtKt61rp3CJeLdfRBLdB9kfcp8Bc5Vm6j3bUx+gmFjLtYjY9NaeoSTuBnuIuA06tpIWEMXj4w28/5Ln53/wTKbhceo9UGOtegXgzgO4wUaL7s+VBTZn2/l4JFTXe96PGueI7kNnRjkbtDQ/blgMQK3WgL/GcnIMQ/ZtArCEIHMIgd5uxljDift+diYpPc2paIBuiim8bPCq0LtjDsY28Q0MVaX0vCNGAWEGvf41306DqjdWTZVtfxb+vGBP0YReeYoPsZ8QIDAQAB";

    public static SecretKey gerarSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);

            return keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao gerar chave AES.");
            return null;
        }
    }

    public static String criptSecretKey(SecretKey secretKey) {
        try {
            PublicKey publicKey = getPublicKeyFromBase64(publicKeyString);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(secretKey.getEncoded());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criptografar chave AES.");
            return null;
        }
    }

    private static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}

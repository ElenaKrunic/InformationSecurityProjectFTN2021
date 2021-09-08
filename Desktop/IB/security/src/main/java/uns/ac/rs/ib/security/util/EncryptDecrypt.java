package uns.ac.rs.ib.security.util;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptDecrypt {
    public static void main(String[] args) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        File publicKeyFile = new File("C:\\Users\\Svetozar\\git\\InformationSecurityProject_FTN_2021\\Desktop\\IB\\security\\src\\main\\resources\\cert\\public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        File privateKeyFile = new File("C:\\Users\\Svetozar\\git\\InformationSecurityProject_FTN_2021\\Desktop\\IB\\security\\src\\main\\resources\\cert\\private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        String secretMessage = "Baeldung secret message";
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);


        byte[] encryptedMessageBytes2 = Base64.getDecoder().decode(encodedMessage);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes2);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        System.out.println(encodedMessage);
        System.out.println(decryptedMessage);
    }
}

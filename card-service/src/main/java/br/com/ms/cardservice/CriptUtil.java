package br.com.ms.cardservice;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CriptUtil {
    private static final String ALGORITMO = "AES";

    public static SecretKey gerarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITMO);
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String criptografar(String senha, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] senhaCriptografada = cipher.doFinal(senha.getBytes());
        return Base64.getEncoder().encodeToString(senhaCriptografada);
    }

    public static String descriptografar(String senhaCriptografada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] senhaDecifrada = cipher.doFinal(Base64.getDecoder().decode(senhaCriptografada));
        return new String(senhaDecifrada);
    }
}

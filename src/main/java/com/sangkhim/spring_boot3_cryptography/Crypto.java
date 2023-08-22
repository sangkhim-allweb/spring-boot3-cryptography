package com.sangkhim.spring_boot3_cryptography;

import com.sangkhim.spring_boot3_cryptography.utils.cryptography.AesUtils;
import com.sangkhim.spring_boot3_cryptography.utils.cryptography.KeyUtils;
import com.sangkhim.spring_boot3_cryptography.utils.cryptography.RsaUtils;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;
import javax.crypto.SecretKey;

public class Crypto {
  public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKey key = KeyUtils.generateKey(128);
    String encryptText = AesUtils.encrypt("AES secret", key);
    System.out.println("encryptText:\n" + encryptText);
    String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
    System.out.println("secretKey:\n" + secretKey);
    String decryptText = AesUtils.decrypt(encryptText, KeyUtils.getSecretKey(secretKey));
    System.out.println("decryptText:\n" + decryptText);

    System.out.println("");

    Map<String, Object> keys = RsaUtils.createKeys();
    PublicKey publicKey = (PublicKey) keys.get("publicKey");
    String publicKeyAsString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    System.out.println("publicKey:\n" + publicKeyAsString);
    PrivateKey privateKey = (PrivateKey) keys.get("privateKey");
    String privateKeyAsString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    System.out.println("privateKey:\n" + privateKeyAsString);

    encryptText = RsaUtils.encryptMessage("RSA secret", publicKeyAsString);
    System.out.println("encryptText:\n" + encryptText);
    decryptText = RsaUtils.decryptMessage(encryptText, privateKeyAsString);
    System.out.println("decryptText:\n" + decryptText);
  }
}

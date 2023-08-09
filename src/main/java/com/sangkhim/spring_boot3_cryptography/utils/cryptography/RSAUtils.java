package com.sangkhim.spring_boot3_cryptography.utils.cryptography;

import static lombok.AccessLevel.PRIVATE;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class RSAUtils {

  public static Map<String, Object> createKeys() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(4096);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      PublicKey publicKey = keyPair.getPublic();
      PrivateKey privateKey = keyPair.getPrivate();

      Map<String, Object> map = new HashMap<>();
      map.put("publicKey", publicKey);
      map.put("privateKey", privateKey);
      return map;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }

  public static String encryptMessage(String plainText, PublicKey publicKey) {
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] encrypt = cipher.doFinal(plainText.getBytes());
      return Base64.getEncoder().encodeToString(encrypt);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String encryptMessage(String plainText, String publicKeyAsString)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    byte[] publicKeyAsByte = Base64.getDecoder().decode(publicKeyAsString);
    PublicKey publicKey =
        KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyAsByte));

    return encryptMessage(plainText, publicKey);
  }

  public static String decryptMessage(String encryptedMessage, PrivateKey privateKey) {
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
      return new String(decrypt);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String decryptMessage(String encryptedMessage, String privateKeyAsString)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    byte[] privateKeyAsByte = Base64.getDecoder().decode(privateKeyAsString);
    PrivateKey privateKey =
        KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyAsByte));

    return decryptMessage(encryptedMessage, privateKey);
  }
}

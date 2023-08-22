package com.sangkhim.spring_boot3_cryptography.utils.cryptography;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static lombok.AccessLevel.PRIVATE;

import com.sangkhim.spring_boot3_cryptography.utils.cryptography.exception.CryptographicTransformationException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class AesUtils {

  private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

  public static String encrypt(String plainText, SecretKey secretKey) {
    byte[] encryptedData = cryptData(ENCRYPT_MODE, plainText.getBytes(UTF_8), secretKey);
    return getEncoder().encodeToString(encryptedData);
  }

  public static String decrypt(String encryptedText, SecretKey secretKey) {
    byte[] encryptedData = getDecoder().decode(encryptedText);
    byte[] decrypted = cryptData(DECRYPT_MODE, encryptedData, secretKey);
    return new String(decrypted, UTF_8);
  }

  private static byte[] cryptData(int mode, byte[] data, SecretKey secretKey) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(mode, secretKey);
      return cipher.doFinal(data);
    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException exception) {
      log.error("Error while encrypting/decrypting", exception);
      throw new CryptographicTransformationException(
          "Error while encrypting/decrypting", exception);
    }
  }
}

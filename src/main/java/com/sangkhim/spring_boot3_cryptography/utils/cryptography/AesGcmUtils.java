package com.sangkhim.spring_boot3_cryptography.utils.cryptography;

import static java.lang.System.arraycopy;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static lombok.AccessLevel.PRIVATE;

import com.sangkhim.spring_boot3_cryptography.utils.cryptography.exception.CryptographicTransformationException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class AesGcmUtils {

  private static final int IV_SIZE = 16;
  private static final String TRANSFORMATION = "AES/GCM/NoPadding";
  private static final int AUTH_TAG_LENGTH = 128;

  public static String encrypt(String plaintext, SecretKey secretKey) {
    byte[] iv = generateIv();
    byte[] encryptedData =
        cryptData(
            ENCRYPT_MODE,
            plaintext.getBytes(UTF_8),
            secretKey,
            new GCMParameterSpec(AUTH_TAG_LENGTH, iv));
    return encodeEncryptedDataWithIv(iv, encryptedData);
  }

  public static String decrypt(String encryptedData, SecretKey secretKey) {
    byte[] encryptedWithIv = getDecoder().decode(encryptedData);
    byte[] iv = extractIv(encryptedWithIv);
    byte[] encrypted = extractEncryptedData(encryptedWithIv);

    byte[] decrypted =
        cryptData(DECRYPT_MODE, encrypted, secretKey, new GCMParameterSpec(AUTH_TAG_LENGTH, iv));

    return new String(decrypted, UTF_8);
  }

  private static byte[] generateIv() {
    byte[] iv = new byte[IV_SIZE];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(iv);
    return iv;
  }

  private static byte[] cryptData(
      int mode, byte[] data, SecretKey secretKey, GCMParameterSpec gcmParameterSpec) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(mode, secretKey, gcmParameterSpec);
      return cipher.doFinal(data);
    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | InvalidAlgorithmParameterException
        | IllegalBlockSizeException
        | BadPaddingException exception) {
      log.error("Error while encrypting/decrypting", exception);
      throw new CryptographicTransformationException(
          "Error while encrypting/decrypting", exception);
    }
  }

  private static String encodeEncryptedDataWithIv(byte[] iv, byte[] encryptedData) {
    byte[] encryptedWithIv = new byte[IV_SIZE + encryptedData.length];
    arraycopy(iv, 0, encryptedWithIv, 0, IV_SIZE);
    arraycopy(encryptedData, 0, encryptedWithIv, IV_SIZE, encryptedData.length);
    return getEncoder().encodeToString(encryptedWithIv);
  }

  private static byte[] extractIv(byte[] encryptedWithIv) {
    byte[] iv = new byte[IV_SIZE];
    arraycopy(encryptedWithIv, 0, iv, 0, IV_SIZE);
    return iv;
  }

  private static byte[] extractEncryptedData(byte[] encryptedWithIv) {
    byte[] encrypted = new byte[encryptedWithIv.length - IV_SIZE];
    arraycopy(encryptedWithIv, IV_SIZE, encrypted, 0, encrypted.length);
    return encrypted;
  }
}

package com.sangkhim.spring_boot3_cryptography.utils.cryptography;

import com.sangkhim.spring_boot3_cryptography.utils.cryptography.exception.KeyGeneratorException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class KeyUtils {

  public static SecretKey generateKey(int size) {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(size);
      return keyGenerator.generateKey();
    } catch (NoSuchAlgorithmException exception) {
      throw new KeyGeneratorException("Failed to generate key", exception);
    }
  }

  public static SecretKey getSecretKey(String secretKeyBase64) {
    return new SecretKeySpec(Base64.getDecoder().decode(secretKeyBase64), "AES");
  }
}

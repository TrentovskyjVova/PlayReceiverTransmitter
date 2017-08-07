package com.vova.receiver.infrastructure.crypto;

import com.vova.receiver.domain.crypto.ICryptoComponent;
import org.apache.commons.codec.binary.Hex;
import play.libs.F.Promise;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static play.libs.F.Promise.promise;

public class CryptoComponent implements ICryptoComponent {

    private static final String AES_MODE = "AES/CBC/PKCS5PADDING";
    private static final String CHARSET = "UTF-8";

    @Override
    public Promise<String> encrypt(String key, String initVector, String value) {
        return promise(() -> {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(CHARSET), "AES");

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Hex.encodeHexString(encrypted);
        });
    }

    @Override
    public Promise<String> decrypt(String key, String initVector, String encrypted) {
        return promise(() -> {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(CHARSET), "AES");

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Hex.decodeHex(encrypted.toCharArray()));

            return new String(original);
        });
    }
}

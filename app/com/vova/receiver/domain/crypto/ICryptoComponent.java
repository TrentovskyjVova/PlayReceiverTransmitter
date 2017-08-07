package com.vova.receiver.domain.crypto;

import play.libs.F.Promise;

public interface ICryptoComponent {

    Promise<String> encrypt(String key, String initVector, String value);

    Promise<String> decrypt(String key, String initVector, String encrypted);
}

package com.vova.receiver.infrastructure.config;

import com.vova.receiver.domain.config.IConfigComponent;
import play.Configuration;

import javax.inject.Singleton;

@Singleton
public class ConfigComponent implements IConfigComponent{

    private String key;
    private String iv;

    @Override
    public String getSecureKey() {
        if (key == null) {
            key = Configuration
                    .root()
                    .getString("secure.key");
        }
        return key;
    }

    @Override
    public String getSecureIV() {
        if (iv == null) {
            iv = Configuration
                    .root()
                    .getString("secure.iv");
        }
        return iv;
    }
}

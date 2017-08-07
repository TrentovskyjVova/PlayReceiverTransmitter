package com.vova.receiver.infrastructure.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.vova.receiver.domain.config.IConfigComponent;
import com.vova.receiver.domain.crypto.ICryptoComponent;
import com.vova.receiver.domain.entities.requests.Message;
import com.vova.receiver.domain.messages.IMessageComponent;
import play.libs.F.Promise;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageComponent implements IMessageComponent {

    private IConfigComponent configComponent;
    private ICryptoComponent cryptoComponent;

    @Inject
    public MessageComponent(
            IConfigComponent configComponent,
            ICryptoComponent cryptoComponent
    ) {
        this.configComponent = configComponent;
        this.cryptoComponent = cryptoComponent;
    }

    @Override
    public Promise<String> receiveMessage(String messageRequest) {

        Promise<String> decrypted = cryptoComponent.decrypt(
                configComponent.getSecureKey(),
                configComponent.getSecureIV(),
                messageRequest);

        return decrypted.flatMap(decryptedMessage -> {
            JsonNode json = Json.parse(decryptedMessage);
            Message message = Json.fromJson(json, Message.class);
            System.out.println(message.getMessage());

            return cryptoComponent.encrypt(
                    configComponent.getSecureKey(),
                    configComponent.getSecureIV(),
                    "received " + message.getId());

        });
    }

}

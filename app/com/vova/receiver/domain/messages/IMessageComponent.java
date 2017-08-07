package com.vova.receiver.domain.messages;

import play.libs.F.Promise;

public interface IMessageComponent {
    Promise<String> receiveMessage(String messageRequest);
}

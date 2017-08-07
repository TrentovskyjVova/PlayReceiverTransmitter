package controllers;

import com.vova.receiver.domain.messages.IMessageComponent;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class Receiver extends Controller {

    @Inject
    private IMessageComponent messageComponent;

    public Promise<Result> receive() {
        String data = request().body().asText();

        return messageComponent.receiveMessage(data).map(str -> (Result) ok(str))
                .recover(throwable -> {
                    System.out.println(throwable.getMessage());
                    return badRequest();
                });
    }

}

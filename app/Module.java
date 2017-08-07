import com.google.inject.AbstractModule;
import com.vova.receiver.domain.config.IConfigComponent;
import com.vova.receiver.domain.crypto.ICryptoComponent;
import com.vova.receiver.domain.messages.IMessageComponent;
import com.vova.receiver.infrastructure.config.ConfigComponent;
import com.vova.receiver.infrastructure.crypto.CryptoComponent;
import com.vova.receiver.infrastructure.messages.MessageComponent;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {

        bind(IMessageComponent.class).to(MessageComponent.class);

        bind(ICryptoComponent.class).to(CryptoComponent.class);

        bind(IConfigComponent.class).to(ConfigComponent.class);
    }

}

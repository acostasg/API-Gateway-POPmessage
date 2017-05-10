package api.binder;

import api.domain.infrastructure.MessageRepository;
import api.domain.infrastructure.TokenRepository;
import api.domain.infrastructure.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

public class POPMessageTestBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(api.infrastucture.inMemory.MessageRepository.class)
                .to(MessageRepository.class)
                .named(api.infrastucture.inMemory.MessageRepository.class.getCanonicalName())
                .in(RequestScoped.class);
        bind(api.infrastucture.inMemory.TokenRepository.class)
                .to(TokenRepository.class)
                .named(api.infrastucture.inMemory.TokenRepository.class.getCanonicalName())
                .in(RequestScoped.class);
        bind(api.infrastucture.inMemory.UserRepository.class)
                .to(UserRepository.class)
                .named(api.infrastucture.inMemory.UserRepository.class.getCanonicalName())
                .in(RequestScoped.class);
    }
}
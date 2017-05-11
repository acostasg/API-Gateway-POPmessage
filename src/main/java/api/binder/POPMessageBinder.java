package api.binder;

import api.domain.infrastructure.MessageRepository;
import api.domain.infrastructure.TokenRepository;
import api.domain.infrastructure.UserRepository;
import api.infrastucture.elasticSearch.ElasticSearchClient;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

public class POPMessageBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(api.infrastucture.elasticSearch.MessageRepository.class)
                .to(MessageRepository.class)
                .named(api.infrastucture.elasticSearch.MessageRepository.class.getCanonicalName())
                .in(RequestScoped.class);
        bind(api.infrastucture.elasticSearch.TokenRepository.class)
                .to(TokenRepository.class)
                .named(api.infrastucture.elasticSearch.TokenRepository.class.getCanonicalName())
                .in(RequestScoped.class);
        bind(api.infrastucture.elasticSearch.UserRepository.class)
                .to(UserRepository.class)
                .named(api.infrastucture.elasticSearch.UserRepository.class.getCanonicalName())
                .in(RequestScoped.class);
        bind(ElasticSearchClient.class)
                .to(ElasticSearchClient.class)
                .named(ElasticSearchClient.class.getCanonicalName())
                .in(RequestScoped.class);
    }
}
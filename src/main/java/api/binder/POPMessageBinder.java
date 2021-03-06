package api.binder;

import api.domain.infrastructure.ConfigRepository;
import api.domain.infrastructure.MessageRepository;
import api.domain.infrastructure.TokenRepository;
import api.domain.infrastructure.UserRepository;
import api.domain.service.ValidationAppService;
import api.infrastucture.cache.CacheTokenInterface;
import api.infrastucture.cache.ehCache.CacheTokenWrapper;
import api.infrastucture.elasticSearch.ElasticSearchClient;
import api.infrastucture.elasticSearch.ElasticSearchClientInterface;
import api.infrastucture.elasticSearch.queryDSL.EncodeWrapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Singleton;

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
                .to(ElasticSearchClientInterface.class)
                .named(ElasticSearchClient.class.getCanonicalName())
                .in(Singleton.class);
        bind(CacheTokenWrapper.class)
                .to(CacheTokenInterface.class)
                .named(CacheTokenWrapper.class.getCanonicalName())
                .in(Singleton.class);
        bind(api.infrastucture.xml.ConfigRepository.class)
                .to(ConfigRepository.class)
                .named(api.infrastucture.xml.ConfigRepository.class.getCanonicalName())
                .in(Singleton.class);
        bind(ValidationAppService.class)
                .to(ValidationAppService.class)
                .named(ValidationAppService.class.getCanonicalName())
                .in(Singleton.class);
        bind(EncodeWrapper.class)
                .to(EncodeWrapper.class)
                .named(EncodeWrapper.class.getCanonicalName())
                .in(Singleton.class);
    }
}
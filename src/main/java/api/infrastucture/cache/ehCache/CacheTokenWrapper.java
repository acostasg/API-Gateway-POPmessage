package api.infrastucture.cache.ehCache;


import api.domain.entity.Token;
import api.domain.entity.User;
import api.infrastucture.cache.CacheTokenInterface;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import javax.inject.Singleton;

public class CacheTokenWrapper implements CacheTokenInterface {

    private Cache<String, User> tokenCache;

    @Singleton
    public CacheTokenWrapper() {
        if (null == tokenCache) {
            synchronized (this) {
                CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
                CacheConfiguration<String, User> cacheConfig =
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                User.class,
                                ResourcePoolsBuilder.heap(10).offheap(10, MemoryUnit.MB)
                        ).withValueSerializer(TokenSerializer.class)
                                .build();

                this.tokenCache = cacheManager.createCache("tokenCache", cacheConfig);
            }
        }
    }

    @Override
    public void setUser(User user, Token token) {
        this.tokenCache.put(token.hash(), user);
    }

    @Override
    public User getUser(Token token) {
        return tokenCache.get(token.hash());
    }

    @Override
    public boolean hasToken(Token token) {
        return tokenCache.containsKey(token.hash());
    }

    @Override
    public void deleteUser(Token token) {
        this.tokenCache.remove(token.hash());
    }
}

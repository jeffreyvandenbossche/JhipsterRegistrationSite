package be.jeffreyvdb.weddingsite.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(be.jeffreyvdb.weddingsite.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Accesscode.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Family.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.PartyPart.class.getName(), jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.PartyPart.class.getName() + ".people", jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Person.class.getName() + ".partyParts", jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Person.class.getName() + ".people", jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.Accesscode.class.getName() + ".partyParts", jcacheConfiguration);
            cm.createCache(be.jeffreyvdb.weddingsite.domain.PartyPart.class.getName() + ".accesscodes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}

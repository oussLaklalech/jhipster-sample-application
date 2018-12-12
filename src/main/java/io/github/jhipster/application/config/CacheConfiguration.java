package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

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
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Consumer.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Consumer.class.getName() + ".orders", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Consumer.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Cooker.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Kitchen.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Kitchen.class.getName() + ".dishes", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.OrderPreparation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.OrderPreparation.class.getName() + ".states", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.State.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dish.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dish.class.getName() + ".orders", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dish.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Comment.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}

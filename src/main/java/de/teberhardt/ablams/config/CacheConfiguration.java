package de.teberhardt.ablams.config;

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
            cm.createCache(de.teberhardt.ablams.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Author.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Author.class.getName() + ".audioBooks", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Author.class.getName() + ".audioSeries", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioSeries.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioSeries.class.getName() + ".audioBooks", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioLibrary.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioLibrary.class.getName() + ".audioFiles", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioFile.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioBook.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.AudioBook.class.getName() + ".audioFiles", jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Progressable.class.getName(), jcacheConfiguration);
            cm.createCache(de.teberhardt.ablams.domain.Progressable.class.getName() + ".audioFiles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}

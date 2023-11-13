package com.peecko.admin.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.peecko.admin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.peecko.admin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.peecko.admin.domain.User.class.getName());
            createCache(cm, com.peecko.admin.domain.Authority.class.getName());
            createCache(cm, com.peecko.admin.domain.User.class.getName() + ".authorities");
            createCache(cm, com.peecko.admin.domain.ApsAccount.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsAccount.class.getName() + ".apsDevices");
            createCache(cm, com.peecko.admin.domain.ApsAccount.class.getName() + ".playlists");
            createCache(cm, com.peecko.admin.domain.SecuredRequest.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsDevice.class.getName());
            createCache(cm, com.peecko.admin.domain.Agency.class.getName());
            createCache(cm, com.peecko.admin.domain.Agency.class.getName() + ".staff");
            createCache(cm, com.peecko.admin.domain.Agency.class.getName() + ".customers");
            createCache(cm, com.peecko.admin.domain.Agency.class.getName() + ".apsPricings");
            createCache(cm, com.peecko.admin.domain.Staff.class.getName());
            createCache(cm, com.peecko.admin.domain.Customer.class.getName());
            createCache(cm, com.peecko.admin.domain.Customer.class.getName() + ".contacts");
            createCache(cm, com.peecko.admin.domain.Customer.class.getName() + ".apsPlans");
            createCache(cm, com.peecko.admin.domain.Contact.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsPlan.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsPlan.class.getName() + ".apsOrders");
            createCache(cm, com.peecko.admin.domain.ApsOrder.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsOrder.class.getName() + ".apsMemberships");
            createCache(cm, com.peecko.admin.domain.ApsOrder.class.getName() + ".invoices");
            createCache(cm, com.peecko.admin.domain.ApsMembership.class.getName());
            createCache(cm, com.peecko.admin.domain.ApsPricing.class.getName());
            createCache(cm, com.peecko.admin.domain.Video.class.getName());
            createCache(cm, com.peecko.admin.domain.VideoCategory.class.getName());
            createCache(cm, com.peecko.admin.domain.VideoCategory.class.getName() + ".videos");
            createCache(cm, com.peecko.admin.domain.Notification.class.getName());
            createCache(cm, com.peecko.admin.domain.CodeTranslation.class.getName());
            createCache(cm, com.peecko.admin.domain.Playlist.class.getName());
            createCache(cm, com.peecko.admin.domain.Playlist.class.getName() + ".videoItems");
            createCache(cm, com.peecko.admin.domain.VideoItem.class.getName());
            createCache(cm, com.peecko.admin.domain.Coach.class.getName());
            createCache(cm, com.peecko.admin.domain.Coach.class.getName() + ".videos");
            createCache(cm, com.peecko.admin.domain.Invoice.class.getName());
            createCache(cm, com.peecko.admin.domain.Invoice.class.getName() + ".invoiceItems");
            createCache(cm, com.peecko.admin.domain.InvoiceItem.class.getName());
            createCache(cm, com.peecko.admin.domain.VideoStat.class.getName());
            createCache(cm, com.peecko.admin.domain.LikedVideo.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

package infinispan;

import dao.DemoDaoImpl;
import model.Demo;
import org.infinispan.commons.api.BasicCacheContainer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.infinispan.util.concurrent.IsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Implementation of {@link CacheContainerProvider} creating a programmatically configured DefaultCacheManager.
 *
 * @author Marian Camak on 24. 4. 2016.
 */
@ApplicationScoped
public class CacheContainerProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private BasicCacheContainer manager;

    @Inject
    private CacheOperationLogger cacheLogger;

    public BasicCacheContainer getCacheContainer() {
        if (manager == null) {
            GlobalConfiguration glob = GlobalConfigurationBuilder.defaultClusteredBuilder()
                    .transport().defaultTransport()
                    .clusterName("LabelWeb")
                    .globalJmxStatistics().enable()
                    .jmxDomain("org.infinispan.demo")
//                    .addProperty("configurationFile", "jgroups-tcp.xml")
                    .build();

            Configuration defaultConfig = new ConfigurationBuilder()
                    .transaction().transactionMode(TransactionMode.TRANSACTIONAL)
                    .build();

            @SuppressWarnings("deprecation")
            Configuration demoCacheConfig = new ConfigurationBuilder()
                    .jmxStatistics().enable()
                    .clustering()
                        .cacheMode(CacheMode.DIST_SYNC)
                        .sync()
                    .transaction()
                        .transactionMode(TransactionMode.TRANSACTIONAL)
                        .autoCommit(false)
                        .lockingMode(LockingMode.OPTIMISTIC)
                        .transactionManagerLookup(new GenericTransactionManagerLookup())
                        .locking().isolationLevel(IsolationLevel.REPEATABLE_READ)
                        .supportsConcurrentUpdates(true)
                    .eviction()
                        .maxEntries(10)
                        .strategy(EvictionStrategy.LRU)
                    .persistence()
                        .passivation(true)
                        .addSingleFileStore()
                        .purgeOnStartup(true)
                    .indexing()
                        .enable()
                        .addIndexedEntity(Demo.class)
                        .addProperty("default.directory_provider", "ram")
                    .build();

            manager = new DefaultCacheManager(glob, defaultConfig);
            ((DefaultCacheManager) manager).defineConfiguration(DemoDaoImpl.DEMO_CACHE_NAME, demoCacheConfig);
            manager.start();

            cacheLogger.init();
            log.info("Cache container configured. ");
        }
        return manager;
    }

    @PreDestroy
    public void cleanUp() {
        manager.stop();
        manager = null;
    }
}

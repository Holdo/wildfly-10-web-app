package infinispan;

import org.infinispan.commons.api.BasicCacheContainer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.infinispan.util.concurrent.IsolationLevel;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

/**
 * Implementation of {@link CacheContainerProvider} creating a programmatically configured DefaultCacheManager.
 *
 * @author Marian Camak on 24. 4. 2016.
 */
@ApplicationScoped
public class CacheContainerProvider {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private BasicCacheContainer manager;

    public BasicCacheContainer getCacheContainer() {
        if (manager == null) {
            GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
            global.transport().clusterName("LabelWeb");

            Configuration config = new ConfigurationBuilder()
                    .transaction().transactionMode(TransactionMode.TRANSACTIONAL)
                    .clustering().cacheMode(CacheMode.REPL_SYNC)
                    .build();

            DefaultCacheManager defaultCacheManager = new DefaultCacheManager(global.build(), config);
            defaultCacheManager.addListener(new CacheOperationLogger());
            manager = defaultCacheManager;

            Configuration demoCacheConfig = new ConfigurationBuilder().jmxStatistics().enable()
                    .clustering().cacheMode(CacheMode.LOCAL)
                    .transaction().transactionMode(TransactionMode.TRANSACTIONAL).autoCommit(true)
                    .lockingMode(LockingMode.OPTIMISTIC).transactionManagerLookup(new GenericTransactionManagerLookup())
                    .locking().isolationLevel(IsolationLevel.REPEATABLE_READ)
                    .eviction().maxEntries(10).strategy(EvictionStrategy.LRU)
                    .persistence().passivation(true).addSingleFileStore().purgeOnStartup(true)
                    .indexing().enable().addProperty("default.directory_provider", "ram")
                    .build();

            ((DefaultCacheManager) manager).defineConfiguration("demoCache", demoCacheConfig);

            manager.start();
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

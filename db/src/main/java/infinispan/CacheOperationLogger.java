package infinispan;

import dao.DemoDaoImpl;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryVisited;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryVisitedEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for logging cache operations.
 *
 * @author Marian Camak on 24. 4. 2016.
 */
@Named
@ApplicationScoped
@Listener
public class CacheOperationLogger {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Inject
    private CacheContainerProvider provider;

    @PostConstruct
    public void getStatsObject() {
        ((DefaultCacheManager) provider.getCacheContainer()).getCache(DemoDaoImpl.DEMO_CACHE_NAME).addListener(this);
    }

    @CacheEntryCreated
    public void logCacheEntry(CacheEntryCreatedEvent e) {
        log.log(Level.INFO, "Entry " + e.getValue() + " created. ");
    }

    @CacheEntryModified
    public void logCacheEntry(CacheEntryModifiedEvent e) {
        log.log(Level.INFO, "Entry " + e.getValue() + " modified. ");
    }

    @CacheEntryRemoved
    public void logCacheEntry(CacheEntryRemovedEvent e) {
        log.log(Level.INFO, "Entry " + e.getValue() + " removed. ");
    }

    @CacheEntryVisited
    public void logCacheEntry(CacheEntryVisitedEvent e) {
        log.log(Level.INFO, "Entry " + e.getValue() + " visited. ");
    }
}

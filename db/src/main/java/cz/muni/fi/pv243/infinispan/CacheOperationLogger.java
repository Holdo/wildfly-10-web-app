package cz.muni.fi.pv243.infinispan;

import cz.muni.fi.pv243.dao.DemoDaoImpl;
import lombok.extern.slf4j.Slf4j;
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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class responsible for logging cache operations.
 *
 * @author Marian Camak on 24. 4. 2016.
 */
@Named
@Slf4j
@ApplicationScoped
@Listener
public class CacheOperationLogger {

	@Inject
	private CacheContainerProvider provider;

	public void init() {
		((DefaultCacheManager) provider.getCacheContainer()).getCache(DemoDaoImpl.DEMO_CACHE_NAME).addListener(this);
	}

	@CacheEntryCreated
	public void logCacheEntry(CacheEntryCreatedEvent e) {
		log.info("Entry " + e.getValue() + " created. ");
	}

	@CacheEntryModified
	public void logCacheEntry(CacheEntryModifiedEvent e) {
		log.info("Entry " + e.getValue() + " modified. ");
	}

	@CacheEntryRemoved
	public void logCacheEntry(CacheEntryRemovedEvent e) {
		log.info("Entry " + e.getValue() + " removed. ");
	}

	@CacheEntryVisited
	public void logCacheEntry(CacheEntryVisitedEvent e) {
		log.info("Entry " + e.getValue() + " visited. ");
	}
}

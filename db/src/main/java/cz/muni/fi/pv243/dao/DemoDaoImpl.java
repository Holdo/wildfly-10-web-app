package cz.muni.fi.pv243.dao;

import cz.muni.fi.pv243.exceptions.DemoNotExistsException;
import cz.muni.fi.pv243.exceptions.TitleAlreadyExistsException;
import cz.muni.fi.pv243.infinispan.CacheContainerProvider;
import cz.muni.fi.pv243.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCache;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * Implementation of interface <link>DemoDAO</link>
 *
 * @author Marian Camak on 4. 5. 2016.
 * @see DemoDAO
 */
@ApplicationScoped
@Slf4j
public class DemoDaoImpl implements DemoDAO {

    public static final String DEMO_CACHE_NAME = "demoCache";

    @Inject
    private CacheContainerProvider provider;

    private BasicCache<String, Object> demoCache;

    @PostConstruct
    public void init() {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void createDemo(Demo demo) {
        if (demoCache.containsKey(encode(demo.getTitle()))) {
            throw new TitleAlreadyExistsException(demo.getTitle());
        }
        if (demo.getTrack() == null) log.warn("Creating Demo with null track!");
        demoCache.put(encode(demo.getTitle()), demo);
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void updateDemo(Demo demo) {
        if (!demoCache.containsKey(encode(demo.getTitle()))) {
            throw new DemoNotExistsException(demo.getTitle());
        }
        demoCache.put(encode(demo.getTitle()), demo);
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void deleteDemo(Demo demo) {
        if (!demoCache.containsKey(encode(demo.getTitle()))) {
            throw new DemoNotExistsException(demo.getTitle());
        }
        demoCache.remove(encode(demo.getTitle()));
    }

    @Override
    public Demo findDemo(String title) {
        if (!demoCache.containsKey(encode(title))) {
            throw new DemoNotExistsException(title);
        }
        Object d = demoCache.get(encode(title));
        if (!(d instanceof Demo)) {
            throw new DemoNotExistsException(title);
        }

        Demo demo = (Demo) d;
        if (demo.getTrack() == null) {
            log.error("Track of " + demo.getTitle() + " is null!");
        }
        return demo;
    }

    @Override
    public List<Demo> findDemos(String interpret) {
        QueryFactory qf = Search.getQueryFactory((Cache) demoCache);

        Query q = qf.from(Demo.class)
                .having("artist").like("%" + (interpret.isEmpty() ? "donotmatch" : interpret) + "%")
                .toBuilder().build();
        log.info("Infinispan Query DSL: " + q.toString());

        List<Demo> demos = new LinkedList<>();
        for (Object o : q.list()) {
            if (o instanceof Demo) {
                demos.add((Demo) o);
            }
        }
        return demos;
    }

    @Override
    public List<Demo> findAll() {
        List<Demo> demos = new LinkedList<>();
        for (Object o : demoCache.values()) {
            if (o instanceof Demo) {
                demos.add((Demo) o);
            }
        }
        return demos;
    }

    @Override
    public List<Demo> findAllNoMp3() {
        List<Demo> demos = new LinkedList<>();
        for (Object o : demoCache.values()) {
            if (o instanceof Demo) {
                Demo demo = (Demo) o;
                demo.setTrack(null);
                demos.add(demo);
            }
        }
        return demos;
    }

    public static String encode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

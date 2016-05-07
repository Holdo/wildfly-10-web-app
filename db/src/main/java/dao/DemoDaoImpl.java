package dao;

import infinispan.CacheContainerProvider;
import model.Demo;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCache;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * Implementation of interface <link>DemoDAO</link>
 *
 * @author Marian Camak on 4. 5. 2016.
 * @see DemoDAO
 */
@Model
@Transactional(REQUIRES_NEW)
public class DemoDaoImpl implements DemoDAO {

    public static final String DEMO_CACHE_NAME = "demoCache";

    @Inject
    private CacheContainerProvider provider;

    private BasicCache<String, Object> demoCache;

    @Override
    public void createDemo(Demo demo) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        demoCache.put(encode(demo.getTitle()), demo);
    }

    @Override
    public void updateDemo(Demo demo) {
        createDemo(demo);
    }

    @Override
    public void deleteDemo(Demo demo) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        demoCache.remove(encode(demo.getTitle()));
    }

    @Override
    public Demo findDemo(String title) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        return (Demo) demoCache.get(encode(title));
    }

    @Override
    public List<Demo> findDemos(String interpret) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        QueryFactory qf = Search.getQueryFactory((Cache) demoCache);

        Query q = qf.from(Demo.class)
                .having("artist").like("%" + (interpret.isEmpty() ? "donotmatch" : interpret) + "%")
                .toBuilder().build();
        System.out.println("Infinispan Query DSL: " + q.toString());

        return q.list().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).collect(Collectors.toList());
    }

    @Override
    public List<Demo> findAll() {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        Collection<Demo> demos = demoCache.values().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).collect(Collectors.toList());
        return new LinkedList<>(demos);
    }

    public static String encode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

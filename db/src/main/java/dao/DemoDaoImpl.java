package dao;

import infinispan.CacheContainerProvider;
import model.Demo;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCache;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of interface <link>DemoDAO</link>
 * @see DemoDAO
 *
 * @author Marian Camak on 4. 5. 2016.
 */
public class DemoDaoImpl implements DemoDAO {

    public static final String DEMO_CACHE_NAME = "demoCache";

    @Inject
    private CacheContainerProvider provider;

    @Inject
    private UserTransaction utx;

    private BasicCache<String, Demo> demoCache;

    @Override
    public void createDemo(Demo demo) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);

        try {
            utx.begin();

            demoCache.put(encode(demo.getTitle()), demo);

            utx.commit();
        } catch (Exception e) {
            if (utx != null) {
                try {
                    utx.rollback();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void updateDemo(Demo demo) {
        createDemo(demo);
    }

    @Override
    public void deleteDemo(Demo demo) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);

        try {
            utx.begin();

            demoCache.remove(encode(demo.getTitle()));

            utx.commit();
        } catch (Exception e) {
            if (utx != null) {
                try {
                    utx.rollback();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public Demo findDemo(String title) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        return demoCache.get(encode(title));
    }

    @Override
    public List<Demo> findDemos(String interpret) {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        QueryFactory qf = Search.getQueryFactory((Cache) demoCache);

        Query q = qf.from(Demo.class)
                .having("interpret").like("%" + (interpret.isEmpty() ? "donotmatch" : interpret) + "%")
                .toBuilder().build();
        System.out.println("Infinispan Query DSL: " + q.toString() );

        // invoke the cache query
        return q.list().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).collect(Collectors.toList());
    }

    @Override
    public List<Demo> findAll() {
        demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
        List<Demo> demoList = (List<Demo>) demoCache.values();

        return new LinkedList<>(demoList);
    }

    public static String encode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

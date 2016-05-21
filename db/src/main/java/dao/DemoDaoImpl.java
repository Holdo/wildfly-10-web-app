package dao;

import infinispan.CacheContainerProvider;
import model.Demo;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCache;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
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
import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 * Implementation of interface <link>DemoDAO</link>
 *
 * @author Marian Camak on 4. 5. 2016.
 * @see DemoDAO
 */
@Model
@Transactional(REQUIRES_NEW)
public class DemoDaoImpl implements DemoDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	public static final String DEMO_CACHE_NAME = "demoCache";

	@Inject
	private CacheContainerProvider provider;

	private BasicCache<String, Object> demoCache;

	@PostConstruct
	public void init() {
		demoCache = provider.getCacheContainer().getCache(DEMO_CACHE_NAME);
	}

	@Override
	public void createDemo(Demo demo) {
		demoCache.put(encode(demo.getTitle()), demo);
	}

	@Override
	public void updateDemo(Demo demo) {
		createDemo(demo);
	}

	@Override
	public void deleteDemo(Demo demo) {
		demoCache.remove(encode(demo.getTitle()));
	}

	@Override
	public Demo findDemo(String title) {
		return (Demo) demoCache.get(encode(title));
	}

	@Override
	public List<Demo> findDemos(String interpret) {
		QueryFactory qf = Search.getQueryFactory((Cache) demoCache);

		Query q = qf.from(Demo.class)
				.having("artist").like("%" + (interpret.isEmpty() ? "donotmatch" : interpret) + "%")
				.toBuilder().build();
		log.info("Infinispan Query DSL: " + q.toString());

		return q.list().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).collect(Collectors.toList());
	}

	@Override
	public List<Demo> findAll() {
		Collection<Demo> demos = demoCache.values().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).collect(Collectors.toList());
		return new LinkedList<>(demos);
	}

	@Override
	public List<Demo> findAllNoMp3() {
		Collection<Demo> demos = demoCache.values().stream().filter(o -> o instanceof Demo).map(o -> (Demo) o).peek(o -> o.setTrack(null)).collect(Collectors.toList());
		return new LinkedList<>(demos);
	}

	@Transactional(SUPPORTS)
	public static String encode(String key) {
		try {
			return URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

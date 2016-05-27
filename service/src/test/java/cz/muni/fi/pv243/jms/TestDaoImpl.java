package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.dao.DemoDAO;
import lombok.Getter;
import cz.muni.fi.pv243.model.Demo;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * MOCK emulation
 *
 * @author 422718
 */
@ApplicationScoped
@Slf4j
public class TestDaoImpl implements DemoDAO {

	@Getter
	private Demo lastItem;

	@Override
	public void createDemo(Demo demo) {
		log.error("You are using test BEAN");
		this.lastItem = demo;
	}

	@Override
	public void updateDemo(Demo demo) {

	}

	@Override
	public void deleteDemo(Demo demo) {

	}

	@Override
	public Demo findDemo(String title) {
		log.error("You are using test BEAN which returns null");
		return null;
	}

	@Override
	public List<Demo> findDemos(String interpret) {
		return null;
	}

	@Override
	public List<Demo> findAll() {
		return null;
	}

	@Override
	public List<Demo> findAllNoMp3() {
		return null;
	}
}

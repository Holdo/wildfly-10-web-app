package cz.muni.fi.pv243.jms;

import dao.DemoDAO;
import lombok.Getter;
import model.Demo;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * MOCK emulation
 *
 * @author 422718
 */
@ApplicationScoped
public class TestDaoImpl implements DemoDAO {

	@Getter
	private Demo lastItem;

	@Override
	public void createDemo(Demo demo) {
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

package cz.muni.fi.pv243.jms.service;

import cz.muni.fi.pv243.model.Demo;

import java.util.List;

/**
 * Created by Michal Holic on 22.05.2016
 */
public interface DemoService {

	void createDemo(Demo demo);
	void updateDemo(Demo demo);
	void deleteDemo(Demo demo);

	Demo findDemo(String title);
	List<Demo> findDemos(String interpret);
	List<Demo> findAll();
	List<Demo> findAllNoMp3();

	/**
	 * Stores mp3 track in the webcontext and returns relative path (URL)
	 *
	 * @param title unique track title
	 * @return relative path (URL)
	 */
	String getDemoLink(String title);
}

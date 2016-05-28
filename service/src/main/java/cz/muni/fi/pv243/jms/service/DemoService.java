package cz.muni.fi.pv243.jms.service;

import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;

import java.io.IOException;
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

	/**
	 * Finds all Demos in the data store but returns DemoDTO's (without mp3 and comments)
	 *
	 * @return List of DemoDTO's
	 */
	List<DemoDTO> findAll();

	/**
	 * Stores mp3 track in the webcontext and returns relative path (URL)
	 *
	 * @param title unique track title
	 * @return relative path (URL)
	 */
	Mp3Link getDemoLink(String title) throws IOException;
}

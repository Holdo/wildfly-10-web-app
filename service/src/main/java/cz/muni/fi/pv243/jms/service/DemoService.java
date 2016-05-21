package cz.muni.fi.pv243.jms.service;

/**
 * Created by Michal Holic on 22.05.2016
 */
public interface DemoService {

	/**
	 * Stores mp3 track in the webcontext and returns relative path (URL)
	 *
	 * @param title unique track title
	 * @return relative path (URL)
	 */
	String getDemoLink(String title);
}

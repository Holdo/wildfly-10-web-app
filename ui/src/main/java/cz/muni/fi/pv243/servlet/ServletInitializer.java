package cz.muni.fi.pv243.servlet;

import cz.muni.fi.pv243.exceptions.DemoNotExistsException;
import cz.muni.fi.pv243.jms.ServiceBean;
import cz.muni.fi.pv243.jms.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

/**
 * Created by Michal Holic on 21.05.2016
 */
@WebListener
@Slf4j
public class ServletInitializer implements ServletContextListener {

	private String mp3WARLocation = "/WEB-INF/classes/SAMPLE.mp3";

	@Inject
	private ServiceBean bootstrap;

	@Inject
	private DemoService demoService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			demoService.findDemo(bootstrap.getTitle());
		} catch (DemoNotExistsException e) {
			final ServletContext context = sce.getServletContext();
			try {
				log.info("Bootstrapping mp3 from " + mp3WARLocation);
				bootstrap.setMp3(IOUtils.toByteArray(context.getResource(mp3WARLocation)));
			} catch (IOException ioe) {
				log.warn("Error bootstrapping sample mp3 from WAR location: " + mp3WARLocation + ": " + ioe.getMessage());
			}
			this.bootstrap.run();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}

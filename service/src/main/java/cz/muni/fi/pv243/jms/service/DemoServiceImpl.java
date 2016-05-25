package cz.muni.fi.pv243.jms.service;

import cz.muni.fi.pv243.dao.DemoDAO;
import cz.muni.fi.pv243.model.Demo;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Michal Holic on 22.05.2016
 */

@ApplicationScoped
@Slf4j
public class DemoServiceImpl implements DemoService {

	@Inject
	private DemoDAO demoDAO;

	public void createDemo(Demo demo){
		demoDAO.createDemo(demo);
	}

	public void updateDemo(Demo demo){
		demoDAO.updateDemo(demo);
	}

	public void deleteDemo(Demo demo){
		demoDAO.deleteDemo(demo);
	}

	public Demo findDemo(String title){
		return demoDAO.findDemo(title);
	}

	public List<Demo> findDemos(String interpret){
		return demoDAO.findDemos(interpret);
	}

	public List<Demo> findAll(){
		return demoDAO.findAll();
	}

	public List<Demo> findAllNoMp3(){
		return demoDAO.findAllNoMp3();
	}

	@Override
	public String getDemoLink(String title) {
		String fileName = title + ".mp3";
		try (FileOutputStream fos = new FileOutputStream(System.getProperty("jboss.home.dir") + File.pathSeparator + "mp3-resources" + File.pathSeparator + fileName)) {
			fos.write(demoDAO.findDemo(title).getTrack());
			fos.flush();
		} catch (IOException e) {
			log.error("Error writing mp3 " + title + " :" + e.getMessage());
		}
		return "/mp3/" + fileName;
	}
}

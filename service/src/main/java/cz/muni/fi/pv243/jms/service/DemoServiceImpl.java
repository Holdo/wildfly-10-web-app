package cz.muni.fi.pv243.jms.service;

import cz.muni.fi.pv243.dao.DemoDAO;
import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<DemoDTO> findAll(){
		List<Demo> demos = demoDAO.findAll();
		return demos.stream().map(DemoDTO::new).collect(Collectors.toList());
	}

	@Override
	public Mp3Link getDemoLink(String title) throws IOException {
		String fileName = title + ".mp3";
		String path = System.getProperty("jboss.home.dir") + File.separator + "mp3-resources" + File.separator;
		File mp3Dir = new File(path);
		if (mp3Dir.exists()) {
			if (!mp3Dir.isDirectory()) {
				log.error("This path should be a directory: " + path);
				throw new IOException("Path should be a directory: " + path);
			}
		} else {
			if (!mp3Dir.mkdir()) {
				log.error("Unable to create directory: " + path);
				throw new IOException("Unable to create directory: " + path);
			}
		}
		try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
			Demo foundDemo = findDemo(title);
			log.info("FoundDemo: " + foundDemo);
			if (foundDemo.getTrack() == null) {
				log.error("Track " + title + " is null");
				throw new IOException("Received track " + title + " is null");
			}
			log.info("Writing " + foundDemo.getTrack().length + " bytes to: " + path + fileName);
			fos.write(foundDemo.getTrack());
			fos.flush();
		} catch (IOException e) {
			log.error("Error writing mp3 " + title + ": " + e.getMessage());
		}
		return new Mp3Link("/mp3/" + fileName);
	}
}

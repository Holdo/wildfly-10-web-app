package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.dao.DemoDAO;
import cz.muni.fi.pv243.jms.service.DemoService;
import cz.muni.fi.pv243.model.Comment;
import cz.muni.fi.pv243.model.Demo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv243.model.Demo.Status.UPLOADED;

@Slf4j
@Getter
@Setter
@Singleton
@Startup
public class ServiceBean {

	private byte[] mp3 = null;
	private boolean mp3LoadingStarted = false;

	@Inject
	private DemoDAO demoDAO;

	@Inject
	private DemoService demoService;

	private static Demo testDemo = new Demo();

	@Transactional(Transactional.TxType.REQUIRED)
	public synchronized void run() {
		if (mp3LoadingStarted) return;
		mp3LoadingStarted = true;
		testDemo.setEmail("redy1337@gmail.com");
		testDemo.setArtist("Redy");
		testDemo.setStatus(UPLOADED);
		testDemo.setTitle("START");
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment("Jožo Ráž", "Môže byť."));
		comments.add(new Comment("Palo Habera", "Nepáči sa mi to."));
		testDemo.setComments(comments);
		log.info("Bootstrapped MP3 size: " + this.getMp3().length);
		testDemo.setTrack(this.getMp3());
		this.setMp3(null); //free memory

		log.info("Should be empty: {}", demoDAO.findAll());
		demoDAO.createDemo(testDemo);
		log.info("Bootstrapped: {}", demoDAO.findDemo(testDemo.getTitle()));
		log.warn("All: {}", demoDAO.findAll());
	}

}

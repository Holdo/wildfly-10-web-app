package cz.muni.fi.pv243.jms;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * For sending messages
 *
 * @author 422718
 */
@Slf4j
public class ArtistMessageSender {

	@Inject
	private JMSContext context;

	@Resource(lookup = "java:jboss/exported/jms/ArtistQueue")
	private Queue queue;

	public void sendCreateNew(DemoDTO demoDTO) {
		this.context.createProducer().send(queue, demoDTO);
		log.info("Message sent {}", demoDTO);
	}

}

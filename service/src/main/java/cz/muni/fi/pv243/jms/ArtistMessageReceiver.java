package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.dao.DemoDAO;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * For receiving messages
 *
 * @author Tomas Valka
 */
@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationType",
															 propertyValue = "javax.jms.Queue"),
								   @ActivationConfigProperty(propertyName = "destination",
															 propertyValue = "java:jboss/exported/jms/ArtistQueue"),
								   @ActivationConfigProperty(propertyName = "acknowledgeMode",
															 propertyValue = "Auto-acknowledge"),
})
@Slf4j
public class ArtistMessageReceiver implements MessageListener {

	@Inject
	private DemoDAO sampleDao;

	@Override
	public void onMessage(Message message) {
		// creating only for now
		try {
			final DemoDTO content = message.getBody(DemoDTO.class);
			log.info("Message received: {}", content);
			this.sampleDao.createDemo(content.toDemo());
		} catch (JMSException e) {
			log.error("Error receiving message: {}", message, e);
		}
	}
}

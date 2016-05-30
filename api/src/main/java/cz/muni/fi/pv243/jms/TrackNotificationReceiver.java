package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * For receiving track notifications
 * Each cluster has it's own JMS receiver to update local websocket sessions
 *
 * @author Michal Holic
 */
@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationType",
															 propertyValue = "javax.jms.Topic"),
								   @ActivationConfigProperty(propertyName = "destination",
															 propertyValue = "java:jboss/exported/jms/TrackTopic"),
								   @ActivationConfigProperty(propertyName = "acknowledgeMode",
															 propertyValue = "Auto-acknowledge"),
})
@Slf4j
public class TrackNotificationReceiver implements MessageListener {

	@Inject
	private TrackNotificationProcessor processor;

	@Override
	public void onMessage(Message message) {
		try {
			TrackNotification trackNotification = message.getBody(TrackNotification.class);
			processor.process(trackNotification);
		} catch (JMSException e) {
			log.error("Error receiving message: {}", message, e);
		}
	}

}

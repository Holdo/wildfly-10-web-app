package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 * For sending track comment notifications to Cluster
 *
 * @author Michal Holic
 */
@ApplicationScoped
@Slf4j
public class TrackNotificationSender {

	@Inject
	private JMSContext context;

	@Resource(lookup = "java:jboss/exported/jms/TrackTopic")
	private Topic topic;

	public void sendNotification(TrackNotification trackNotification) {
		log.info("Sending JMS notification to cluster for track {}", trackNotification.getTitle());
		this.context.createProducer().send(topic, trackNotification);
	}

}

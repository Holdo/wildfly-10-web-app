package cz.muni.fi.pv243.jms;

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
 * @author 422718
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
    private SampleDao sampleDao;

    @Override
    public void onMessage(Message message) {
        try {
            final SampleItem content = message.getBody(SampleItem.class);
            log.info("Message received: {}", content);
            this.sampleDao.store(content);
        } catch (JMSException e) {
            log.error("Error receiving message: {}", message, e);
        }
    }

}

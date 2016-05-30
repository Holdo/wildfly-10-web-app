package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.TrackNotification;
import cz.muni.fi.pv243.websocket.WebSocketServer;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * Tests the JMS settings
 *
 * @author Tomas Valka
 */
@RunWith(Arquillian.class)
public class JMSConfigAQ {

    public static final String ASSERTJ = "org.assertj:assertj-core:3.2.0";

    @Inject
    private TrackNotificationSender sender;

    @EJB(lookup = "java:global/test/DevNullWebSocketServer")
    private DevNullWebSocketServer webSocketServer;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                         .addClasses(TrackNotificationSender.class,
                                     TrackNotificationReceiver.class,
                                     TrackNotification.class,
                                     DevNullWebSocketServer.class,
                                     WebSocketServer.class)
                         .addPackage("cz.muni.fi.pv243.model")
                         .addAsWebInfResource("activemq-jms.xml")
                         .addAsWebInfResource("beans-web_socket_server.xml", "beans.xml")
                         .addAsLibraries(((Maven.resolver().resolve(ASSERTJ)).withTransitivity()).asFile());
    }

    @Test
    public void jms() throws InterruptedException {
        TrackNotification trackNotification = new TrackNotification("title", "author", "comment");

        this.sender.sendNotification(trackNotification);

        Thread.sleep(1000L); //for message to receive

        Assertions.assertThat(this.webSocketServer.getLastItem())
                  .isNotNull()
                  .isEqualTo(trackNotification);
    }

}

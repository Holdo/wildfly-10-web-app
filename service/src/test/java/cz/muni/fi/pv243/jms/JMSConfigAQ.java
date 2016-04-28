package cz.muni.fi.pv243.jms;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Tests the JMS settings
 *
 * @author 422718
 */
@RunWith(Arquillian.class)
public class JMSConfigAQ {

    public static final String ASSERTJ = "org.assertj:assertj-core:3.2.0";

    @Inject
    private TestDaoImpl testDaoImpl;
    @Inject
    private ArtistMessageSender sender;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                         .addClasses(ArtistMessageSender.class,
                                     ArtistMessageReceiver.class,
                                     SampleDao.class,
                                     SampleItem.class,
                                     TestDaoImpl.class)
                         .addAsWebInfResource("activemq-jms.xml")
                         .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
                         .addAsLibraries(((Maven.resolver().resolve(ASSERTJ)).withTransitivity()).asFile());
    }

    @Test
    public void jms() throws InterruptedException {
        final SampleItem item = new SampleItem("Sample Name");

        this.sender.send(item);

        Thread.sleep(1000L); //for message to receive

        Assertions.assertThat(this.testDaoImpl.getLastItem())
                  .isNotNull()
                  .isEqualTo(item);
    }

}

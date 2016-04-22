package cz.muni.fi.pv243;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * @author 422718
 */
@RunWith(Arquillian.class)
public class DryDeployAQ {

    private static final String WAR_PATH = "target/service-1.0-SNAPSHOT.war";

    @Deployment
    public static WebArchive createDeployment() {
        final File warFile = new File(WAR_PATH);
        return ShrinkWrap.createFromZipFile(WebArchive.class, warFile);
    }

    @Test
    public void dryDeploy() {
        // do nothing, just verify deployment
    }

}

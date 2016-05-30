package cz.muni.fi.pv243.rest;

import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.jms.service.DemoService;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Wallecnik on 30.05.16.
 */
@Slf4j
@RunWith(Arquillian.class)
public class DemoResourceImplAQ {

    public static final String ASSERTJ = "org.assertj:assertj-core:3.2.0";
    private static final String MOCKITO = "org.mockito:mockito-core:1.10.19";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                         .addClasses(JaxRsActivator.class,
                                     DemoResource.class,
                                     DemoResourceImpl.class,
                                     DemoService.class,
                                     TestDemoService.class)
                         .addPackage("cz.muni.fi.pv243.model")
                         .addClass(DemoDTO.class)
                         .addAsWebInfResource("beans-service.xml", "beans.xml")
                         .addAsWebInfResource("jboss-web.xml", "jboss-web.xml")
                         .addAsLibraries(((Maven.resolver().resolve(MOCKITO)).withTransitivity()).asFile())
                         .addAsLibraries(((Maven.resolver().resolve(ASSERTJ)).withTransitivity()).asFile());
    }

    @Test
    public void findByTitle() throws InterruptedException {
        Client client = ClientBuilder.newClient();
        Demo demo = client.target("http://localhost:8080/rest/demo/title/testTitle")
                          .request(MediaType.APPLICATION_JSON_TYPE)
                          .get(Demo.class);

        Assertions.assertThat(demo)
                  .isEqualTo(new Demo("testTitle",
                                      "testArtist",
                                      "testEmail@mail.cz",
                                      null,
                                      Demo.Status.UPLOADED,
                                      Collections.emptyList()));
    }

    @Test
    public void addTitle() {
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8080/rest/demo/upload/testTitle")
              .request(MediaType.APPLICATION_JSON_TYPE)
              .put(Entity.entity(new Demo("testTitle",
                                          "testArtist",
                                          "testEmail@mail.cz",
                                          null,
                                          Demo.Status.UPLOADED,
                                          Collections.emptyList()),
                                 MediaType.APPLICATION_JSON_TYPE));

        log.error("{}", TestDemoService.getLastCreate());
    }

    @Slf4j
    static class TestDemoService implements DemoService {

        private static final Demo commonInstance = new Demo("testTitle",
                                                            "testArtist",
                                                            "testEmail@mail.cz",
                                                            null,
                                                            Demo.Status.UPLOADED,
                                                            Collections.emptyList());
        private static final DemoDTO commonInstanceDTO = new DemoDTO(new Demo("testTitle",
                                                                              "testArtist",
                                                                              "testEmail@mail.cz",
                                                                              null,
                                                                              Demo.Status.UPLOADED,
                                                                              Collections.emptyList()));
        @Getter
        public static Demo lastCreate;

        @Override
        public void createDemo(Demo demo) {
            lastCreate = demo;
        }

        @Override
        public void updateDemo(Demo demo) {

        }

        @Override
        public void deleteDemo(Demo demo) {

        }

        @Override
        public Demo findDemo(String title) {
            if (title.equals("testTitle")) {
                return commonInstance;
            }
            return null;
        }

        @Override
        public List<Demo> findDemos(String interpret) {
            return null;
        }

        @Override
        public List<DemoDTO> findAll() {
            return Collections.singletonList(commonInstanceDTO);
        }

        @Override
        public Mp3Link getDemoLink(String title) throws IOException {
            return null;
        }
    }

}

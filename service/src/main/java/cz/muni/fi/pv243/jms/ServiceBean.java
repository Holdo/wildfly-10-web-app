package cz.muni.fi.pv243.jms;

import dao.DemoDAO;
import lombok.extern.slf4j.Slf4j;
import model.Demo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static model.Demo.Status.UPLOADED;

@Slf4j
@RequestScoped
public class ServiceBean {

    @Inject
    private DemoDAO demoDAO;

    private static Demo testDemo = new Demo();

    static {
        testDemo.setArtist("Interpreeteurer");
        testDemo.setStatus(UPLOADED);
        testDemo.setTitle("titelel");
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void run() {
        log.warn("All: {}", demoDAO.findAll());
        demoDAO.createDemo(testDemo);
        log.warn("Saved: {}", testDemo);
        log.warn("All: {}", demoDAO.findAll());
    }

}

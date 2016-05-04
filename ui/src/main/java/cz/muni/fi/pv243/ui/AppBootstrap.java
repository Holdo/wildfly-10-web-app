package cz.muni.fi.pv243.ui;

import cz.muni.fi.pv243.jms.ServiceBean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by Wallecnik on 04.05.16.
 */
@Singleton
@Startup
public class AppBootstrap {

    @Inject
    private ServiceBean bootstrap;

    @PostConstruct
    public void init() {
        this.bootstrap.run();
    }

}

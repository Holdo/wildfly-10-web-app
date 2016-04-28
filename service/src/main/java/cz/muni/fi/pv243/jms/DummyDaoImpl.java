package cz.muni.fi.pv243.jms;

import javax.enterprise.context.ApplicationScoped;

/**
 * MOCK emulation
 *
 * @author 422718
 */
@ApplicationScoped
public class DummyDaoImpl implements SampleDao {

    @Override
    public void store(SampleItem item) {
    }
}

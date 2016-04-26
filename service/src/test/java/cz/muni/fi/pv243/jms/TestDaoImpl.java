package cz.muni.fi.pv243.jms;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;

/**
 * MOCK emulation
 *
 * @author 422718
 */
@ApplicationScoped
public class TestDaoImpl implements SampleDao {

    @Getter
    private SampleItem lastItem;

    @Override
    public void store(SampleItem item) {
        this.lastItem = item;
    }
}

package cz.muni.fi.pv243.dao;


import cz.muni.fi.pv243.exceptions.DemoNotExistsException;
import cz.muni.fi.pv243.exceptions.TitleAlreadyExistsException;
import cz.muni.fi.pv243.model.Demo;

import java.util.List;

/**
 * Interface for persistence of a {@link Demo} entity. Contains basic CRUD operations on the entity.
 *
 * @author Marian Camak
 */
public interface DemoDAO {

    /**
     * Creates a demo and stores it in infinispan cache. Demo title has to be unique so it can be used as the key.
     *
     * @param demo which will be created.
     * @throws TitleAlreadyExistsException when a demo with the same title already exists.
     */
    void createDemo(Demo demo);

    /**
     * Updates the demo. Demo has to be created (stored) first.
     *
     * @param demo to be updated.
     * @throws DemoNotExistsException when the demo is not stored in our infinispan cache.
     */
    void updateDemo(Demo demo);

    /**
     * Deletes a demo from our infinispan cache. Demo has to be stored first, otherwise the
     * {@link DemoNotExistsException} will be thrown.
     *
     * @param demo to be deleted.
     * @throws DemoNotExistsException when the demo is not stored in our infinispan cache.
     */
    void deleteDemo(Demo demo);

    /**
     * Returns a Demo with given title.
     *
     * @param title of a demo which will be searched for.
     * @return the Demo with given title.
     * @throws DemoNotExistsException when a demo with given title is not found.
     */
    Demo findDemo(String title);

    /**
     * Finds all demos of an interpret.
     *
     * @param interpret whose demos will be returned.
     * @return a list of demos of an interpret.
     */
    List<Demo> findDemos(String interpret);

    /**
     * Finds all demos stored in our infinispan cache.
     *
     * @return a list of all stored demos.
     */
    List<Demo> findAll();
}

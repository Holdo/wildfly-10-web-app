package dao;

import model.Demo;

import java.util.List;

/**
 * Interface for persistence of a Demo entity.
 *
 * @author Marian Camak
 */
public interface DemoDAO {

    void createDemo(Demo demo);
    void updateDemo(Demo demo);
    void deleteDemo(Demo demo);

    Demo findDemo(String title);
    List<Demo> findDemos(String interpret);
    List<Demo> findAll();
    List<Demo> findAllNoMp3();
}

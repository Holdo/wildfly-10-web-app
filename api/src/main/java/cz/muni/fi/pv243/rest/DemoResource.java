package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.ws.rs.PathParam;
import model.Demo;

/**
 *
 * @author skylar
 */
public interface DemoResource {

    List<Demo> findAll();

    List<Demo> findAllFromArtist(String artist);

    Demo findByTitle(@PathParam("title") String id);

    /*void addDemo(
            @PathParam("artist") String artist,
            MultipartFormDataInput track
    );*/

    void removeDemo(@PathParam("title") String title);

}

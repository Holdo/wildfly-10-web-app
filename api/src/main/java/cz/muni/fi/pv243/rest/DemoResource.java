package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.json.JsonObject;
import javax.ws.rs.PathParam;
import model.Demo;

/**
 *
 * @author Diana Vilkolakova
 */
public interface DemoResource {

    List<Demo> findAll();

    List<Demo> findAllFromArtist(String artist);

    Demo findByTitle(@PathParam("title") String id);

    void addTitleWithArtistAndEmail(@PathParam("artist") String artist,
            @PathParam("title") String title,
            @PathParam("email") String email);

    void addTitle(@PathParam("title") String title);

    void addTitleWithArtist(@PathParam("artist") String artist, @PathParam("title") String title);

    void removeDemo(@PathParam("title") String title);
    
    void addDemo(JsonObject input);
}

package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.inject.Inject;
import model.Demo;
import dao.DemoDAO;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Base64;

/**
 *
 * @author Diana Vilkolakova
 */
@Path("/demo")
public class DemoResourceImpl implements DemoResource {

    @Inject
    private DemoDAO demoDao;

    @GET
    @Path("/findAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Demo> findAll() {
        return demoDao.findAllNoMp3();
    }

    @GET
    @Path("/artist/{artist}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Demo> findAllFromArtist(@PathParam("artist") String artist) {
        return demoDao.findDemos(artist);
    }

    @GET
    @Path("/title/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Demo findByTitle(@PathParam("title") String title) {
        return demoDao.findDemo(title);
    }

    @PUT
    @Path("/upload") 
    @Consumes("application/json")
    public void addDemo(JsonObject input) {
        Demo demo = new Demo();
        demo.setTitle(input.getString("title"));
        demo.setArtist(input.getString("artist"));
        demo.setEmail(input.getString("email"));
        demo.setStatus(Demo.Status.UPLOADED);        
        demo.setTrack(Base64.getDecoder().decode(input.getString("file")));
        demoDao.createDemo(demo);
    }

    @PUT
    @Path("/upload/{title}")
    public void addTitle(@PathParam("title") String title) {
        Demo demo = new Demo();
        demo.setTitle(title);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }

    @PUT
    @Path("/upload/{title}/{artist}")
    public void addTitleWithArtist(@PathParam("artist") String artist, @PathParam("title") String title) {
        Demo demo = new Demo();
        demo.setTitle(title);
        demo.setArtist(artist);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }

    @PUT
    @Path("/upload/{title}/{artist}/{email}")
    public void addTitleWithArtistAndEmail(@PathParam("artist") String artist,
            @PathParam("title") String title,
            @PathParam("email") String email) {
        Demo demo = new Demo();
        demo.setArtist(artist);
        demo.setTitle(title);
        demo.setEmail(email);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }

    @DELETE
    @Path("/{title}")
    public void removeDemo(@PathParam("title") String title) {
        demoDao.deleteDemo(demoDao.findDemo(title));
    }
}


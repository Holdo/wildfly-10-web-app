package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.inject.Inject;
import model.Demo;
import dao.DemoDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
        return demoDao.findAll();
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
    @Path("/{title}")
    public void addTitle(@PathParam("title") String title) {
        Demo demo = new Demo();
        demo.setTitle(title);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }

    @PUT
    @Path("/{title}/{artist}")
    public void addTitleWithArtist(@PathParam("artist") String artist, @PathParam("title") String title) {
        Demo demo = new Demo();
        demo.setTitle(title);
        demo.setArtist(artist);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }

    @PUT
    @Path("/{title}/{artist}/{email}")
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

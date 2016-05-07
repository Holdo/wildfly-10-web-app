/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Demo;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author skylar
 */
@Path("")
public interface DemoResource {

    @GET
    @Path("/getAll")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Demo> findAll();

    @GET
    @Path("/artist/{artist}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Demo> findAllFromArtist(String artist);

    @GET
    @Path("/title/{title}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Demo findByTitle(@PathParam("title") String id);

    @PUT
    @Path("/demo/add/{artist}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addDemo(
            @PathParam("artist") String artist,
            MultipartFormDataInput track
    );

    @DELETE
    @Path("/demo/delete/{title}")
    public void removeDemo(@PathParam("title") String title);

}

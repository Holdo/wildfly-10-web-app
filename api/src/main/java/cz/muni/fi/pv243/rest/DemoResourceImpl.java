package cz.muni.fi.pv243.rest;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;

import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.jms.service.DemoService;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;
import lombok.extern.slf4j.Slf4j;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Base64;

/**
 *
 * @author Diana Vilkolakova
 */
@Path("/demo")
@Slf4j
public class DemoResourceImpl implements DemoResource {

	@Inject
	private DemoService demoService;

	@GET
	@Path("/findAll")
	@Produces({MediaType.APPLICATION_JSON})
	public List<DemoDTO> findAll() {
		return demoService.findAll();
	}

	@GET
	@Path("/artist/{artist}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Demo> findAllFromArtist(@PathParam("artist") String artist) {
		return demoService.findDemos(artist);
	}

	@GET
	@Path("/title/{title}")
	@Produces({MediaType.APPLICATION_JSON})
	public Demo findByTitle(@PathParam("title") String title) {
		return demoService.findDemo(title);
	}

	@GET
	@Path("/mp3link/{title}")
	@Produces({MediaType.APPLICATION_JSON})
	public Mp3Link getMp3LinkByTitle(@PathParam("title") String title) throws IOException {
		return demoService.getDemoLink(title);
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
		demoService.createDemo(demo);
	}

	@PUT
	@Path("/upload/{title}")
	public void addTitle(@PathParam("title") String title) {
		Demo demo = new Demo();
		demo.setTitle(title);
		demo.setStatus(Demo.Status.UPLOADED);
		demoService.createDemo(demo);
	}

	@PUT
	@Path("/upload/{title}/{artist}")
	public void addTitleWithArtist(@PathParam("artist") String artist, @PathParam("title") String title) {
		Demo demo = new Demo();
		demo.setTitle(title);
		demo.setArtist(artist);
		demo.setStatus(Demo.Status.UPLOADED);
		demoService.createDemo(demo);
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
		demoService.createDemo(demo);
	}

	@DELETE
	@Path("/{title}")
	public void removeDemo(@PathParam("title") String title) {
		demoService.deleteDemo(demoService.findDemo(title));
	}
}


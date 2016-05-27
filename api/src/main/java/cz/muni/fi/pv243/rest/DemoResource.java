package cz.muni.fi.pv243.rest;

import java.io.IOException;
import java.util.List;
import javax.json.JsonObject;

import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;

/**
 *
 * @author Diana Vilkolakova
 */
public interface DemoResource {

	List<DemoDTO> findAll();

	List<Demo> findAllFromArtist(String artist);

	Demo findByTitle(String id);

    Mp3Link getMp3LinkByTitle(String title) throws IOException;

	void addTitleWithArtistAndEmail(String artist, String title, String email);

	void addTitle(String title);

	void addTitleWithArtist(String artist, String title);

	void removeDemo(String title);
	
	void addDemo(JsonObject input);
}

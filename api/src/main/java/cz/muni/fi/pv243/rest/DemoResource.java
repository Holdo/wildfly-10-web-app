package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.json.JsonObject;
import cz.muni.fi.pv243.model.Demo;

/**
 *
 * @author Diana Vilkolakova
 */
public interface DemoResource {

	List<Demo> findAll();

	List<Demo> findAllFromArtist(String artist);

	Demo findByTitle(String id);

    String getMp3LinkByTitle(String title);

	void addTitleWithArtistAndEmail(String artist, String title, String email);

	void addTitle(String title);

	void addTitleWithArtist(String artist, String title);

	void removeDemo(String title);
	
	void addDemo(JsonObject input);
}

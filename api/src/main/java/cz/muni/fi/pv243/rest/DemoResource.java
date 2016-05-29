package cz.muni.fi.pv243.rest;

import cz.muni.fi.pv243.jms.DemoDTO;
import cz.muni.fi.pv243.model.Demo;
import cz.muni.fi.pv243.model.Mp3Link;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.List;

/**
 * Rest layer.
 *
 * @author Diana Vilkolakova
 */
public interface DemoResource {

    /**
     * Finds all demos stored, mapped to DemoDTOs.
     *
     * @return list of DemoDTOs
     */
	List<DemoDTO> findAll();

    /**
     * Finds all demos created by given artist.
     *
     * @param artist name of the artist.
     * @return list of artist's demos
     */
	List<Demo> findAllFromArtist(String artist);

    /**
     * Finds a demo by title.
     *
     * @param title of the demo
     * @return found demo
     */
	Demo findByTitle(String title);

    /**
     * Returns a link (relative path) to the demo track mp3 file.
     *
     * @param title of the demo
     * @return relative path to the demo track mp3 file
     * @throws IOException when the mp3 is not found (due to wrong title name or so)
     */
	Mp3Link getMp3LinkByTitle(String title) throws IOException;

    /**
     * Creates a new demo.
     *
     * @param artist name of the artist
     * @param title of the demo
     * @param email address of the artist
     */
	void addTitleWithArtistAndEmail(String artist, String title, String email);

    /**
     * Creates a new demo.
     *
     * @param title of the demo
     */
	void addTitle(String title);

    /**
     * Creates a new demo.
     *
     * @param artist name of the artist
     * @param title of the demo
     */
	void addTitleWithArtist(String artist, String title);

    /**
     * Removes a demo with title <code>title</code>
     *
     * @param title of the demo to be removed
     */
	void removeDemo(String title);

    /**
     * Creates a new demo, including the mp3 file (track).
     *
     * @param input json object contating all information of the demo.
     */
    void addDemo(JsonObject input);

    /**
     * Runs the batchlet to delete old mp3s.
     */
    void triggerBatchlet();
}

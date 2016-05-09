package cz.muni.fi.pv243.jms;

import lombok.AllArgsConstructor;
import model.Demo;

import java.io.Serializable;
import java.util.Collections;

import static model.Demo.Status.UPLOADED;

/**
 * @author 422718
 */
@AllArgsConstructor
public class DemoDTO implements Serializable {

    /**
     * ID, name of the demo
     */
    private String title;

    /**
     * Interpret of the demo.
     */
    private String artist;

    /**
     * Demo track.
     */
    private byte[] track;

    public Demo toDemo() {
        final Demo demo = new Demo();
        demo.setTitle(this.title);
        demo.setArtist(this.artist);
        demo.setTrack(null); //TODO howto?!!!
        demo.setStatus(UPLOADED);
        demo.setComments(Collections.emptyList());
        return demo;
    }

}

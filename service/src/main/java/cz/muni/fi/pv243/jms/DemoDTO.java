package cz.muni.fi.pv243.jms;

import cz.muni.fi.pv243.model.Demo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Class representing a demo song, containing several information about it.
 *
 * @author Michal Holic
 */
@Getter
@Setter
@Indexed(index="DemoIndex")
@NoArgsConstructor
public class DemoDTO implements Serializable {

	private static final long serialVersionUID = 7910152804839284560L;
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public DemoDTO(Demo demo) {
		setArtist(demo.getArtist());
		setEmail(demo.getEmail());
		setStatus(demo.getStatus());
		setTitle(demo.getTitle());
	}

	public Demo toDemo() {
		Demo demo = new Demo();
		demo.setArtist(getArtist());
		demo.setEmail(getEmail());
		demo.setStatus(getStatus());
		demo.setTitle(getTitle());
		return demo;
	}

	/**
	 * Unique name of the demo.
	 */
	@NotNull
	@Size(min=3, max=255)
	@Field(analyze= Analyze.NO)
	private String title;

	/**
	 * Interpret of the demo.
	 */
	@NotNull
	@Size(min=3, max=255)
	@Field(analyze=Analyze.NO)
	private String artist;

	/**
	 * E-mail address of the artist.
	 */
	@NotNull
	@Pattern(regexp=EMAIL_PATTERN)
	@Field(analyze=Analyze.NO)
	private String email;

	/**
	 * Status of the demo uploaded.
	 */
	@NotNull
	@Field(analyze=Analyze.NO)
	private Demo.Status status;

	@Override
	public int hashCode() {
		return getTitle().hashCode();
	}

	@Override
	public String toString() {
		return "Demo {" +
				"title='" + title + '\'' +
				", artist='" + artist + '\'' +
				'}';
	}
}

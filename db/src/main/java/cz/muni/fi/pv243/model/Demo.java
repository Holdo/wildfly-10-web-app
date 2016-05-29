package cz.muni.fi.pv243.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a demo track, containing several information about it.
 *
 * @author Marian Camak
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed(index="DemoIndex")
public class Demo implements Serializable {

	private static final long serialVersionUID = 7910152804839284560L;
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Unique name of the demo. The length of the title has to be more than 2 and less than 256 characters.
	 */
	@NotNull
	@Size(min=3, max=255)
	@Field(analyze= Analyze.NO)
	private String title;

	/**
	 * Interpret of the demo. The length of the artist name has to be more than 2 and less than 256 characters.
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
	 * Demo track.
	 */
	@NotNull
	@JsonIgnore
	private byte[] track;

	/**
	 * Status of the demo uploaded.
	 */
	@NotNull
	@Field(analyze=Analyze.NO)
	private Status status;

	/**
	 * Collection of user comments.
	 */
	private List<Comment> comments = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Demo demo = (Demo) o;

		return getTitle().equals(demo.getTitle());
	}

	@Override
	public int hashCode() {
		return getTitle().hashCode();
	}

	/**
	 * Returns a demo as a printable string, containing the title, artist name and the presence of the track stored.
	 *
	 * @return the demo as a printable string.
	 */
	@Override
	public String toString() {
		String isMp3Present = (track == null)? "no" : "yes";
		return "Demo {" +
				"title='" + title + '\'' +
				", artist='" + artist + '\'' +
				", mp3=" + isMp3Present +
				'}';
	}

	/**
	 * Status of a demo.
	 */
	public enum Status {

		/**
		 * Demo was uploaded, but not reviewed yet.
		 */
		UPLOADED,

		/**
		 * Demo was reviewed and approved for publishing.
		 */
		APPROVED,

		/**
		 * Demo was reviewed but rejected.
		 */
		REJECTED,

		/**
		 * Demo was published in our label.
		 */
		PUBLISHED
	}
}

package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a demo song, containing several information about it.
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

    /**
     * Unique name of the demo.
     */
    @NotNull
    @Field(analyze= Analyze.NO)
    private String title;

    /**
     * Interpret of the demo.
     */
    @NotNull
    @Field(analyze=Analyze.NO)
    private String artist;

    /**
     * Demo track.
     */
    @NotNull
    private byte[] track;

    /**
     * Status of the demo uploaded.
     */
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

        if (!getTitle().equals(demo.getTitle())) return false;
        return getArtist().equals(demo.getArtist());
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getArtist().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Demo {" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    /**
     * Class representing a comment to a demo for exchange of notes and information.
     */
    @Getter
    @Setter
    public class Comment implements Serializable {
        private static final long serialVersionUID = -6729363174359158134L;

        /**
         * Author of the comment
         */
        @NotNull
        private String author;

        /**
         * Text of a comment
         */
        private String comment;

        /**
         * Reactions to an comment
         */
        private List<Comment> reactions = new ArrayList<>();
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

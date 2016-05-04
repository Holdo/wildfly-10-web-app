package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Indexed;

import javax.validation.constraints.NotNull;
import java.io.File;
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
@Indexed
public class Demo implements Serializable {

    private static final long serialVersionUID = 7910152804839284560L;
    /**
     * Unique name of the demo.
     */
    @NotNull
    private String title;

    /**
     * Interpret of the demo.
     */
    @NotNull
    private String interpret;

    /**
     * Demo track.
     */
    @NotNull
    private File track;

    /**
     * Status of the demo uploaded.
     */
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
        return getInterpret().equals(demo.getInterpret());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getInterpret().hashCode();
        return result;
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

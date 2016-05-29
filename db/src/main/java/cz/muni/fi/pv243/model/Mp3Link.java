package cz.muni.fi.pv243.model;

import lombok.*;

/**
 * Contains a link (path) to the {@link Demo} track file (mp3).
 *
 * @author Michal Holic
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Mp3Link {

    /**
     * Link (path) to the {@link Demo} track file (mp3).
     */
	String link = "";
}

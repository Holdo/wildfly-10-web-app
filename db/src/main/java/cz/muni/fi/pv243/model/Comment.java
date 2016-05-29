package cz.muni.fi.pv243.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Class representing a comment to a demo for exchange of notes and information.
 *
 * @author Marian Camak
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
	private static final long serialVersionUID = -6729363174359158134L;

	/**
	 * Author of the comment. Must be more than 2 and less than 256 characters.
	 */
	@NotNull
	@Size(min=3, max=255)
	private String author;

	/**
	 * Text of a comment. 
	 */
	@NotNull
	@Size(min=3, max=1024)
	private String comment;
}
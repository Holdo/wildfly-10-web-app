package cz.muni.fi.pv243.model;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Michal Holic on 29.05.2016
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TrackNotification implements Serializable {

	private static final long serialVersionUID = 4556284560L;

	private String title = "";
	private String author = "";
	private String comment = "";
}

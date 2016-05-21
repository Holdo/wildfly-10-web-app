package cz.muni.fi.pv243.exceptions;

import javax.persistence.PersistenceException;

/**
 * Exception signalising that Demo, which is to update, is not created.
 *
 * @author Marian Camak
 */
public class DemoNotExistsException extends PersistenceException {
	private static final long serialVersionUID = -3647315103467115868L;

	public DemoNotExistsException(String message) {
		super(message);
	}
}

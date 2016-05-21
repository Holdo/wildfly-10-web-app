package exceptions;

import javax.persistence.PersistenceException;

/**
 * Exceptions signalising, that the Demo (Title) already exists.
 *
 * @author Marian Camak
 */
public class TitleAlreadyExistsException extends PersistenceException {
    private static final long serialVersionUID = 8392856899912125522L;

    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}

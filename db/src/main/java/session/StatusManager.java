package session;

import model.Demo.Status;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * @author Marian Camak (inQool) on 4. 5. 2016.
 */
public class StatusManager {

    @Produces
    @Named
    public Status[] getStatuses() {
        return Status.values();
    }
}

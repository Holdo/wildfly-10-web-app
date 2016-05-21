package cz.muni.fi.pv243.session;

import cz.muni.fi.pv243.model.Demo.Status;

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

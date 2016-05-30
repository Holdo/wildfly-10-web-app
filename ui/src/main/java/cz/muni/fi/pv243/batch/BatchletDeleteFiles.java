package cz.muni.fi.pv243.batch;

import cz.muni.fi.pv243.jms.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.Batchlet;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import java.io.File;

/**
 * Class containing the definition of the batch job. When executed, it deletes all the mp3s from the corresponding
 * folder which are older than 24 hours.
 *
 * @author Diana Vilkolakova
 */
@Dependent
@Named("BatchletDeleteFiles")
public class BatchletDeleteFiles implements Batchlet {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Inject
	private DemoService demoService;

	@Override
	public String process() throws Exception {
		String dirPath = System.getProperty("jboss.home.dir") + File.separator + "mp3-resources";
		int hoursOld = 24;
		File folder = new File(dirPath);

		if (folder.exists()) {
			File[] listFiles = folder.listFiles();
			long eligibleForDeletion = System.currentTimeMillis() - (hoursOld * 60 * 60 * 1000L);

			boolean deletedSomething = false;
			for (File listFile : listFiles) {
				if (listFile.lastModified() < eligibleForDeletion) {
					String fileName = listFile.getName();
					if (fileName.endsWith(".mp3")) fileName = fileName.substring(0, fileName.length() - 4);
					else continue;
					if (!listFile.delete()) {
						log.warn("Unable to delete " + listFile.getCanonicalPath());
					} else {
						deletedSomething = true;
						log.info("Deleted " + listFile.getCanonicalPath());
						try {
							demoService.deleteDemo(demoService.findDemo(fileName));
						} catch (PersistenceException e) {
							//Ignore
						}
					}
				}
			}
			if (deletedSomething) log.info("Batch job deleted all mp3s older than {} hours", hoursOld);
			else log.info("Batch job didn't delete any mp3s because none are older than {} hours", hoursOld);
		} else {
			log.error("Folder does not exist: " + dirPath);
		}
		return "COMPLETED";
	}

	@Override
	public void stop() throws Exception {
	}
}

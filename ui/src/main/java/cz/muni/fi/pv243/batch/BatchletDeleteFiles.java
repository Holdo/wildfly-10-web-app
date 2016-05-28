package cz.muni.fi.pv243.batch;

import java.io.File;
import javax.batch.api.Batchlet;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
@Named("BatchletDeleteFiles")
public class BatchletDeleteFiles implements Batchlet {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public String process() throws Exception {
        String dirPath = System.getProperty("jboss.home.dir") + File.separator + "mp3-resources";
        int hoursOld = 5;
        File folder = new File(dirPath);

        if (folder.exists()) {

            File[] listFiles = folder.listFiles();

            long eligibleForDeletion = System.currentTimeMillis()
                    - (hoursOld * 60 * 60 * 1000L);

            for (File listFile : listFiles) {

                if (listFile.lastModified() < eligibleForDeletion) {

                    if (!listFile.delete()) {

                        log.info("Unable to Delete Files in Batch");

                    }
                }
            }
        } else {
            log.info("Folder with tracks does not exist");
        }
        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {
    }
}

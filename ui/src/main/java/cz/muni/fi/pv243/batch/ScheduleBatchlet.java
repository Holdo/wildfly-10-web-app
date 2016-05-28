
package cz.muni.fi.pv243.batch;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class ScheduleBatchlet {

    @Schedule(dayOfWeek="*", hour="5") 
    public void processFiles() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        jobOperator.start("BatchletDelete", null);
    }
}

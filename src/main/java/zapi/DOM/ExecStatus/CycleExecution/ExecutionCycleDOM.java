package zapi.DOM.ExecStatus.CycleExecution;

import java.util.List;

/**
 * Created by Chaitanya on 18-Jul-15.
 */
public class ExecutionCycleDOM {
    long issueId;
    int recordsCount;
    List<ExecutionCycleIndividualDOM> executions;

    public long getIdByVersion(long versionId) {
        long idToBeReturned = -1;
        for (ExecutionCycleIndividualDOM iterator : executions) {
            if (iterator.getVersionId() == versionId) {
                idToBeReturned = iterator.getId();
            }
        }
        return idToBeReturned;
    }
}

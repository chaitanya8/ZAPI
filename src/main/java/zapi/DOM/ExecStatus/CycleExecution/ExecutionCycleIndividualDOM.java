package zapi.DOM.ExecStatus.CycleExecution;

/**
 * Created by Chaitanya on 18-Jul-15.
 */
public class ExecutionCycleIndividualDOM {
    long id;
    long issueId;
    int executionStatus;
    String cycleName;
    long versionId;
    String versionName;
    String projectKey;

    public long getId() {
        return id;
    }

    public long getIssueId() {
        return issueId;
    }

    public int getExecutionStatus() {
        return executionStatus;
    }

    public String getCycleName() {
        return cycleName;
    }

    public long getVersionId() {
        return versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getProjectKey() {
        return projectKey;
    }
}

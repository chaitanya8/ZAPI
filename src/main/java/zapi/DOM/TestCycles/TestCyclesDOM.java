package zapi.DOM.TestCycles;

/**
 * Created by Chaitanya on 31-May-15.
 */
public class TestCyclesDOM {
    private int id;
    private String projectKey;
    private long versionId;
    private String name;
    private long projectId;
    private long totalExecutions;
    private String build;

    public String getProjectKey() {
        return projectKey;
    }

    public long getVersionId() {
        return versionId;
    }

    public String getName() {
        return name;
    }

    public long getProjectId() {
        return projectId;
    }

    public int getId() {
        return id;
    }

    public long getTotalExecutions() {
        return totalExecutions;
    }

    public String getBuild() {
        return build;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package zapi.DOM.RequestDOM;

import java.util.Arrays;

/**
 * Created by Chaitanya on 12-Jul-15.
 */
public class TestExecution {
    /*{
    "issues": ["TEST-1"],
    "versionId": 10000,
    "cycleId": "1",
    "projectId": "10000",
    "method": "1"
}*/
    String [] issues;
    long versionid;
    String cycleId;
    String projectId;
    String method;

    public TestExecution(String[] issues, long versionid, String cycleId, String projectId, String method) {
        this.issues = issues;
        this.versionid = versionid;
        this.cycleId = cycleId;
        this.projectId = projectId;
        this.method = method;
    }

    public TestExecution() {
    }

    public String[] getIssues() {
        return issues;
    }

    public void setIssues(String[] issues) {
        this.issues = issues;
    }

    public long getVersionid() {
        return versionid;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "{" +
                "\"issues\":[\"" + issues[0] +"\"]"+
                ", \"versionId\":" + versionid +
                ", \"cycleId\":\"" + cycleId + '"' +
                ", \"projectId\":\"" + projectId + '"' +
                ", \"method\":\"" + method + '"' +
                '}';
    }
}

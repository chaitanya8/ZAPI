package zapi.DOM.RequestDOM;

import java.util.Arrays;

/**
 * Created by Chaitanya on 20-Jul-15.
 */
public class StepResultUpdateDOM {
    /*{
    "id": 28,
    "issueId": "10100",
    "executionId": 34,
    "status": "1",
    "defectList": [
        "ZFJ-3"
    ],
    "updateDefectList": "true"
}*/
    long id;
    String issueId;
    long executionId;
    String status;
     String updateDefectList;
     String [] defectList;
    /*@Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", issueId=\"" + issueId + '\"' +
                ", executionId=" + executionId +
                ", status=\"" + status + '\"' +
                '}';
    }*/

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id +
                ", \"issueId\" : \"" + issueId + '\"' +
                ", \"executionId\" : " + executionId +
                ", \"status\" : \"" + status + '\"' +
//                ", updateDefectList : \"" + updateDefectList + '\"' +
//                ", defectList : " + defectList[0] +
                "}";
    }

    public StepResultUpdateDOM(long id, String issueId, long executionId, String status) {
        this.id = id;
        this.issueId = issueId;
        this.executionId = executionId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getIssueId() {
        return issueId;
    }

    public long getExecutionId() {
        return executionId;
    }

    public String getStatus() {
        return status;
    }
}

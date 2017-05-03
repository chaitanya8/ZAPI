package zapi.DOM.JiraIssue;

/**
 * Created by Chaitanya on 5-Jul-15.
 */
public class IssueDOM {
    long id;
    String key;
    FieldsDOM fields;

    public long getIdAsLong() {
        return id;
    }

    public String getIdAsString() {
        return Long.toString(id);
    }

    public String getKey() {
        return key;
    }

    public FieldsDOM getFields() {
        return fields;
    }

    public long getProjectId() {
        return this.getFields().getProject().getId();
    }

    public String getProjectName() {
        return this.getFields().getProject().getName();
    }

    public String getIssueType() {
        return this.getFields().getIssuetype().getName();
    }
}

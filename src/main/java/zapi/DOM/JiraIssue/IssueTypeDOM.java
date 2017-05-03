package zapi.DOM.JiraIssue;

/**
 * Created by Chaitanya on 6-Jul-15.
 */
public class IssueTypeDOM {
    /*issuetype": {
      "self": "http://localhost:9000/rest/api/2/issuetype/10000",
      "id": "10000",
      "description": "This Issue Type is used to create Zephyr Test within Jira.",
      "iconUrl": "http://localhost:9000/download/resources/com.thed.zephyr.je/images/icons/ico_zephyr_issuetype.png",
      "name": "Test",
      "subtask": false*/
    String self;
    String id;
    String description;
    String name;
    boolean subtask;

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isSubtask() {
        return subtask;
    }
}

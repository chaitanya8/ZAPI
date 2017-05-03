package zapi.DOM.JiraIssue;

/**
 * Created by Chaitanya on 5-Jul-15.
 */
public class ProjectIssueDOM {
    /*"project": {
      "self": "http://localhost:9000/rest/api/2/project/10001",
      "id": "10001",
      "key": "TEST",
      "name": "TEST",*/
    String self;
    long id;
    String key;
    String name;

    public String getSelf() {
        return self;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

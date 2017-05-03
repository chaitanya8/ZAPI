package zapi.DOM.Projects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Chaitanya on 5-Jun-15.
 */
public class ProjectOptionsDOM {
    public static final Logger logger = Logger.getLogger(ProjectOptionsDOM.class.getName());
    //    @SerializedName("options")
    List<ProjectDOM> options = new ArrayList<ProjectDOM>();

    public List<ProjectDOM> getOptions() {
        return options;
    }

    public void setOptions(List<ProjectDOM> options) {
        this.options = options;
    }

    public long getProjectIdForProjectName(String projectName) {
        ProjectDOM toBeReturned = null;
        for (ProjectDOM x : this.options) {
            if (x.getLabel().equalsIgnoreCase(projectName)) {
                toBeReturned = x;
            }
        }
        if (toBeReturned == null) {
            logger.severe("PROJECT BY NAME : \"" + projectName + "\" NOT FOUND!");
        }
        return toBeReturned.getValue();
    }
}

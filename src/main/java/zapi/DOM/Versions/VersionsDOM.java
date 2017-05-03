package zapi.DOM.Versions;

/**
 * Created by Chaitanya on 31-May-15.
 */
public class VersionsDOM {
    private boolean archived;
    private String label;
    private long value;

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

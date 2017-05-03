package zapi.DOM.TestSteps;

/**
 * Created by Chaitanya on 20-Jul-15.
 */
public class StepExecutionStatusDOM {
    long id;
    double executedOn;
    String executedBy;
    long executionId;
    int stepId;
    String[] defects;
    String modifiedBy;

    public long getId() {
        return id;
    }

    public double getExecutedOn() {
        return executedOn;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public long getExecutionId() {
        return executionId;
    }

    public int getStepId() {
        return stepId;
    }

    public String[] getDefects() {
        return defects;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }
}

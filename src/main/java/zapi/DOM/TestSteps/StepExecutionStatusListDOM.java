package zapi.DOM.TestSteps;

/**
 * Created by Chaitanya on 21-Jul-15.
 */
public class StepExecutionStatusListDOM {
    StepExecutionStatusDOM[] stepExecutionStatusDOMs;

    public StepExecutionStatusListDOM(StepExecutionStatusDOM[] stepExecutionStatusDOMs) {
        this.stepExecutionStatusDOMs = stepExecutionStatusDOMs;
    }

    public StepExecutionStatusDOM[] getStepExecutionStatusDOMs() {
        return stepExecutionStatusDOMs;
    }

    public long getExecutionIdForEachTestStep(long id) {
        StepExecutionStatusDOM toBeReturned = null;
        for (StepExecutionStatusDOM iterator : stepExecutionStatusDOMs) {
            if (iterator.getStepId() == id) {
                toBeReturned = iterator;
            }
        }
        return toBeReturned.getId();
    }
}

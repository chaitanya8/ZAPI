package zapi.DOM.TestCycles;

import java.util.List;

/**
 * Created by Chaitanya on 24-Jun-15.
 */
public class TestCycleList {
    public List<TestCyclesDOM> testCyclesDOMs;

    public TestCycleList(){}

    public TestCycleList (List<TestCyclesDOM> testCyclesDOMs) {
        this.testCyclesDOMs = testCyclesDOMs;
    }

    public List<TestCyclesDOM> getTestCyclesDOMs() {
        return this.testCyclesDOMs;
    }

    public void setTestCyclesDOMs(List<TestCyclesDOM> testCyclesDOMs) {
        this.testCyclesDOMs = testCyclesDOMs;
    }

    public int getRecordCount(){
        return getTestCyclesDOMs().size();
    }

    public int getTestCycleIdFromName(String testCycleName){
        TestCyclesDOM toBeReturnedTestCycleDOM = null;
        for (TestCyclesDOM x : testCyclesDOMs) {
            if (x.getName().equalsIgnoreCase(testCycleName)) {
                toBeReturnedTestCycleDOM = x;
            }
        }
        return toBeReturnedTestCycleDOM.getId();
    }
}


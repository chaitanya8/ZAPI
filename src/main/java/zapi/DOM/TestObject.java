package zapi.DOM;

import zapi.ConnectionManager;
import zapi.DOM.ExecStatus.CycleExecution.ExecutionCycleDOM;
import zapi.DOM.ExecStatus.StepExecutionList;
import zapi.DOM.ExecStatus.TestExecutionList;
import zapi.DOM.JiraIssue.IssueDOM;
import zapi.DOM.Projects.ProjectOptionsDOM;
import zapi.DOM.TestCycles.TestCycleList;
import zapi.DOM.TestSteps.StepExecutionStatusListDOM;
import zapi.DOM.Versions.VersionsList;
import zapi.Utils.PropertiesParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by Chaitanya on 13-Jun-15.
 */
public class TestObject {
    public static final String TEST_ISSUE_TYPE = "Test";
    public static final String TEST_CYCLE_NAME = "Automated";

    private StepExecutionList stepExecutionList;
    private TestExecutionList testExecutionList;
    private VersionsList versionsList;
    private ProjectOptionsDOM projectOptionsDOM;
    private TestCycleList testCycleList;
    public TestStepsDOM[] testSteps;
    private IssueDOM issue;
    private static TestObject testObject = null;
    private ExecutionCycleDOM executionCycleDOM;

    String key;
    long versionId;
    long projectId;
    String versionName;
    long executionId = -1;
    boolean testExecutedInSession = false;
    public boolean isTestFail = false;
    public int stepNumberUnderExecution = 0;

    static Logger logger = Logger.getLogger(TestObject.class.getName());

    public static TestObject createTestObject(String key) throws IOException, URISyntaxException {
        if (testObject == null) {
            testObject = new TestObject(key);
        }
        return testObject;
    }

    public static TestObject getInstance() {
        return testObject;
    }


    // TODO :  Make Singleton with Global Access
    protected TestObject(String inputKey) throws IOException, URISyntaxException {
        // TODO : Validate Version format and existence on jira
//        String inputVersion = "2015.09.03";
        String inputVersion = PropertiesParser.getVersionFromTerminal();
        this.versionName = inputVersion;
        this.key = inputKey;
        if (ConnectionManager.isIssueValidTest(key)) {
            logger.info("ISSUE IS VALID");
            issue = ConnectionManager.getIssueId(key);
            if (issue.getIssueType().equalsIgnoreCase(TEST_ISSUE_TYPE)) {
                versionsList = ConnectionManager.getProjectVersions(issue.getProjectId());
                if ((versionsList.findUnreleasedProjectVersion(versionName)) != null) {
                    logger.info("Version found!!");
                    versionId = versionsList.findUnreleasedProjectVersion(versionName).getValue();
                    testCycleList = ConnectionManager.getTestCycles(issue.getProjectId(), versionId);
                }
                projectOptionsDOM = ConnectionManager.getProjectsList();
                projectId = projectOptionsDOM.getProjectIdForProjectName(issue.getProjectName());
            } else {
                logger.severe("INVALID ISSUE KEY");
            }
            stepExecutionList = ConnectionManager.getStepExecutionStatusSupported();
            testExecutionList = ConnectionManager.getTestExecutionStatusSupported();
            testSteps = ConnectionManager.getTestSteps(issue.getIdAsLong());
            executionId = getExecutionForTest();
            if (executionId == -1) {
                logger.info("TEST not executed under current test cycle.");
                executeTestUnderCycle();
                executionId = getExecutionForTest();
                initTestStep();
            }
            getTestStepsWithExecutionIds();
//            markStepStatusTo(testSteps[0].getExecutionId(), TestStatus.iStatus_WIP);
        }
    }

    // get Test execution Id for a Test, if not exists, creates one
    public long getExecutionForTest() throws IOException, URISyntaxException {
        ConnectionManager.refreshSession();
        executionCycleDOM = ConnectionManager.getTestExecutionForIssue(issue.getIdAsLong());
        executionId = executionCycleDOM.getIdByVersion(versionId);
        logger.info("EXECUTION ID : " + executionId);
        return executionId;
    }

    // executes a Test under a given test cycle
    public void executeTestUnderCycle() throws IOException, URISyntaxException {
        ConnectionManager.addTestToTestCycle(issue.getKey(),
                versionId,
                issue.getProjectId(),
                Integer.toString(testCycleList.getTestCycleIdFromName(TEST_CYCLE_NAME)));
        testExecutedInSession = true;
    }

    public void markTestStatusTo(int statusToSet) throws IOException, URISyntaxException {
        ConnectionManager.changeTestStatus(executionId, statusToSet);
        logger.info("Test Status changed to : " + statusToSet);
    }

    public void markStepStatusTo(long id, int statusToSet) throws IOException, URISyntaxException {
        ConnectionManager.changeStepStatus(id, issue.getIdAsString(), executionId, String.valueOf(statusToSet));
    }

    public void getTestStepsWithExecutionIds() throws IOException, URISyntaxException {
        ConnectionManager.refreshSession();
        logger.info(String.valueOf(this.issue.getIdAsLong()));
        testSteps = ConnectionManager.getTestSteps(issue.getIdAsLong());
//        executionId = getExecutionForTest();
        StepExecutionStatusListDOM stepExecutionStatusList = ConnectionManager.getStepExecutionForIssue(executionId);
        for (TestStepsDOM iterator : testSteps) {
            int id = iterator.getId();
            iterator.setExecutionId((stepExecutionStatusList.getExecutionIdForEachTestStep(id)));
        }
    }

    public void markCurrentStepAs(int status) throws IOException, URISyntaxException {
        markStepStatusTo(testSteps[stepNumberUnderExecution].getExecutionId(), status);
        logger.info("MARKED STEP AS : " + status);
        stepNumberUnderExecution++;
    }

    public void initTestStep() throws IOException, URISyntaxException {
        for (TestStepsDOM iterator : testSteps) {
            ConnectionManager.initiateTestStepsAfterExecution(iterator.getId(), executionId);
        }
    }

    @Override
    public String toString() {
        return "ISSUE NAME : " + issue.getKey()
                + "\nISSUE ID : " + issue.getIdAsLong()
                + "\nPROJECT ID : " + issue.getProjectId()
                + "\nPROJECT NAME : " + issue.getProjectName()
                + "\nVERSION ID : " + versionId
                + "\nVERSION NAME : " + versionName;
    }
}

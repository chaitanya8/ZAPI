package zapi.Listeners;

import org.testng.*;
import zapi.ConnectionManager;
import zapi.DOM.ExecStatus.TestStatus;
import zapi.DOM.TestObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by Chaitanya on 13-Jun-15.
 */
public class CustomListener implements ISuiteListener, ITestListener {
    static Logger logger = Logger.getLogger(CustomListener.class.getName());
    TestObject currentTest;

    public void onStart(ISuite suite) {
        logger.info("ON SUITE START : MARK TEST FOR EXECUTION");
        try {
            currentTest = TestObject.createTestObject(suite.getParameter("testKey"));
            currentTest.getExecutionForTest();
            currentTest.markTestStatusTo(TestStatus.iStatus_WIP);
            logger.info("TEST STATUS : WIP");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onFinish(ISuite suite) {
        logger.info("ON SUITE END : MARK TEST FOR FINISH");
        logger.info("Is test Fail : " + currentTest.isTestFail);
        if (currentTest.isTestFail == true) {
            try {
                currentTest.markTestStatusTo(TestStatus.iStatus_FAIL);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            logger.info("TEST STATUS : FAIL");

        } else if (currentTest.isTestFail == false) {
            try {
                currentTest.markTestStatusTo(TestStatus.iStatus_PASS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            logger.info("TEST STATUS : PASS");
        }
        try {
            ConnectionManager.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void onTestStart(ITestResult result) {
        logger.info("CUSTOM LISTENER : onTestStart \nFOR : " + result.getName());

    }

    public void onTestSuccess(ITestResult result) {
        /* TODO : Execute test step as passed
         * TODO : Attach test data in comments if any
         * TODO : Indicate in logger
         */
    }

    public void onTestFailure(ITestResult result) {
        currentTest.isTestFail = true;
        /* TODO : Execute test step as failed
         * TODO : Attach test data in comments if any
         * TODO : Indicate in logger
         */
    }

    public void onTestSkipped(ITestResult result) {
        /* TODO : Execute test step as skipped / unexecuted
         * TODO : Attach test data in comments if any
         * TODO : Indicate in logger
         */
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
        logger.info(context.getSuite().getParameter("testKey"));
//        context.get

        /* TODO : Mark test step as WIP
         * TODO : Indicate in logger
         * */

    }

    public void onFinish(ITestContext context) {
        if (context.getFailedTests() != null && context.getFailedTests().size() != 0) {
            TestObject.getInstance().isTestFail = true;
            logger.info("Marked Test as FAILING");
        }
    }


    private void printTestResults(ITestResult result) {
        System.out.println("Test Method resides in "
                + result.getTestClass().getName());
        if (result.getParameters().length != 0) {
            String params = null;
            for (Object parameter : result.getParameters()) {
                params += parameter.toString() + ",";
            }
            System.out.println("Test Method had the following parameters : "
                    + params);
        }
        String status = null;
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                status = "Pass";
                break;
            case ITestResult.FAILURE:
                status = "Failed";
                break;
            case ITestResult.SKIP:
                status = "Skipped";
        }
        System.out.println("Test Status: " + status);
    }
}
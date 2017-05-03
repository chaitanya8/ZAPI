package zapi;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import zapi.DOM.ExecStatus.TestStatus;
import zapi.DOM.TestObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by Chaitanya on 9-Jul-15.
 */
public class CustomAssert extends Assertion {
    public static final Logger logger = Logger.getLogger(CustomAssert.class.getName());
    @Override
    public void onAssertSuccess(IAssert assertCommand) {
        super.onAssertSuccess(assertCommand);
        // get step number and mark it as passed
        try {
            TestObject.getInstance().markCurrentStepAs(TestStatus.iStatus_PASS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAssertFailure(IAssert assertCommand, AssertionError ex) {
        super.onAssertFailure(assertCommand, ex);
        // get step number and mark it as passed
        try {
            TestObject.getInstance().markCurrentStepAs(TestStatus.iStatus_FAIL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
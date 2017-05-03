package zapi.DOM.ExecStatus;


/**
 * Created by Chaitanya on 18-Jul-15.
 */
public class TestStatus {
    public static final int iStatus_PASS = 1;
    public static final int iStatus_FAIL = 2;
    public static final int iStatus_WIP = 3;
    public static final int iStatus_BLOCKED = 4;
    public static final int iStatus_UNEXECUTED = -1;

    public static final String sStatus_PASS = String.valueOf(iStatus_PASS);
    public static final String sStatus_FAIL = String.valueOf(iStatus_FAIL);
    public static final String sStatus_WIP = String.valueOf(iStatus_WIP);
    public static final String sStatus_BLOCKED = String.valueOf(iStatus_BLOCKED);
    public static final String sStatus_UNEXECUTED_ = String.valueOf(iStatus_UNEXECUTED);;

    /*String label;
    int id;

    public TestStatus(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public static final TestStatus PASS = new TestStatus("PASS", 1);
    public static final TestStatus FAIL = new TestStatus("FAIL", 2);
    public static final TestStatus WIP = new TestStatus("WIP", 3);
    public static final TestStatus BLOCKED = new TestStatus("BLOCKED", 4);
    public static final TestStatus UNEXECUTED = new TestStatus("UNEXECUTED", -1);*/
}

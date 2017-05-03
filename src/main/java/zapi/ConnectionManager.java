package zapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.http.HttpStatus;
import zapi.DOM.ExecStatus.CycleExecution.ExecutionCycleDOM;
import zapi.DOM.ExecStatus.ExecutionStatusDOM;
import zapi.DOM.ExecStatus.StepExecutionList;
import zapi.DOM.ExecStatus.TestExecutionList;
import zapi.DOM.JiraIssue.IssueDOM;
import zapi.DOM.Projects.ProjectOptionsDOM;
import zapi.DOM.RequestDOM.StepResultUpdateDOM;
import zapi.DOM.RequestDOM.TestExecution;
import zapi.DOM.TestCycles.TestCycleDeserializer;
import zapi.DOM.TestCycles.TestCycleList;
import zapi.DOM.TestObject;
import zapi.DOM.TestSteps.StepExecutionStatusDOM;
import zapi.DOM.TestSteps.StepExecutionStatusListDOM;
import zapi.DOM.TestStepsDOM;
import zapi.DOM.Versions.VersionsList;
import zapi.Utils.ConnectionUtils;
import zapi.Utils.PropertiesParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;


/**
 * Created by Chaitanya on 23-May-15.
 */
public class ConnectionManager {
    static final String projectListUrl = "/rest/zapi/latest/util/project-list";
    static final String testCycleUrl = "/rest/zapi/latest/cycle";
    static final String testStepUrl = "/rest/zapi/latest/teststep";
    static final String versionsUrl = "/rest/zapi/latest/util/versionBoard-list";

    static final String stepExecStatusUrl = "/rest/zapi/latest/util/teststepExecutionStatus";
    static final String testExecStatusUrl = "/rest/zapi/latest/util/testExecutionStatus";
    static final String fetchIssueUrl = "/rest/api/2/issue/";

    static final String addTestToCycleUrl = "/rest/zapi/latest/execution/addTestsToCycle";
    static final String changeTestExecutionUrl = "/rest/zapi/latest/execution/";
    static final String changeTestStepExecutionUrl = "/rest/zapi/latest/stepResult/";
    static final String getTestExecutionsUrl = "/rest/zapi/latest/execution/";
    static final String getTestStepExecutionIdUrl = "/rest/zapi/latest/stepResult";
    static final String initiateTestAfterExecUrl = "/secure/ExecuteTest!default.jspa";

    private static ConnectionManager connectionManager = new ConnectionManager();
    private static Gson gson = new Gson();
    private static URIBuilder uriBuilder = new URIBuilder();
    private GsonBuilder builder;
    static CloseableHttpClient client;
    static Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    ConnectionManager() {
        client = HttpClients.createDefault();
    }

    public static ConnectionManager getInstance() {
        return connectionManager;
    }

    public static void refreshSession() throws IOException {
        closeConnection();
        client = HttpClients.createDefault();
    }

    public static void closeConnection() throws IOException {
        uriBuilder = null;
        uriBuilder = new URIBuilder();
        client.close();
    }

    public static Gson getGson() {
        return gson;
    }

    public static HttpEntity makeRequest(HttpGet get) throws IOException {
        HttpResponse httpResponse = client.execute(get);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
            return httpResponse.getEntity();
        } else {
            logger.severe("RESPONSE CODE : " + httpResponse.getStatusLine().getStatusCode()
                    + "\nREASON : " + httpResponse.getStatusLine().getReasonPhrase());
            return null;
        }
    }

    public static HttpEntity makeRequest(HttpPost post) throws IOException {
        HttpResponse httpResponse = client.execute(post);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
            return httpResponse.getEntity();
        } else {
            logger.severe("RESPONSE CODE : " + httpResponse.getStatusLine().getStatusCode()
                    + "\nREASON : " + httpResponse.getStatusLine().getReasonPhrase());
            return null;
        }
    }
    public static HttpEntity makeRequest(HttpPut put) throws IOException {
        HttpResponse httpResponse = client.execute(put);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
            return httpResponse.getEntity();
        } else {
            logger.severe("RESPONSE CODE : " + httpResponse.getStatusLine().getStatusCode()
                    + "\nREASON : " + httpResponse.getStatusLine().getReasonPhrase());
            return null;
        }
    }

    public static HttpGet setGenericParams(HttpGet httpGet) {
        httpGet.setHeader("Authorization", ConnectionUtils.getEncodedAuthKey());
        httpGet.setHeader("Content-Type", "application/json");
        return httpGet;
    }
    public static HttpPost setGenericParams(HttpPost httpPost) {
        httpPost.setHeader("Authorization", ConnectionUtils.getEncodedAuthKey());
        httpPost.setHeader("Content-Type", "application/json");
        return httpPost;
    }
    public static HttpPut setGenericParams(HttpPut httpPut) {
        httpPut.setHeader("Authorization", ConnectionUtils.getEncodedAuthKey());
        httpPut.setHeader("Content-Type", "application/json");
        return httpPut;
    }

    // gets list of projects on Jira
    public static ProjectOptionsDOM getProjectsList() throws IOException, URISyntaxException {
        URI uri = uriBuilder.clearParameters()
                .setScheme("http")
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(projectListUrl)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        JsonElement element = new JsonParser().parse(EntityUtils.toString(httpEntity));
        logger.info(element.toString());
        ProjectOptionsDOM retrievedOptions = null;
        if (element.isJsonObject()) {
            logger.info(String.valueOf(element.isJsonObject()));
            retrievedOptions = gson.fromJson(element.getAsJsonObject(), ProjectOptionsDOM.class);
        }
        EntityUtils.consume(httpEntity);
        return retrievedOptions;
        /*List<ProjectDOM> list = retreivedOptions.getOptions();
        logger.info(String.valueOf(list.size()));
        return list;*/
    }

    // returns test cycles for given project and version
    public static TestCycleList getTestCycles(long projectId, long versionId) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(testCycleUrl)
                .setParameter("projectId", String.valueOf(projectId))
                .setParameter("versionId", String.valueOf(versionId))
                .setParameter("offset", "0")
                .setParameter("expand", "executionSummaries")
                .build();
        HttpGet httpGet = new HttpGet(uri); // TODO surround with try catch block
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info(json);
        String modifiedJson = json.replaceAll("[,][\"][r][e][c][o][r][d][s][C][o][u][n][t][\"][:][\\d]", "");
        logger.info(modifiedJson);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TestCycleList.class, new TestCycleDeserializer());
        Gson gson = gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
        TestCycleList testCycleList = gson.fromJson(modifiedJson, TestCycleList.class);
        EntityUtils.consume(httpEntity);
        return testCycleList;
    }

    // get Steps included in a Test document
    public static TestStepsDOM[] getTestSteps(long testKey) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(testStepUrl).build();
        HttpGet httpGet = new HttpGet(uri + "/" + testKey); // TODO surround with try catch block
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info("TEST STEPS JSON : " + json);
        TestStepsDOM[] array = gson.fromJson(json, TestStepsDOM[].class);
        EntityUtils.consume(httpEntity);
        /*for (TestStepsDOM x : array) {
            logger.info("ID : " + x.getId());
            logger.info("ORDER : " + x.getOrderId());
            logger.info("STEP : " + x.getStep());
            logger.info("RESULT : " + x.getResult());
        }*/
        return array;
    }

    // get Versions List
    public static VersionsList getProjectVersions(long projectId) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(versionsUrl)
                .setParameter("projectId", String.valueOf(projectId))
                .build();
        HttpGet httpGet = new HttpGet(uri); // TODO surround with try catch block
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info(json);
        VersionsList list = gson.fromJson(json, VersionsList.class);
        EntityUtils.consume(httpEntity);
        return list;
    }

    // gets list of Status codes supported for the Steps in a Test
    public static StepExecutionList getStepExecutionStatusSupported() throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(stepExecStatusUrl)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info(json);
        ExecutionStatusDOM[] stepStatusList = gson.fromJson(json, ExecutionStatusDOM[].class);
        StepExecutionList stepExecutionList = new StepExecutionList();
        stepExecutionList.setList(stepStatusList);
        EntityUtils.consume(httpEntity);
        return stepExecutionList;
    }

    // gets list of Status codes supported for the Test as a whole
    public static TestExecutionList getTestExecutionStatusSupported() throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(testExecStatusUrl)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        logger.info(httpGet.getURI().toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info(json);
        ExecutionStatusDOM[] testStatusList = gson.fromJson(json, ExecutionStatusDOM[].class);
        TestExecutionList testExecutionList = new TestExecutionList();
        testExecutionList.setList(testStatusList);
        EntityUtils.consume(httpEntity);
        return testExecutionList;
    }

    // gets basic Issue info
    public static IssueDOM getIssueId(String key) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(fetchIssueUrl + key)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info("ISSUE JSON : " + json);
        if (json.contains("errorMessages")) {
            logger.severe("No such issue exists");
            return null;
        }
        IssueDOM issue = gson.fromJson(json, IssueDOM.class);
        EntityUtils.consume(httpEntity);
        return issue;
    }

    // gets the
    public static boolean isIssueValidTest(String key) throws URISyntaxException, IOException {
        boolean isValid = true;                         // default initialization to true
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(fetchIssueUrl + key)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info("ISSUE VALID JSON : " + json);
        if (json.contains("errorMessages")) {
            logger.severe("No such issue exists");
            isValid = false;
        }
        EntityUtils.consume(httpEntity);
        return isValid;
    }

    // change test Status to PASS,FAIL,WIP, etc.
    public static void changeTestStatus(long testExecutionId, int statusCode) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(changeTestExecutionUrl + testExecutionId + "/quickExecute")
                .build();
        HttpPost httpPost = new HttpPost(uri);
        setGenericParams(httpPost);
        String json = "{\"status\":" + Integer.toString(statusCode) + "}";
        logger.info(json);
        httpPost.setEntity(new StringEntity(json));
        logger.info(httpPost.toString());
//        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity httpEntity = makeRequest(httpPost);
        InputStream is = httpEntity.getContent();
        is.close();
        EntityUtils.consume(httpEntity);
    }

    // add a test to a particular test cycle
    public static void addTestToTestCycle(String issue, long versionId, long projectId, String cycleId)
            throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(addTestToCycleUrl)
                .build();
        HttpPost httpPost = new HttpPost(uri);
        logger.info(httpPost.toString());
        setGenericParams(httpPost);
        TestExecution testExecution = new TestExecution(new String[]{issue}, versionId, cycleId, Long.toString(projectId), "1");
        String json = testExecution.toString();
        logger.info(json);
        httpPost.setEntity(new StringEntity(json));
        HttpEntity httpEntity = makeRequest(httpPost);
        logger.info(httpEntity.toString());
        EntityUtils.consume(httpEntity);
    }

    //  gets All executions for a test
    public static ExecutionCycleDOM getTestExecutionForIssue(long testId) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(getTestExecutionsUrl)
                .setParameter("issueId", Long.toString(testId))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        HttpEntity httpEntity = makeRequest(httpGet);
        String json = EntityUtils.toString(httpEntity);
        logger.info(json);
        ExecutionCycleDOM executionCycleDOM = gson.fromJson(json,ExecutionCycleDOM.class);
        EntityUtils.consume(httpEntity);
        return executionCycleDOM;
    }

    // change step status via quick execute method
    public void changeStepStatus(int stepId, int status) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(changeTestStepExecutionUrl + stepId + "/quickExecute")
                .build();
        HttpPost httpPost = new HttpPost(uri);
        setGenericParams(httpPost);
        String json = "{\"status\":" + Integer.toString(status) + "}";
        httpPost.setEntity(new StringEntity(json));
        logger.info(httpPost.toString());
        HttpEntity httpEntity = makeRequest(httpPost);
        logger.info(httpEntity.toString());
        EntityUtils.consume(httpEntity);
    }

    // change step status via a detailed execute method
    public static void changeStepStatus(long id, String issueId, long stepExecutionId, String status) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(changeTestStepExecutionUrl + id)
                .build();
        HttpPut httpPut = new HttpPut(uri);
        setGenericParams(httpPut);
        logger.info(httpPut.toString());
        StepResultUpdateDOM requestBody = new StepResultUpdateDOM(id, issueId, stepExecutionId, status);
        logger.info(requestBody.toString());
        httpPut.setEntity(new StringEntity(requestBody.toString()));
        HttpEntity entity =  makeRequest(httpPut);
        EntityUtils.consume(entity);
    }

    // gets unique identifier step to execution id
    public static StepExecutionStatusListDOM getStepExecutionForIssue(long executionId) throws URISyntaxException, IOException {
        URI uri = uriBuilder.setScheme("http")
                .clearParameters()
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(getTestStepExecutionIdUrl)
                .setParameter("executionId", String.valueOf(executionId))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        setGenericParams(httpGet);
        logger.info(httpGet.toString());
        HttpEntity httpEntity = makeRequest(httpGet);
        String response = EntityUtils.toString(httpEntity);
        logger.info(response);
        StepExecutionStatusDOM[] list = gson.fromJson(response, StepExecutionStatusDOM[].class);
        StepExecutionStatusListDOM stepList = new StepExecutionStatusListDOM(list);
        EntityUtils.consume(httpEntity);
        return stepList;
    }

    public static void initiateTestStepsAfterExecution(long stepId, long executionId) throws URISyntaxException, IOException {
        URI uri = uriBuilder.clearParameters()
                .setScheme("http")
                .setHost(PropertiesParser.getJiraHost())
                .setPort(PropertiesParser.getJiraPort())
                .setPath(changeTestStepExecutionUrl)
                .build();
        String body = "{\"stepId\" : " + stepId + ",\"executionId\" : " + executionId + " } ";
        HttpPost httpPost = new HttpPost(uri);
        setGenericParams(httpPost);
        httpPost.setEntity(new StringEntity(body));
        logger.info(httpPost.toString());
        HttpEntity httpEntity = makeRequest(httpPost);
        EntityUtils.consume(httpEntity);
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        /*long projectId = getProjectsList().getProjectIdForProjectName("TEST");
        long versionId = getProjectVersions(projectId).findUnreleasedProjectVersion("2015.09.03").getValue();
        String cycleId = Integer.toString(getTestCycles(projectId, versionId).getTestCycleIdFromName("Automated"));
        logger.info("PROJECT : " + projectId + "\tVERSION : " + versionId + "\tCYCLE : " + cycleId);
        String issues = "TEST-1";
        addTestToTestCycle(issues, versionId, projectId, cycleId);*/
//        getTestExecutionForIssue(10000);
//        changeStepStatus(9, String.valueOf(10000), 8, String.valueOf(TestStatus.sStatus_PASS));

        /*StepExecutionStatusListDOM list = getStepExecutionForIssue(8);
        logger.info(String.valueOf(list.getExecutionIdForEachTestStep(2).getId()));*/
//        TestCycleList l = getTestCycles(10000,10000);
//        logger.info(String.valueOf(l.getTestCycleIdFromName("Automated")));
//        closeConnection();

        /*TestStepsDOM [] steps = ConnectionManager.getTestSteps(10000);
        for(TestStepsDOM x : steps){
            logger.info(x.toString());
        }*/
        TestObject.createTestObject("TEST-1");
        TestObject.getInstance().getExecutionForTest();
//        TestObject.getInstance().executeTestUnderCycle();
        for (TestStepsDOM x : TestObject.getInstance().testSteps) {
            logger.info(x.toString());
        }
//        TestObject.getInstance().markCurrentStepAs(TestStatus.iStatus_WIP);

    }
}
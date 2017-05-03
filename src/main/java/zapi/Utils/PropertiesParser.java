package zapi.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Chaitanya on 31-May-15.
 */
public class PropertiesParser {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String JIRA_URL = "jira.url";
    public static final String JIRA_PORT = "jira.port";
    public static final String JIRA_HOST = "jira.host";

    static Logger logger = Logger.getLogger(PropertiesParser.class.getName());
    static Properties properties;
    static InputStream inputStream;

    private static PropertiesParser propertiesParser = new PropertiesParser();

    public static PropertiesParser getInstance() {
        return propertiesParser;
    }

    PropertiesParser() {
        try {
            // TODO : Remove hard coding
            inputStream = new FileInputStream("D:\\DevEnv\\workspace\\ZAPI\\src\\main\\resources\\default.properties");
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.severe("Properties file not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //inputStream = new FileInputStream("C:\\Users\\Chaitanya\\Desktop\\ZAPI\\src\\main\\resources\\default.properties");
    }

    public static String getProperty(String propertyName) {
        return PropertiesParser.getInstance().properties.getProperty(propertyName);
    }

    public static String getUsername() {
        return PropertiesParser.getProperty(USERNAME);
    }

    public static String getPassword() {
        return PropertiesParser.getProperty(PASSWORD);
    }

    public static String getJiraUrl() {
        return PropertiesParser.getProperty(JIRA_URL);
    }

    public static int getJiraPort() {
        return Integer.parseInt(PropertiesParser.getProperty(JIRA_PORT));
    }

    public static String getJiraHost() {
        return PropertiesParser.getProperty(JIRA_HOST);
    }

    public static String getUsernameFromTerminal() {
        return System.getProperty("jiraUsername");
    }

    public static String getPasswordFromTerminal() {
        return System.getProperty("jiraPassword");
    }

    public static String getVersionFromTerminal() {
        return System.getProperty("releaseVersion");
    }
}

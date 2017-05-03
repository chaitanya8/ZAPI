package sample;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import zapi.CustomAssert;
import zapi.Utils.PropertiesParser;

import java.util.logging.Logger;

/**
 * Created by Chaitanya on 9-Jun-15.
 */
public class myTest extends BaseTest {
    static Logger logger = Logger.getLogger(myTest.class.getName());

    @Test(testName = "TEST_STEP_1")
    @Parameters("testKey")
    public void myTestExample1(String testKey) {
        logger.info("Test Key : " + testKey);
        logger.info("USERNAME : " + PropertiesParser.getUsernameFromTerminal());
        driver.manage().deleteAllCookies();
        driver.get("https://www.care.com");
        driver.findElement(By.id("loginLink")).click();
        driver.findElement(By.id("emailId")).sendKeys("dkrupinski@gmail.com");
        driver.findElement(By.name("password")).sendKeys("letmein1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        boolean isLoginSuccessful = false;
        if (driver.findElements(By.linkText("Log Out")).size() > 0) {
            isLoginSuccessful = true;
        }
        CustomAssert myAssert = new CustomAssert();
        myAssert.assertEquals(isLoginSuccessful, true, "FAILED LOGIN STEP 1");
    }

    @Test(testName = "TEST_STEP_2")
    @Parameters("testKey")
    public void myTestExample2(String testKey) {
        logger.info("Test Key : " + testKey);
        driver.manage().deleteAllCookies();
        driver.get("https://www.bing.com");
        CustomAssert myAssert = new CustomAssert();
        myAssert.assertEquals(1, 1, "FAILED STEP 2");
    }

    @AfterTest
    public void myAfterTest() {
        driver.close();
    }
}
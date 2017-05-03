package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * Created by Chaitanya on 12-Jul-15.
 */
public class BaseTest {
    public static WebDriver driver;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "timeout", "testKey"})
    public void initBrowser() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void destroyBrowser() {
        driver.close();
    }
}

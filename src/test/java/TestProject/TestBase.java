package TestProject;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import sun.nio.cs.UTF_32;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class TestBase {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void getDriver() throws IOException {
        Properties prop = new Properties();
        File file = new File("//D:/MavenProject/TestProject/src/test/java/resources/properties.properties");
        InputStream inputStream = new FileInputStream(file);
        prop.load(inputStream);
        String browser = prop.get("browser").toString(); //
        if(browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "//D:/MavenProject/TestProject/drivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome();
            handlSSLErr.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            handlSSLErr.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(handlSSLErr);
        } else if(browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "//D:/MavenProject/TestProject/drivers/geckodriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            //capabilities.acceptInsecureCerts();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            driver = new FirefoxDriver(capabilities);

        }else if(browser.equals("IE")){

            File IEfile = new File("D:/MavenProject/TestProject/drivers/IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", IEfile.getAbsolutePath());
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            System.setProperty("webdriver.ie.driver","IEDriverServer.exe");
            driver = new InternetExplorerDriver(capabilities);
            driver.manage().window().maximize();
        }else{
           System.out.println("Select driver");
        }
        driver.manage().window().maximize();// сначало просто окно а потом увеличится
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String filePath = "D:/MavenProject/TestProject/log.log";
        FileUtils.write(new File(filePath), "", "UTF_32");
    }



    public void Verify(String actual, String expected, int step, String description) throws Exception {
        boolean verifyFlag;
        try {
            Assert.assertEquals(actual, expected);
            verifyFlag = true;
        } catch (AssertionError e) {
            System.out.println(e);
            verifyFlag = false;
            //takeSnapShot(driver);
        }
            CustomLogger.logging(driver, verifyFlag, step, description);


    }

    /*public static void takeSnapShot(WebDriver driver) throws Exception{
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
        File dirScreenshots = new File(currentDir+ "\\screenshots");
        if (!dirScreenshots.exists()) {
            try{
                dirScreenshots.mkdir();
            } catch(SecurityException e){
                System.out.println(e);
            }
        }
        File testResult= new File(dirScreenshots + "//" + timeStamp);
        if (!testResult.exists()) {
            try{
                testResult.mkdir();
            } catch(SecurityException e){
                System.out.println(e);
            }
        }
       try {
            FileUtils.copyFile(scrFile, new File( testResult+ "//result.png"));
       } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("Screenshot is done");
    }
    */

    @AfterMethod
   public void endTest(){driver.quit();
   }
}

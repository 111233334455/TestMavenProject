package TestProject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;


public class TestClass extends TestBase {
    JavascriptExecutor js;

   /* @Test
    public void test() throws InterruptedException {
        driver.get("http://google.com");//for IE
        String mainWindow = driver.getWindowHandle();
        System.out.println(mainWindow);

        js = (JavascriptExecutor) driver;
        js.executeScript("window.open('https://news.com','_blank');");
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        Thread.sleep(3000);
        driver.switchTo().window(tabs2.get(1));
        Thread.sleep(4000);
        String secondWindow = driver.getWindowHandle();
        System.out.println(secondWindow);
        driver.close();
        driver.switchTo().window(mainWindow);
        switchWindow();
        driver.findElement(By.id("lst-ib")).sendKeys("111");
        driver.findElement(By.name("btnK")).click();
        Thread.sleep(5000);
//todo for git
    }

    @Test
    public void deleteElementFromThePage() throws InterruptedException {
        driver.navigate().to("http://google.com");
        WebElement element = driver.findElement(By.className("sfibbbc"));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'display: none;')",element);
        Thread.sleep(4000);
    }*/

    @Test
    public void testlogger() throws Exception {
        driver.get("http://google.com");
        CustomLogger.logger.info("message");
        WebElement element = driver.findElement(By.id("hplogo"));
        String actual = element.getText();
        Verify(actual, "Україна", 1, "Message");
        Verify(actual, "Україна1", 2, "Message");
        Verify(actual, "Україна", 3, "Message");
    }

    public void switchWindow() throws InterruptedException {

        String currentWindow = driver.getWindowHandle();

        System.out.println("Current window handler is : " + currentWindow);

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equalsIgnoreCase(currentWindow)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to window handler : " + handle);
                break;
            }
        }
    }

}
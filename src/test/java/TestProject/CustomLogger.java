package TestProject;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomLogger {
    final static Logger logger = Logger.getLogger(CustomLogger.class);



    public static void logging(WebDriver driver, Boolean verifyFlag, int step, String description) throws Exception {
        PropertyConfigurator.configure("log4j.properties");
        //BasicConfigurator.configure();
        //org.apache.log4j.PropertyConfigurator.configure("D://MavenProject//TestProject//log4j.properties");
        // System.setProperty("org.apache.commons.logging.Log",
        // "org.apache.commons.logging.impl.Jdk14Logger");
        //DOMConfigurator.configure("log4j.xml");

        //String passFailText = (pass ? "PASS" : "FAIL");

        String filePath = "D:/MavenProject/TestProject/log.log";
        if (verifyFlag) {
            logger.info(step + " " + description + "- Passed");
        } else {
            logger.info(step + " " + description + "- Fail ");
            takeSnapShot(driver);
        }
        File resultDir = createDir();
        File source = new File(filePath);
        File dest = new File(resultDir + "//log.log");
        FileUtils.copyFile(source, dest);
    }


    public static void takeSnapShot(WebDriver driver) throws Exception{
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File resutlDir= createDir();
        try {
            FileUtils.copyFile(scrFile, new File( resutlDir + "//result.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot is done");
    }

    public static File createDir(){
        String currentDir = System.getProperty("user.dir");
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(Calendar.getInstance().getTime());
        File testResult = new File(currentDir + "//Results");
        if (!testResult.exists()) {
            try {
                testResult.mkdir();
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }
        File currentTestResult = new File(testResult + "//" + timeStamp);
        if (!currentTestResult.exists()) {
            try {
                currentTestResult.mkdir();
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }return currentTestResult;
    }

    public static boolean deleteFile(String path) {
        try {
            File file = new File(path);
            if(file.exists()){
            file.delete();
            }
            return true;
        } catch (Exception x) {
            System.err.println(x);
        }return  false;
    }

    public static void writeToFile(String fileName)
            throws IOException {
        String str = "";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(str);

        writer.close();
    }
}


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Author: Shahriar Rahman
Date: 03-December-2023
*/
public class HelperMethodsSelenium {

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void typeIntoInputField(WebDriverWait wait, WebDriver driver, String xpath, String text) {
        // Type the specified text into the input field
        WebElement element__of_XML = waitForElementVisible(wait, driver, xpath);

        element__of_XML.sendKeys(text);
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static WebElement waitForElementVisible(WebDriverWait wait, WebDriver driver, String xpath) {
        WebElement element__of_XML = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return element__of_XML;
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void skippableClick(WebDriverWait wait, WebDriver driver, String xpath, int timeoutSeconds) {
        try {
            WebElement element__of_XML = wait
                    .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            if (element__of_XML != null) {
                element__of_XML.click();
            } else {
                System.out.println("Element not found within the timeout. Skipping click.");
            }
        } catch (TimeoutException e) {
            System.out.println("Element not found within the specified timeout. Skipping click.");
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void waiter(int time_durn){
        try {
            Thread.sleep(time_durn);  // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
Author: Shahriar Rahman
Date: 03-December-2023
*/
    public static boolean isDisplayed(WebElement element__of_XML) {
        try {
            if(element__of_XML.isDisplayed())
                return element__of_XML.isDisplayed();
        }catch (NoSuchElementException ex) {
            return false;
        }
        return false;
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    public static boolean isEnabled(WebElement element__of_XML) {
        try {
            return element__of_XML.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

}

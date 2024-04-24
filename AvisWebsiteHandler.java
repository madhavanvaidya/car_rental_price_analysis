
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


/*
Author: Shahriar Rahman
Date: 03-December-2023
*/
public class AvisWebsiteHandler {
    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    public static void processAvisWebsite(WebDriver driver) {
        driver.get("https://www.avis.ca/en/reservation#/time-and-place");
        try {
            HelperMethodsSelenium.waiter(5000);

            WebDriverWait wait = new WebDriverWait(driver, 30);

            String decline__Offer_01 = "/html/body/div[16]/div[3]/div/div/div/div[2]/form/div[3]/div[4]/button";

            try{
                WebElement element_01 = driver.findElement(By.xpath(decline__Offer_01));
                if(HelperMethodsSelenium.isDisplayed(element_01) && HelperMethodsSelenium.isEnabled(element_01)){
                    element_01.click(); // here i want if  element_01.click(); command
                }
            }
            catch(Exception e){
                System.out.print("An unexpected error occurred");
            }

            HelperMethodsSelenium.waiter(2000);

            String returnDate = "//*[@id=\"to\"]";
            String future_DateString_01 = HelperMethodsTime.getFutureDateString(30);

            String return_Date_Value_01 = future_DateString_01;
            WebElement return_DateElement__01 = driver.findElement(By.xpath(returnDate));
            return_DateElement__01.findElement(By.xpath(returnDate)).clear();
            return_DateElement__01.findElement(By.xpath(returnDate)).sendKeys(return_Date_Value_01);

            HelperMethodsSelenium.waiter(2000);

            String location_to_pickUp__01 = "//*[@id=\"PicLoc_value\"]";
            String pick_UpLocationVal__01 = "Windsor Airport, Windsor, Ontario, Canada-(YQG)";
            WebElement pick_Up_Location_Elements_1 = driver.findElement(By.xpath(location_to_pickUp__01));
            pick_Up_Location_Elements_1.findElement(By.xpath(location_to_pickUp__01)).sendKeys(pick_UpLocationVal__01);
            HelperMethodsSelenium.waiter(1000);
            String pick__UpOption__01 = "/html/body/div[5]/div[2]/div[1]/div/div/div/div[2]/section/div[2]/div/div[1]/form/div[1]/div[2]/div[7]/div[1]/div[1]/div[1]/angucomplete-alt/div/div[2]/div[3]/div[1]/div[2]";
            WebElement pickUpOptionElement = driver.findElement(By.xpath(pick__UpOption__01));
            pickUpOptionElement.click();

            HelperMethodsSelenium.waiter(2000);

            String select_My_Car_Btn = "//*[@id=\"res-home-select-car\"]";
            WebElement selectMyCarButtonElement = driver.findElement(By.xpath(select_My_Car_Btn));
            selectMyCarButtonElement.click();

            HelperMethodsSelenium.waiter(2000);

            String dontWantDiscountButton = "//*[@id=\"bx-element_01-1526913-RKSShaY\"]/button";
            HelperMethodsSelenium.skippableClick(wait, driver, dontWantDiscountButton, 20);

            List<WebElement> div_Tag_Elements = driver.findElements(By.xpath("/html/body/div[5]/div[2]/div[1]/div/div/div[2]/div[1]/section[3]/div[1]"));

            HelperMethodsAvis.processCarDataToExcel(div_Tag_Elements);

        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException("Error occurred during web automation. Retrying...");
        }
    }
}

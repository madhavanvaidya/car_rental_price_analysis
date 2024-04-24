
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/*
Author: Shahriar Rahman
Date: 03-December-2023
*/

public class EnterpriseWebsiteHandler {
    /*
Author: Shahriar Rahman
Date: 03-December-2023
*/
    public static void processEnterpriseWebsite(WebDriver driver) {
        //  Opening the enterprise website for scraping
        driver.get("https://www.enterprise.ca/en/home.html");
        try {
            // Initializing init__wait
            WebDriverWait init__wait = new WebDriverWait(driver, 20);

            String pick_Up_Location_Input_001 = "//*[@id=\"pickupLocationTextBox\"]";
            String pick_UpLocation_Val_001 = "Windsor airport";
            HelperMethodsSelenium.typeIntoInputField(init__wait, driver, pick_Up_Location_Input_001, pick_UpLocation_Val_001);

            HelperMethodsSelenium.waiter(3000);

            JavascriptExecutor jse_001 = (JavascriptExecutor)driver;
            jse_001.executeScript("window.scrollBy(0,250)");

            String selecting_Location_Opt_001 = "//*[@id=\"location-1039532\"]";
            WebElement select_Location_Option_Btn_001 = HelperMethodsSelenium.waitForElementVisible(init__wait, driver, selecting_Location_Opt_001);
            select_Location_Option_Btn_001.click();

            HelperMethodsSelenium.waiter(2000);

            String search = "//*[@id=\"continueButton\"]";
            WebElement search_Btn_001 = HelperMethodsSelenium.waitForElementVisible(init__wait, driver, search);
            search_Btn_001.click();

            HelperMethodsSelenium.waiter(5000);

            WebElement olElement_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul"));

            // Locating all  {li} elements 
            List<WebElement> li_Elements_01 = olElement_01.findElements(By.tagName("li"));

            // Iterating over each {li} element 
            int a = 1;
            for (WebElement liElement : li_Elements_01) {
                try {
                    String excelFilePath = "CarRentalData.xls";
                    FileInputStream fileInputStream = new FileInputStream(excelFilePath);
                    Workbook workbook2_for_XML = new HSSFWorkbook(fileInputStream);

                    Sheet sheet2 = workbook2_for_XML.getSheetAt(0);

                    Row newRow_XML = sheet2.createRow(sheet2.getLastRowNum() + 1);

                    WebElement modelEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/section/div/h2"));
                    String model = modelEll_01.getText().trim();

                    WebElement typeEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/section/div/p[2]"));
                    String carType = typeEll_01.getText().trim();

                    WebElement priceEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/div[2]/div/div/div[1]/div/div/span/span[2]"));
                    String cost = priceEll_01.getText().trim();

                    WebElement passengerEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/section/div/ul/li[2]"));
                    String passengers = passengerEll_01.getText().trim();

                    WebElement transmissionEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/section/div/ul/li[1]"));
                    String transmission = transmissionEll_01.getText().trim();

                    WebElement luggageEll_01 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul/li[" + a + "]/div/div[1]/section/div/ul/li[3]"));
                    String luggage = luggageEll_01.getText().trim();

                    System.out.println(model + " " +carType+ " " +cost+ " " +passengers+ " " +transmission+ " " +luggage);

                    Cell cell_00 = newRow_XML.createCell(0);
                    cell_00.setCellValue(model);
                    Cell cell_01 = newRow_XML.createCell(1);
                    cell_01.setCellValue(carType.replace("or", "").replace("similar", "").replace("- Vehicle determined upon pick-up", ""));
                    Cell cell_02 = newRow_XML.createCell(2);
                    cell_02.setCellValue(passengers.replace("People", "").trim());
                    Cell cell_03 = newRow_XML.createCell(3);
                    cell_03.setCellValue(luggage.replace("Bags", "").trim());
                    Cell cell_04 = newRow_XML.createCell(4);
                    cell_04.setCellValue(0);
                    Cell cell_05 = newRow_XML.createCell(5);
                    cell_05.setCellValue(transmission);
                    Cell cell_06 = newRow_XML.createCell(6);
                    cell_06.setCellValue(cost.replaceAll("\\$", ""));
                    Cell cell7 = newRow_XML.createCell(7);
                    cell7.setCellValue("enterprise");

                    Cell cell8 = newRow_XML.createCell(8);
                    cell8.setCellValue(HelperMethodsTime.getCurrentTimestamp());

                    // Save all the changes to the created Excel file
                    FileOutputStream OutputStream_for_XML = new FileOutputStream(excelFilePath);
                    workbook2_for_XML.write(OutputStream_for_XML);
                    OutputStream_for_XML.flush();
                    OutputStream_for_XML.close();
                    workbook2_for_XML.close();
                } catch (Exception ignored){
                }
                a++;
            }

        } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error occurred during web automation. Retrying...");
    }
    }
}

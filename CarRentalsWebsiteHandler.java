
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
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
public class CarRentalsWebsiteHandler {
    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    public static void processCarRentalsWebsite(WebDriver driver) {
        // region Opening the carrentals website
        driver.get("https://www.carrentals.com/");
        try {
            // Initializing explicit init_wait
            WebDriverWait init_wait = new WebDriverWait(driver, 20);

            String pick_Up_Location = "//*[@id=\"wizard-car-pwa-1\"]/div[1]/div[1]/div/div/div/div/div[2]/div[1]/button";
            WebElement pick_Up_Location_btn = HelperMethodsSelenium.waitForElementVisible(init_wait, driver, pick_Up_Location);
            pick_Up_Location_btn.click();

            String pick_Up_Location_Input = "//*[@id=\"location-field-locn\"]";
            String picking__UpLocation_Value = "Windsor";
            HelperMethodsSelenium.typeIntoInputField(init_wait, driver, pick_Up_Location_Input, picking__UpLocation_Value);

            String selecting_Location_Option_001 = "//*[@id=\"location-field-locn-menu\"]/section/div/div[2]/div/ul/li[1]/div/button";
            WebElement select_Location_Option_Btn_01 = HelperMethodsSelenium.waitForElementVisible(init_wait, driver, selecting_Location_Option_001);
            select_Location_Option_Btn_01.click();

            String search_01 = "//*[@id=\"wizard-car-pwa-1\"]/div[3]/div[2]/button";
            WebElement searchButton = HelperMethodsSelenium.waitForElementVisible(init_wait, driver, search_01);
            searchButton.click();

            HelperMethodsSelenium.waiter(5000);

            WebElement ol__Element = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol"));

            // Locate all { li } elements 
            List<WebElement> liElements__web = ol__Element.findElements(By.tagName("li"));

            // Iterating over each li element and perform actions
            int a = 1;
            for (WebElement liElement : liElements__web) {

                try {
                    String excel_File_Path_01 = "CarRentalData.xls";
                    FileInputStream fileInputStream = new FileInputStream(excel_File_Path_01);
                    Workbook XML_workbook2 = new HSSFWorkbook(fileInputStream);

                    Sheet sheet2__web = XML_workbook2.getSheetAt(0);

                    Row newRow = sheet2__web.createRow(sheet2__web.getLastRowNum() + 1);

                    WebElement model_EEl = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol/li[" + a + "]/div/div/div[2]/div/div[1]/div[1]/h3"));
                    String model = model_EEl.getText().trim();

                    WebElement type__EEl = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol/li[" + a + "]/div/div/div[2]/div/div[1]/div[2]/section[1]/div[1]/div[1]/div"));
                    String carType = type__EEl.getText().trim();

                    WebElement price__EEl = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol/li[" + a + "]/div/div/div[2]/div/div[2]/div[1]/div/section/div[1]/span"));
                    String cost = price__EEl.getText().trim();

                    WebElement passenger__EEl = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol/li[" + a + "]/div/div/div[2]/div/div[1]/div[2]/section[1]/div[1]/div[2]/div/span[1]"));
                    String passengers = passenger__EEl.getText().trim();

                    WebElement transmission__EEl = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/main/div[2]/div[2]/div/div[1]/div[2]/div[2]/div[2]/ol/li[" + a + "]/div/div/div[2]/div/div[1]/div[2]/section[1]/div[1]/div[2]/div/span[3]"));
                    String transmission__001 = transmission__EEl.getText().trim();

                    Cell cell00 = newRow.createCell(0);
                    cell00.setCellValue(model);
                    Cell cell_01 = newRow.createCell(1);
                    cell_01.setCellValue(carType.replace("or", "").replace("similar", "").replace("- Vehicle determined upon pick-up", ""));
                    Cell cell_02 = newRow.createCell(2);
                    cell_02.setCellValue(passengers);
                    Cell cell3 = newRow.createCell(3);
                    cell3.setCellValue(passengers);
                    Cell cell_04 = newRow.createCell(4);
                    cell_04.setCellValue(0);
                    Cell cell5 = newRow.createCell(5);
                    cell5.setCellValue(transmission__001);
                    Cell cell6 = newRow.createCell(6);
                    cell6.setCellValue(cost.replaceAll("\\$", ""));
                    Cell cell_07 = newRow.createCell(7);
                    cell_07.setCellValue("carrentals");

                    Cell cell8 = newRow.createCell(8);
                    cell8.setCellValue(HelperMethodsTime.getCurrentTimestamp());

                    // Save the changes to the Excel file
                    FileOutputStream file_Out_Stream__001 = new FileOutputStream(excel_File_Path_01);
                    XML_workbook2.write(file_Out_Stream__001);
                    file_Out_Stream__001.flush();
                    file_Out_Stream__001.close();
                    XML_workbook2.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
                a++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred during web automation. Retrying...");
        }
    }
}
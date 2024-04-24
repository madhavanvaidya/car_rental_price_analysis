
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Author: Shahriar Rahman
Date: 03-December-2023
*/
public class HelperMethods {

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void typeIntoInputField(WebDriverWait wait, WebDriver driver, String xpath, String text) {
        // Type the specified text into the input field
        WebElement element_01 = waitForElementVisible(wait, driver, xpath);

        element_01.sendKeys(text);
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static WebElement waitForElementVisible(WebDriverWait wait, WebDriver driver, String xpath) {
        WebElement element_01 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return element_01;
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void skippableClick(WebDriverWait wait, WebDriver driver, String xpath, int timeoutSeconds) {
        try {
            WebElement element_01 = wait
                    .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            if (element_01 != null) {
                element_01.click();
            } else {
                System.out.println("Element not found within the specified timeout. Skipping click.");
            }
        } catch (TimeoutException e) {
            System.out.println("Element not found within the specified timeout. Skipping click.");
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void processCarDataToExcel(List<WebElement> divElements) {
        // Creating a workbook_for_XML and a sheet
        System.out.println(divElements.size());
        try (Workbook workbook_for_XML = new HSSFWorkbook()) {
            Sheet sheet = workbook_for_XML.createSheet("CarRentalData");

            // Adding headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Vehicle Type");
            headerRow.createCell(1).setCellValue("Vehicle Model");
            headerRow.createCell(2).setCellValue("Number of Passengers");
            headerRow.createCell(3).setCellValue("Number of Large Bags");
            headerRow.createCell(4).setCellValue("Number of Small Bags");
            headerRow.createCell(5).setCellValue("Transmission");
            headerRow.createCell(6).setCellValue("Cost");
            headerRow.createCell(7).setCellValue("Website");
            headerRow.createCell(8).setCellValue("Time");

            // Adding data to the sheet
            int rowIndex_XML = 1; // Starting from the second row after headers

            // Loop through the div elements
            for (WebElement div_Element_01 : divElements) {

                String[] carDataArray = div_Element_01.getText().split("\n");
                int totalData = (int) Math.round(carDataArray.length / 10.0);
                System.out.println(totalData);

                //--------------------------------
                String Data_to_Input = div_Element_01.getText();

                // Split the input data into lines
                String[] lines = Data_to_Input.split("\n");

                // List to store chunks of 10 lines for each car
                List<String[]> Chunks_of_Cars = new ArrayList<>();

                // Start processing when these words are found
                String[] carStartKeywords = {"Economy", "Compact", "Intermediate", "Standard", "Full-Size", "Intermediate SUV", "Standard SUV", "Luxury"};

                // Index to keep track of the current position in the lines array
                int currentIndex = 0;

                while (currentIndex < lines.length) {
                    if (startsWithAny(lines[currentIndex], carStartKeywords)) {
                        String[] carChunk = new String[10];
                        for (int i = 0; i < 10 && currentIndex < lines.length; i++) {
                            carChunk[i] = lines[currentIndex];
                            currentIndex++;
                        }
                        Chunks_of_Cars.add(carChunk);
                    } else {
                        currentIndex++;
                    }
                }

                for (String[] carChunk : Chunks_of_Cars) {
                    // Assuming carChunk is a String array with at least 6 elements
                    String price__01 = carChunk[5].replace("C$", "").replace("Total", "").replace(",", "").trim();

                    // Check if carChunk[5] contains any substring from carStartKeywords
                    boolean contains_Keyword_01 = Arrays.stream(carStartKeywords).anyMatch(price__01::contains);

                    Row row = sheet.createRow(rowIndex_XML);

                    // Add data to each column
                    row.createCell(0).setCellValue(carChunk[0].trim()); // Vehicle Type
                    row.createCell(1).setCellValue(carChunk[1].replace("or", "").replace("similar", "").replace("- Vehicle determined upon pick-up", "").trim()); // Vehicle Model
                    String[] passengerData = carChunk[2].split(" ");
                    row.createCell(2).setCellValue(passengerData[0].trim()); // Number of Passengers
                    row.createCell(3).setCellValue(passengerData[1].trim()); // Number of Large Bags
                    row.createCell(4).setCellValue(passengerData[2].trim()); // Number of Small Bags
                    row.createCell(5).setCellValue(carChunk[3].replace("Transmission", "").trim()); // Transmission
                    if (contains_Keyword_01) {
                        // If carChunk[5] contains any substring from carStartKeywords, set cost to "40"
                        row.createCell(6).setCellValue("40");
                    } else {
                        // Convert the price__01 to double
                        double priceDouble_01;
                        try {
                            priceDouble_01 = Double.parseDouble(price__01);
                        } catch (Exception e) {
                            // Handle the case where the priceString is not a valid double
                            priceDouble_01 = 1200;
                            e.printStackTrace();
                        }

                        // Divide the price__01 by 30
                        double pricePerDay = priceDouble_01 / 30;

                        // Format the result as a string
                        String resultString_01 = formatDoubleToString(pricePerDay);
                        row.createCell(6).setCellValue(resultString_01); // Cost
                    }
                    row.createCell(7).setCellValue("avis"); // website
                    row.createCell(8).setCellValue(getCurrentTimestamp()); // time

                    rowIndex_XML++;
                }
            }

            // Save the workbook_for_XML to a file
            try (FileOutputStream fileOut_01_XML = new FileOutputStream("CarRentalData.xls")) {
                workbook_for_XML.write(fileOut_01_XML);
                workbook_for_XML.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static void waiter(int duration){
        try {
            Thread.sleep(duration);  // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    public static boolean isDisplayed(WebElement element_01) {
        try {
            if(element_01.isDisplayed())
                return element_01.isDisplayed();
        }catch (NoSuchElementException ex) {
            return false;
        }
        return false;
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    public static boolean isEnabled(WebElement element_01) {
        try {
            return element_01.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    private static boolean startsWithAny(String str, String[] keyWrd) {
        for (String keyword : keyWrd) {
            if (str.startsWith(keyword)) {
                return true;
            }
        }
        return false;
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static String getCurrentTimestamp() {
        // Get current timestamp in a specific format
        SimpleDateFormat date_FormatXML = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return date_FormatXML.format(currentDate);
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static boolean isTimeDifferenceGreaterThanTenMinutes() {
        try {
            // Parsing the timestamp from the last row in the created Excel file
            String lastRowTimestamp = getLastRowTimestamp("CarRentalData.xls");

            // Calculating the time difference
            SimpleDateFormat date_FormatXML = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lastRowDate__01 = date_FormatXML.parse(lastRowTimestamp);
            Date currentDate = new Date();

            long time_Diff__Minutes = TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime() - lastRowDate__01.getTime());

            return time_Diff__Minutes > 10;
        } catch (ParseException | IOException e) {
            System.out.println("An expected error occured");
            return true; //  running the code in case of any parsing issues or IO errors occured
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    private static String getLastRowTimestamp(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             HSSFWorkbook workbook_for_XML = new HSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook_for_XML.getSheetAt(0);
            int lastRowNum__01 = sheet.getLastRowNum();

            if (lastRowNum__01 >= 0) {
                Row lastRow = sheet.getRow(lastRowNum__01);
                Cell timestampCell = lastRow.getCell(8); // For timestamp is in the 9th column

                if (timestampCell != null) {
                    return timestampCell.getStringCellValue(); // Assuming the timestamp is stored 
                }
            }
        }

        return null; // Returning the null if there is no timestamp
    }


    static String getFutureDateString(int daysToAdd) {
        // Getting the current date
        LocalDate currentDate = LocalDate.now();

        // Adding the specified number of days to the current date
        LocalDate futureDate = currentDate.plusDays(daysToAdd);

        // Formatting the future date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return futureDate.format(formatter);
    }

    private static String formatDoubleToString(double value) {
        // Use DecimalFormat to format the double as a string
        DecimalFormat decimalFormat__01 = new DecimalFormat("#.##");
        return decimalFormat__01.format(value);
    }
}

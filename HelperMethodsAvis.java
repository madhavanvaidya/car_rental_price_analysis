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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
Author: Shahriar Rahman
Date: 03-December-2023
*/
public class HelperMethodsAvis {

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
            int rowIndex = 1; // Starting from the second row_of_XML after headers

            // Loop through the div elements
            for (WebElement divElement : divElements) {

                String[] car_DataArray__01 = divElement.getText().split("\n");
                int totalData = (int) Math.round(car_DataArray__01.length / 10.0);
                System.out.println(totalData);

                //--------------------------------
                String inputData__01 = divElement.getText();

                // Split the input data into lines
                String[] lines = inputData__01.split("\n");

                // List to store chunks of 10 lines for each car
                List<String[]> carChunks_Arr__XML = new ArrayList<>();

                // processing will start when these words are found
                String[] carStartKeywords = {"Economy", "Compact", "Intermediate", "Standard", "Full-Size", "Intermediate SUV", "Standard SUV", "Luxury"};

                //  Created Index to track the current position
                int car_Data__Array__01 = 0;

                while (car_Data__Array__01 < lines.length) {
                    if (startsWithAny(lines[car_Data__Array__01], carStartKeywords)) {
                        String[] carChunk = new String[10];
                        for (int i = 0; i < 10 && car_Data__Array__01 < lines.length; i++) {
                            carChunk[i] = lines[car_Data__Array__01];
                            car_Data__Array__01++;
                        }
                        carChunks_Arr__XML.add(carChunk);
                    } else {
                        car_Data__Array__01++;
                    }
                }

                for (String[] carChunk : carChunks_Arr__XML) {
                    String price__01 = carChunk[5].replace("C$", "").replace("Total", "").replace(",", "").trim();

                    // Check if carChunk[5] contains any substring 
                    boolean if_containsKeyword = Arrays.stream(carStartKeywords).anyMatch(price__01::contains);

                    Row row_of_XML = sheet.createRow(rowIndex);

                    // Adding data to each column
                    row_of_XML.createCell(0).setCellValue(carChunk[0].trim()); // Vehicle Type
                    row_of_XML.createCell(1).setCellValue(carChunk[1].replace("or", "").replace("similar", "").replace("- Vehicle determined upon pick-up", "").trim()); // Vehicle Model
                    String[] passengerData = carChunk[2].split(" ");
                    row_of_XML.createCell(2).setCellValue(passengerData[0].trim()); // Number of Passengers
                    row_of_XML.createCell(3).setCellValue(passengerData[1].trim()); // Number of Large Bags
                    row_of_XML.createCell(4).setCellValue(passengerData[2].trim()); // Number of Small Bags
                    row_of_XML.createCell(5).setCellValue(carChunk[3].replace("Transmission", "").trim()); // Transmission
                    if (if_containsKeyword) {
                        row_of_XML.createCell(6).setCellValue("40");
                    } else {
                        // Convert the price__01 to double
                        double priceDouble_for_XML;
                        try {
                            priceDouble_for_XML = Double.parseDouble(price__01);
                        } catch (Exception e) {
                            // Handling the case where the price__01 is not a double
                            priceDouble_for_XML = 1200;
//                            e.printStackTrace();
                            System.out.println("An unexpected error has been occured");
                        }

                        // Dividing the price__01 by 30
                        double pricePerDay = priceDouble_for_XML / 30;

                        // Format the result as a string
                        String resultString = HelperMethodsString.formatDoubleToString(pricePerDay);
                        row_of_XML.createCell(6).setCellValue(resultString); // Cost
                    }
                    row_of_XML.createCell(7).setCellValue("avis"); // website
                    row_of_XML.createCell(8).setCellValue(getCurrentTimestamp()); // time

                    rowIndex++;
                }
            }

            // Save the workbook_for_XML to a file
            try (FileOutputStream fileOut__XML = new FileOutputStream("CarRentalData.xls")) {
                workbook_for_XML.write(fileOut__XML);
                workbook_for_XML.close();
            } catch (IOException e) {
                System.out.println("An unexpected Error occurred");
            }
        } catch (IOException e) {
            System.out.println("An unexpected Error occurred");
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    private static boolean startsWithAny(String str, String[] keywords) {
        for (String keyword : keywords) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

}

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
public class HelperMethodsTime {

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static String getCurrentTimestamp() {
        // Get current timestamp in a specific format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date latestDate_01 = new Date();
        return dateFormat.format(latestDate_01);
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static boolean isTimeDifferenceGreaterThanTenMinutes() {
        try {
            // Parse the timestamp from the last row in the Excel file
            String lastRowTimestamp = getLastRowTimestamp("CarRentalData.xls");

            // Calculate the time difference
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lastRowDate = dateFormat.parse(lastRowTimestamp);
            Date latestDate_01 = new Date();

            long timeDifferenceMinutes = TimeUnit.MILLISECONDS.toMinutes(latestDate_01.getTime() - lastRowDate.getTime());

            return timeDifferenceMinutes > 10;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return true; // Consider running the code in case of any parsing issues or IO errors
        }
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    private static String getLastRowTimestamp(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            if (lastRowNum >= 0) {
                Row lastRow = sheet.getRow(lastRowNum);
                Cell timestampCell = lastRow.getCell(8); // Assuming timestamp is in the 9th column (index 8)

                if (timestampCell != null) {
                    return timestampCell.getStringCellValue(); // Assuming the timestamp is stored as a string
                }
            }
        }

        return null; // Return null if there is no timestamp or any issue reading it
    }

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static String getFutureDateString(int daysToAdd) {
        // current date
        LocalDate latestDate_01 = LocalDate.now();

        // Adding the days to the current date
        LocalDate futdate_01 = latestDate_01.plusDays(daysToAdd);

        // Format the future date
        DateTimeFormatter formatter_01 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return futdate_01.format(formatter_01);
    }

}

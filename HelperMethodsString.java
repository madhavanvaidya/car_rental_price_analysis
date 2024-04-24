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
public class HelperMethodsString {

    /*
    Author: Shahriar Rahman
    Date: 03-December-2023
    */
    static String formatDoubleToString(double value) {
        // Use DecimalFormat to format the double as a string
        DecimalFormat decimal_Frmt = new DecimalFormat("#.##");
        return decimal_Frmt.format(value);
    }
}

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class SearchVehicle {

    public static void searchForVehicleType(String targetVehicleType, String filePath) {
        try {
            // Load data from the Excel file
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook__01 = WorkbookFactory.create(file);

            // Only for the first column
            Sheet sheet = workbook__01.getSheetAt(0);

            // Using variable to check any row found__01 
            boolean found__01 = false;

            // Iterate through all rows and print matching rows
            for (Row row : sheet) {
                Cell cell__01 = row.getCell(0); // Assuming Vehicle Type is in the first column
                if (cell__01 != null && cell__01.getStringCellValue().toLowerCase().equals(targetVehicleType)) {
                    // Print all columns of the matching row
                    for (Cell c : row) {
                        System.out.print(c + "\t");
                    }
                    System.out.println();
                    found__01 = true;
                }
            }

            // If no matching row is found__01, print a message
            if (!found__01) {
//                System.out.println("Vehicle Type '" + targetVehicleType + "' not found__01 in the Excel sheet.");
            }

            // Close the workbook__01 and input stream
            workbook__01.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}

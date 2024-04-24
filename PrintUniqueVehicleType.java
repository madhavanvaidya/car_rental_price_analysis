import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PrintUniqueVehicleType {

    public static Set<String> getUniqueVehicleTypes(String filePath) throws IOException {
        Set<String> unique_Vehicle_Typ_01 = new HashSet<>();

        try (FileInputStream fis_01 = new FileInputStream(filePath);
             Workbook excelDataBook = new HSSFWorkbook(fis_01)) {

            for (int i = 0; i < excelDataBook.getNumberOfSheets(); i++) {
                Sheet excelSheet = excelDataBook.getSheetAt(i);
                extractVehicleTypesFromSheet(excelSheet, unique_Vehicle_Typ_01);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excel file not read properly. PLease try again");
        }
        return unique_Vehicle_Typ_01;
    }

    private static void extractVehicleTypesFromSheet(Sheet excelSheet, Set<String> unique_Vehicle_Typ_01) {
        for (Row dataRow : excelSheet) {
            Cell dataCell = dataRow.getCell(0); // Assuming "VEHICLE TYPE" is in the first column
            if (dataCell != null && dataCell.getCellType() == CellType.STRING) {
                String selected__vehicleType_1 = dataCell.getStringCellValue().trim();
                if (!selected__vehicleType_1.isEmpty()) {
                    unique_Vehicle_Typ_01.add(selected__vehicleType_1);
                }
            }
        }
    }

    public static void printUniqueVehicleTypes(String filePath) {
        try {
            Set<String> unique_Vehicle_Typ_01 = getUniqueVehicleTypes(filePath);
            System.out.println("-------------------------------");
            System.out.println("Unique Vehicle Types:");
            System.out.println("-------------------------------");
            for (String selected__vehicleType_1 : unique_Vehicle_Typ_01) {
                System.out.println(selected__vehicleType_1);
            }
        } catch (IOException e) {
            System.out.println("Excel file not read properly. PLease try again");
        }
    }
}

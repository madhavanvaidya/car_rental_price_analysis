import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class BestPrice {
    public static void printCostSummary(List<CarData>  List_of_Data_Car) {
        int size =  List_of_Data_Car.size();
        if (size > 0) {
            // To print the Lowest data
            System.out.println("Lowest Cost:");
            System.out.println( List_of_Data_Car.get(0));

            if (size > 1) {
                // To print the middle data
                int  middle__Index = size / 2;
                System.out.println("One In-between Cost:");
                System.out.println( List_of_Data_Car.get( middle__Index));
            }
            System.out.println("Highest Cost:");
            System.out.println( List_of_Data_Car.get(size - 1)); // To print the Highest data
        } else {
            System.out.println("No data to display."); // If size is 0, display no data exist
        }
    }

    public static List<CarData> readExcel(String filePath) throws Exception {
        List<CarData>  List_of_Data_Car = new ArrayList<>(); // creating a new arrayList

        try (FileInputStream file = new FileInputStream(filePath); // Reading data from the path(XML)
             Workbook workbook = new HSSFWorkbook(file)) {

            Sheet sheet_XML = workbook.getSheetAt(0); // For first column
            Iterator<Row> iterator = sheet_XML.iterator();

            while (iterator.hasNext()) {  // Iteration through each Row
                Row Data_current__Row = iterator.next();

                // To Skip the header row
                if (Data_current__Row.getRowNum() == 0) {
                    continue;
                }

                CarData carData = new CarData("Economy", "Mitsubishi Mirage or similar", 1442.60);

                // Iterating through each cell in the row
                Iterator<Cell> cellIterator = Data_current__Row.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            carData.setVehicleType(currentCell.getStringCellValue()); // For column 0
                            break;
                        case 1:
                            carData.setVehicleModel(currentCell.getStringCellValue()); // For column 1
                            break;
                        case 6:
                            // Parsing is required here for the last column and replace all ,
                            carData.setCost(Double.parseDouble(currentCell.getStringCellValue().replace(",", "")));
                            break;
                        // Add cases for other columns if needed
                    }
                }

                 List_of_Data_Car.add(carData);
            }
        }
        return  List_of_Data_Car;
    }

    // Defining the class
    static class CarData implements Comparable<CarData> {
        private String selected_vehicle_Type;
        private String selected_vehicle_Model;
        private double selected_vehicle_cost;

        // Constructor
        public CarData(String selected_vehicle_Type, String selected_vehicle_Model, double selected_vehicle_cost) {
            this.selected_vehicle_Type = selected_vehicle_Type;  // Initialization
            this.selected_vehicle_Model = selected_vehicle_Model;
            this.selected_vehicle_cost = selected_vehicle_cost;
        }

        // Getters and Setters
        public String getVehicleType() {
            return selected_vehicle_Type;
        }

        public void setVehicleType(String selected_vehicle_Type) {
            this.selected_vehicle_Type = selected_vehicle_Type;
        }

        public String getVehicleModel() {
            return selected_vehicle_Model;
        }

        public void setVehicleModel(String selected_vehicle_Model) {
            this.selected_vehicle_Model = selected_vehicle_Model;
        }

        public double getCost() {
            return selected_vehicle_cost;
        }

        public void setCost(double selected_vehicle_cost) {
            this.selected_vehicle_cost = selected_vehicle_cost;
        }

        @Override
        public int compareTo(CarData other) {
            return Double.compare(this.selected_vehicle_cost, other.selected_vehicle_cost);
        }

        @Override
        public String toString() {
            return "Vehicle Type: " + selected_vehicle_Type + ", Vehicle Model: " + selected_vehicle_Model + ", Cost: " + selected_vehicle_cost;  //Showing data to the user
        }
    }
}

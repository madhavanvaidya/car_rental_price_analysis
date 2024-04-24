
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PageRanking {

    static void displayPageRanking(String filePath, String target__Column_01) throws IOException {
        // Get the page ranking for the specified column (Vehicle Type)
        Map<String, Integer> page__Ranking_1 = getPageRanking(filePath, target__Column_01);

        // Display the page ranking without certain words
        displayFilteredPageRanking(page__Ranking_1);
    }

    private static Map<String, Integer> getPageRanking(String filePath, String target__Column_01) throws IOException {
        Map<String, Integer> Occurrences_of_keywords = new HashMap<>();

        try (FileInputStream fileInputStream_01 = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fileInputStream_01)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Find the header row to get the column index
            Row header__Row_1 = sheet.getRow(0);
            int targetColumnIndex__001 = findColumnIndex(header__Row_1, target__Column_01);

            if (targetColumnIndex__001 == -1) {
                throw new IllegalArgumentException("Column is not Available: " + target__Column_01);
            }

            // Iterate through rows and update keyword occurrences for the specified column
            for (Row row : sheet) {
                Cell cell_01 = row.getCell(targetColumnIndex__001);

                if (cell_01 != null && cell_01.getCellType() == CellType.STRING) {
                    String cellValue = cell_01.getStringCellValue().toLowerCase();

                    // Split the cell_01 content into keywords
                    String[] keywords = cellValue.split("\\s+");

                    // Update the occurrences of each keyword
                    for (String keyword : keywords) {
                        Occurrences_of_keywords.put(keyword, Occurrences_of_keywords.getOrDefault(keyword, 0) + 1);
                    }
                }
            }
        }

        // Perform page ranking based on keyword occurrences
        return calculatePageRank(Occurrences_of_keywords);
    }

    private static int findColumnIndex(Row header__Row_1, String target__Column_01) {
        if (header__Row_1 != null) {
            for (Cell cell_01 : header__Row_1) {
                if (cell_01.getCellType() == CellType.STRING && cell_01.getStringCellValue().equalsIgnoreCase(target__Column_01)) {
                    return cell_01.getColumnIndex();
                }
            }
        }
        return -1;
    }

    private static Map<String, Integer> calculatePageRank(Map<String, Integer> Occurrences_of_keywords) {
        // Filter out certain words from the page ranking
        List<String> excludedWords = List.of("or", "similar", "not", "found", "total", "-");

        // Remove the excluded words from the key set
        Occurrences_of_keywords.keySet().removeIf(excludedWords::contains);

        // Simple ranking based on the number of occurrences
        // You can implement a more sophisticated algorithm if needed
        return Occurrences_of_keywords;
    }

    private static void displayFilteredPageRanking(Map<String, Integer> page__Ranking_1) {
        if (page__Ranking_1.isEmpty()) {
            System.out.println("No data found for the specified column.");
        } else {
            System.out.println("\n-----------------------------------------");
            System.out.println("Ranking based on Provided vehicles Types");
            System.out.println("-----------------------------------------");
            for (Map.Entry<String, Integer> entry : page__Ranking_1.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}

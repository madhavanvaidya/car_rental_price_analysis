import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellChecker {
    private final Map<String, Integer> inverted__Index_1;

    public SpellChecker(String excelFilePath) {
        // Initialize the vocabulary_11 from the Excel file
        inverted__Index_1 = loadVocabularyFromExcel(excelFilePath);
    }

    private Map<String, Integer> loadVocabularyFromExcel(String excelFilePath) {
        Map<String, Integer> vocabulary_11 = new HashMap<>();

        try (Workbook exl_book_obj = WorkbookFactory.create(new FileInputStream(excelFilePath))) {
            // Assuming the vocabulary_11 is in the first sheet_01 and the first column, adjust as needed
            Sheet sheet_01 = exl_book_obj.getSheetAt(0);

            for (Row data_rows : sheet_01) {
                Cell data_cells = data_rows.getCell(0); // Get the first data_cells in each data_rows

                if (data_cells != null && data_cells.getCellType() == CellType.STRING) {
                    String wrds = data_cells.getStringCellValue().trim().toLowerCase();

                    // Filter out numeric values and other non-relevant terms
                    if (!wrds.matches(".*\\d+.*")) {
                        vocabulary_11.put(wrds, 1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading vocabulary from Excel");
        }

        return vocabulary_11;
    }

    public List<String> getSuggestions(String wrds) {
        List<String> suggestions_11 = new ArrayList<>();

        for (String vocabularyWord : inverted__Index_1.keySet()) {
            int th__distance = calculateEditDistance(wrds, vocabularyWord);

            // Set a threshold for considering a wrds as a suggestion
            if (th__distance <= 4) {
                suggestions_11.add(vocabularyWord);
            }
        }

        return suggestions_11;
    }

    public boolean isWordAvailable(String wrds) {
        return inverted__Index_1.containsKey(wrds.toLowerCase());
    }

    private static int calculateEditDistance(String str_01, String str_02) {
        int mmm = str_01.length();
        int nnn = str_02.length();
        int[][] dp__001 = new int[mmm + 1][nnn + 1];

        for (int con_i = 0; con_i <= mmm; con_i++) {
            for (int con_j = 0; con_j <= nnn; con_j++) {
                if (con_i == 0) {
                    dp__001[con_i][con_j] = con_j;
                } else if (con_j == 0) {
                    dp__001[con_i][con_j] = con_i;
                } else if (str_01.charAt(con_i - 1) == str_02.charAt(con_j - 1)) {
                    dp__001[con_i][con_j] = dp__001[con_i - 1][con_j - 1];
                } else {
                    dp__001[con_i][con_j] = 1 + Math.min(Math.min(dp__001[con_i - 1][con_j], dp__001[con_i][con_j - 1]),
                            dp__001[con_i - 1][con_j - 1]);
                }
            }
        }

        return dp__001[mmm][nnn];
    }
}

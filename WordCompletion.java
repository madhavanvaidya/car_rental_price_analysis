import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WordCompletion {
    static void completeWordsStartingWith(String filePath_01, String userWord) throws IOException {
        // Set to store completed completed_words_1
        Set<String> completed__Words_01 = new HashSet<>();

        try (FileInputStream strm_for_file_inpt = new FileInputStream(new File(filePath_01));
            Workbook exl_book_obj = WorkbookFactory.create(strm_for_file_inpt)) {

            Sheet sheet_01 = exl_book_obj.getSheetAt(0);

            // Iterating through rows and complete completed_words_1 starting with the user-provided word_data
            for (Row data_row : sheet_01) {
                for (Cell data_cell : data_row) {
                    if (data_cell != null && data_cell.getCellType() == CellType.STRING) {
                        String val_of_cell = data_cell.getStringCellValue().toLowerCase();

                        // Split the data_cell content into completed_words_1
                        String[] completed_words_1 = val_of_cell.split("\\s+");

                        // Check if any word_data starts with the user-provided word_data
                        for (String word_data : completed_words_1) {
                            if (word_data.startsWith(userWord)) {
                                completed__Words_01.add(word_data);
                            }
                        }
                    }
                }
            }
        }

        // Print completed completed_words_1 to the console
        if (completed__Words_01.isEmpty()) {
            System.out.println("Found no words that starts with '" + userWord + "' in Excel.");
        } else {
            System.out.println("All the Completed Words that starts with '" + userWord + "' are:");
            for (String word_data : completed__Words_01) {
                System.out.println(word_data);
            }
        }
    }

}

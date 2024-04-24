import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class provides functionality for counting occurrences of a specified word in an Excel file.
 */
public class WordCount {

    /**
     * Counting the occurrences of a target word in an Excel file.
     *
     * @param filePath    The path to the Excel file.
     * @param targetWord  The word being counted for occurrences.
     * @return            The total count of occurrences of the target word in the Excel file.
     * @throws IOException If an error is encountered while reading the Excel file.
     */
    public static int countWordOccurrences(String filePath, String targetWord) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) {

            // Initializing the word count variable
            int wordCount = 0;

            // Iterating through each sheet in the workbook
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // Updating the word count based on occurrences in the current sheet
                wordCount += countWordOccurrencesInSheet(sheet, targetWord);
            }

            // Returning the total word count across all sheets
            return wordCount;

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Excel file not read properly. PLease try again");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Excel file not read properly. PLease try again");
        }
    }

    /**
     * Counting the occurrences of a target word in a specific sheet.
     *
     * @param sheet       The Excel sheet being searched for occurrences.
     * @param targetWord  The word being counted for occurrences.
     * @return            The count of occurrences of the target word in the given sheet.
     */
    private static int countWordOccurrencesInSheet(Sheet sheet, String targetWord) {
        // Initializing the word count variable for the current sheet
        int wordCount = 0;

        // Iterating through each row in the sheet
        for (Row row : sheet) {
            // Iterating through each cell in the row
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    // Getting the content of the cell in lowercase for case-insensitive comparison
                    String content = cell.getStringCellValue().toLowerCase();

                    // Checking if the target word appears in the cell content
                    if (isWordInString(content, targetWord)) {
                        // Incrementing the word count if a match is found
                        wordCount++;
                    }
                }
                // Adding conditions for other data types if needed
            }
        }

        // Returning the total word count for the current sheet
        return wordCount;
    }

    /**
     * Checking if a target word appears in a given string as a standalone word.
     *
     * @param content     The string being searched for the target word.
     * @param targetWord  The word being checked for in the string.
     * @return            True if the target word is appearing as a standalone word, false otherwise.
     */
    private static boolean isWordInString(String content, String targetWord) {
        // Using a regular expression to check for word boundaries
        String regexPattern = "\\b" + targetWord + "\\b";
        // Returning true if the pattern is found in the content
        return content.matches(".*" + regexPattern + ".*");
    }
}

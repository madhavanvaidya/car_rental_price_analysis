import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrequencyCount {
    private static void handleException(Exception e) { // handling the unexpected exceptions and showing the result.
        System.err.println("An error occurred, Please try again ");
//        e.printStackTrace();
    }

    public static Map<String, Integer> initializeWordFrequencyMap(String filePath) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String word = cell.getStringCellValue().toLowerCase();

                        // Only consider unique words once when it contains the word
                        if (!wordFrequencyMap.containsKey(word)) {
                            wordFrequencyMap.put(word, 1);
                        } else {
                            // Print the counts during initialization
                        }
                    }
                }
            }
        } catch (IOException | EncryptedDocumentException e) { // handling the IO and EncryptedDocument exceptions cases
            handleException(e);
        }

        return wordFrequencyMap;
    }

    public static int performWordSearch(Map<String, Integer> wordFrequencyMap, String searchWord) {

        String lowerCaseSearchWord = searchWord.toLowerCase(); // Consider the lowerCase Word

        // Using this to update the current count
        int currentCount = wordFrequencyMap.getOrDefault(lowerCaseSearchWord, 0);

        // Here, Setting the updated count to 1 if the word is not found
        if (currentCount == 0){
            currentCount = 1;
        }
        int updatedCount = currentCount + 1;

        wordFrequencyMap.put(lowerCaseSearchWord, updatedCount);

        return currentCount; //returning the output which will be used to check the current count
    }
}

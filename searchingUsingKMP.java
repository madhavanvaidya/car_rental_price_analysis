import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class searchingUsingKMP {

    private Map<String, List<SearchResult>> index__01;

    public searchingUsingKMP() {
        initializeIndex();
    }

    private void initializeIndex() {
        this.index__01 = new HashMap<>();
    }

    private int[] computeLPS(String pattern) {
        int mmm = pattern.length();
        int[] lps_01 = new int[mmm];
        int len_KMP = 0;
        int i = 1;

        while (i < mmm) {
            if (pattern.charAt(i) == pattern.charAt(len_KMP)) {
                len_KMP++;
                lps_01[i] = len_KMP;
                i++;
            } else {
                if (len_KMP != 0) {
                    len_KMP = lps_01[len_KMP - 1];
                } else {
                    lps_01[i] = 0;
                    i++;
                }
            }
        }
        return lps_01;
    }

    private List<SearchResult> search(String pattern, String text, int rowNum) {
        List<SearchResult> occur_nces = new ArrayList<>();
        int mmm = pattern.length();
        int nnn = text.length();
        int[] lps_01 = computeLPS(pattern.toLowerCase());
        int i = 0; // index__01 for text
        int j = 0; // index__01 for pattern

        while (i < nnn) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == mmm) {
                // Pattern found at index__01 i-j
                occur_nces.add(new SearchResult(i - j, rowNum));
                // Move j to the previous longest prefix suffix
                j = lps_01[j - 1];
            } else if (i < nnn && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps_01[j - 1];
                } else {
                    i++;
                }
            }
        }
        return occur_nces;
    }

    public void addDocument(int documentId, String data_content) {
        String[] words = data_content.split("\\s+");

        for (String word : words) {
            // Convert the word to lowercase for case-insensitive indexing
            word = word.toLowerCase();

            // If the word is not in the index__01, add it with a new ArrayList
            index__01.putIfAbsent(word, new ArrayList<>());

            // Use KMP to find occur_nces of the word in the data_content
            List<SearchResult> occur_nces = search(word, data_content.toLowerCase(), documentId);

            // Add the occur_nces to the list associated with the word
            index__01.get(word).addAll(occur_nces);
        }
    }

    public List<SearchResult> searchWord(String query) {
        // Convert the query to lowercase for case-insensitive search
        query = query.toLowerCase();

        // Retrieve the list of occur_nces associated with the query word
        return index__01.getOrDefault(query, new ArrayList<>());
    }

    public void buildIndexFromExcel(String excelFilePath) {
        initializeIndex(); // Clear existing index__01 before building from Excel

        try (FileInputStream file = new FileInputStream(excelFilePath)) {
            Workbook exl_book_obj = new HSSFWorkbook(file);
            DataFormatter dataFormatter = new DataFormatter();

            for (Sheet sheet__01 : exl_book_obj) {
                for (Row data_row : sheet__01) {
                    StringBuilder contentBuilder = new StringBuilder();
                    for (Cell cell__01 : data_row) {
                        String cellContent = dataFormatter.formatCellValue(cell__01);
                        contentBuilder.append(cellContent).append(" ");
                    }
                    String data_content = contentBuilder.toString().trim();
                    addDocument(data_row.getRowNum(), data_content);
                }
            }
        } catch (IOException e) {
            System.out.println("Indexing not done properly. Please provide proper data.");
        }
    }

    public static String getDataFromRow(String excelFilePath, int rowNum) {
        try (FileInputStream file = new FileInputStream(excelFilePath)) {
            Workbook exl_book_obj = new HSSFWorkbook(file);
            Sheet sheet__01 = exl_book_obj.getSheetAt(0); // Assuming data is in the first sheet__01

            Row data_row = sheet__01.getRow(rowNum);
            StringBuilder db_data_builder = new StringBuilder();

            DataFormatter dataFormatter = new DataFormatter(); // Create DataFormatter instance

            if (data_row != null) {
                for (Cell cell__01 : data_row) {
                    String cellContent = dataFormatter.formatCellValue(cell__01);
                    db_data_builder.append(cellContent).append(" ");
                }
            }

            return db_data_builder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static class SearchResult {
        private int index__01;
        private int data_row;

        public SearchResult(int index__01, int data_row) {
            this.index__01 = index__01;
            this.data_row = data_row;
        }

        public int getIndex() {
            return index__01;
        }

        public int getRow() {
            return data_row;
        }
    }
}

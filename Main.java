import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import org.openqa.selenium.WebDriver;


public class Main {

    public static void main(String[] args) {

        // Check if the Excel file exists
        File excelFile = new File("CarRentalData.xls");
        boolean excelFileExists = excelFile.exists();

        // Check the timestamp difference if the Excel file exists
        if (!excelFileExists || HelperMethodsTime.isTimeDifferenceGreaterThanTenMinutes()) {
            boolean successfullyExecuted = executeWebAutomation();

            // Add any additional logic based on the value of successfullyExecuted
        } else {
            System.out.println("Skipping web automation as the time difference is less than 10 minutes.");
        }

        for (int i = 0 ; i< 100 ; i++ )
            System.out.println();

        System.out.println(" _______  _______  _______             _______  _______  _______    _______  _______  _       _________ _______  _       ");
        System.out.println("(  ____ \\(  ___  )(  ____ \\|\\     /|  (  ____ \\(  ___  )(  ____ )  (  ____ )(  ____ \\( (    /|\\__   __/(  ___  )( \\      ");
        System.out.println("| (    \\/| (   ) || (    \\/( \\   / )  | (    \\/| (   ) || (    )|  | (    )|| (    \\/|  \\  ( |   ) (   | (   ) || (      ");
        System.out.println("| (__    | (___) || (_____  \\ (_) /   | |      | (___) || (____)|  | (____)|| (__    |   \\ | |   | |   | (___) || |      ");
        System.out.println("|  __)   |  ___  |(_____  )  \\   /    | |      |  ___  ||     __)  |     __)|  __)   | (\\ \\) |   | |   |  ___  || |      ");
        System.out.println("| (      | (   ) |      ) |   ) (     | |      | (   ) || (\\ (     | ( \\ (   | (      | | \\   |   | |   | (   ) || |      ");
        System.out.println("| (____/\\| )   ( |/\\____) |   | |     | (____/\\| )   ( || ) \\ \\__  | ) \\ \\__| (____/\\| )  \\  |   | |   | )   ( || (____/\\");
        System.out.println("(_______/|/     \\|\\_______)   \\_/     (_______/|/     \\||/   \\__/  |/   \\__/(_______/|/    )_)   )_(   |/     \\|(_______/");
        System.out.println("\n-------------------------------");
        System.out.println(" Overall Summary ");
        System.out.println("-------------------------------");
        try {
            String filePath = "CarRentalData.xls";
            List<BestPrice.CarData> carDataList = BestPrice.readExcel(filePath);
            SpellChecker wordSuggestions = new SpellChecker(filePath);

            // Sort the data based on the cost
            Collections.sort(carDataList);

            // Print the highest, lowest, and one in-between cost
            BestPrice.printCostSummary(carDataList);


            PrintUniqueVehicleType.printUniqueVehicleTypes(filePath);

            // Specify the target column as "Vehicle Type"
            String targetColumn = "Vehicle Type";
            // Call the function to get and display the page ranking
            PageRanking.displayPageRanking(filePath, targetColumn);

            Scanner scanner = new Scanner(System.in);

            Map<String, Integer> wordFrequencyMap = FrequencyCount.initializeWordFrequencyMap(filePath);

            searchingUsingKMP invertedIndex = new searchingUsingKMP();
            invertedIndex.buildIndexFromExcel(filePath);
            System.out.println("-------------------------------");

            // Infinite loop for continuous user input
            while (true) {
                System.out.print("Enter a vehicle type to search (type 'exit' to quit): ");
                String targetWord = scanner.nextLine();

                if (targetWord.equals("exit")) {
                    // Exit the loop if the user types 'exit'
                    break;
                }

                List<searchingUsingKMP.SearchResult> result = invertedIndex.searchWord(targetWord);

                // Print the search result
                if (result.isEmpty()) {
                    System.out.println("Sorry, Feeded Data '" + targetWord + "' not found in the document.");
                } else {
                    System.out.println("Occurrences of the Provided word in our Database ::- '" + targetWord + "':");
                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("Showing Data in order -  Vehicle Type Vehicle Model Number of Passengers Number of Large Bags Number of Small Bags Transmission Cost Website Time ");
                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (searchingUsingKMP.SearchResult searchResult : result) {
                        System.out.println("Row: " + searchResult.getRow() + ", Data: " +
                                searchingUsingKMP.getDataFromRow(filePath, searchResult.getRow()));
                    }
                }

                int wordCount = WordCount.countWordOccurrences(filePath, targetWord);
                int searchCount = FrequencyCount.performWordSearch(wordFrequencyMap, targetWord);

                System.out.println("Word '" + targetWord + "' found in the Excel file " + wordCount + " times & you have searched it: " + searchCount + " times");
                List<String> suggestions = wordSuggestions.getSuggestions(targetWord);

                if (suggestions.isEmpty() && !wordSuggestions.isWordAvailable(targetWord)) {
                } else if (suggestions.isEmpty()) {
                    System.out.println("No suggestions found for '" + targetWord + "'. Proceeding with other operations...");
                } else {
                    System.out.println("-------------------------------");
                    System.out.println("Suggestions for '" + targetWord + "':");
                    for (String suggestion : suggestions) {
                        System.out.println("- " + suggestion);
                    }
                    System.out.println("-------------------------------");
                }

                WordCompletion.completeWordsStartingWith(filePath, targetWord);
                System.out.println("-------------------------------");
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Excel file read was not done properly");
        }
    }

    private static boolean executeWebAutomation() {
        boolean successfullyExecuted = false;

        while (!successfullyExecuted) {
            String geckoDriverLocation = "geckodriver";
            System.setProperty("webdriver.gecko.driver", geckoDriverLocation);
            WebDriver driver = new FirefoxDriver();

            try {
                AvisWebsiteHandler.processAvisWebsite(driver);
                CarRentalsWebsiteHandler.processCarRentalsWebsite(driver);
                EnterpriseWebsiteHandler.processEnterpriseWebsite(driver);

                successfullyExecuted = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.out.println("Process restarting, initial run was not successful");
                }
            }
        }
        return successfullyExecuted;
    }
}

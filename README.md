# Car Rental Price Analysis Project

The Car Rental Price Analysis project aims to explore pricing information from three distinct car rental websites—Avis, CarRentals.com, and Enterprise. The objective is to evaluate cars based on factors such as pickup and drop-off locations, dates, car type, price, and distance, with the goal of identifying the most budget-friendly options on these platforms.

## Objective

The primary objective of this project is to provide customers with in-depth insights into pricing trends, enabling them to make well-informed decisions when selecting cars. By conducting a comprehensive analysis of cost factors in each category, the project aims to empower customers to customize their choices based on budget constraints and personal preferences.

## Key Features

- **Automated Data Collection**: Utilizes Selenium WebDriver to automate the process of collecting pricing data from car rental websites.
- **Data Analysis**: Analyzes collected data to identify trends, patterns, and cost factors influencing car rental prices.
- **Insightful Reports**: Generates reports and summaries to provide customers with valuable insights into pricing trends and cost-effective options.

## Prerequisites

- **Java**: The project is developed in Java programming language.
- **Selenium WebDriver**: Requires Selenium WebDriver to automate web interactions.
- **GeckoDriver**: GeckoDriver is used for Firefox browser automation.

## Usage

1. **Clone the Repository**: Clone this repository to your local machine.

    ```bash
    git clone https://github.com/yourusername/car-rental-price-analysis.git
    ```

2. **Set Up Environment**: Ensure you have Java and GeckoDriver installed on your system.

3. **Run the Project**: Execute the `Main.java` file to start the car rental price analysis process.

    ```bash
    javac Main.java
    java Main
    ```

4. **Review Results**: Once the analysis is complete, review the generated reports and summaries to gain insights into car rental pricing trends.

## Project Structure

- `Main.java`: Main class containing the entry point of the project.
- `HelperMethodsTime.java`: Contains helper methods for time-related operations.
- `BestPrice.java`: Handles data reading, sorting, and printing cost summaries.
- `PrintUniqueVehicleType.java`: Prints unique vehicle types from the data.
- `PageRanking.java`: Displays page ranking based on a specified target column.
- `FrequencyCount.java`: Initializes word frequency map and performs word search operations.
- `searchingUsingKMP.java`: Implements searching and indexing functionalities using the Knuth-Morris-Pratt algorithm.
- `SpellChecker.java`: Provides suggestions for misspelled words.
- `WordCount.java`: Counts occurrences of a word in the data.
- `WordCompletion.java`: Completes words starting with a specified prefix.

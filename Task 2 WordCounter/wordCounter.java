import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int choice = getChoice(scanner);

            if (choice == 1) {
                String text = getUserText(scanner);

                // Step 3: Split the string into an array of words using space or punctuation as delimiters.
                String[] words = text.split("\\s+|\\p{Punct}");

                // Step 4: Initialize a counter variable to keep track of the number of words.
                int wordCount = 0;

                // Step 5: Initialize a set to store unique words.
                Set<String> uniqueWords = new HashSet<>();

                // Step 6: Create a set of common words (stop words) to ignore.
                Set<String> stopWords = createStopWordsSet();

                // Step 7: Initialize a map to store word frequency.
                Map<String, Integer> wordFrequency = new HashMap<>();

                // Step 8: Iterate through the array of words and increment the counters.
                for (String word : words) {
                    if (!word.isEmpty()) {
                        // Ignore empty strings (e.g., multiple spaces in the input)
                        word = word.toLowerCase(); // Convert to lowercase to count words case-insensitively

                        // Remove punctuation marks
                        word = word.replaceAll("[^a-zA-Z]", "");

                        // Count total words
                        wordCount++;

                        // Count unique words
                        if (!stopWords.contains(word)) {
                            uniqueWords.add(word);
                        }

                        // Count word frequency
                        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                    }
                }

                // Step 9: Display the total count of words and the number of unique words to the user.
                System.out.println("Total number of words: " + wordCount);
                System.out.println("Number of unique words: " + uniqueWords.size());

                // Step 10: Display word frequency
                System.out.println("Word frequencies:");
                for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            } else if (choice == 2) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }
        }
    }

    private static int getChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("Enter '1' to provide text input, '2' to exit:");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter '1' or '2'.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return choice;
    }

    private static String getUserText(Scanner scanner) {
        System.out.println("Enter the text:");
        String text = scanner.nextLine().trim();
        while (text.isEmpty()) {
            System.out.println("The text cannot be empty. Please enter the text:");
            text = scanner.nextLine().trim();
        }
        return text;
    }

    private static Set<String> createStopWordsSet() {
        return new HashSet<>(Arrays.asList("a", "an", "the", "and", "is", "are", "in", "on", "to", "for", "of", "with"));
    }
}
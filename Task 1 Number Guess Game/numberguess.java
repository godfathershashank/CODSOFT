import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessGame {
    private static final int SECRET_NUMBER_MIN = 1;
    private static final int SECRET_NUMBER_MAX = 100;
    private static final int TIMER_DURATION = 5; // Timer duration in seconds
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    public static void main(String[] args) {
        playNumberGuessGame();
    }

    public static void playNumberGuessGame() {
        Random random = new Random();
        int secretNumber = random.nextInt(SECRET_NUMBER_MAX - SECRET_NUMBER_MIN + 1) + SECRET_NUMBER_MIN;
        int attempts = 0;
        int currentHighScore = Integer.MAX_VALUE;
        boolean gameActive = true; // Indicates if the game is still active

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.print("Please enter your name: ");
        String playerName = scanner.nextLine(); // Read the player's name

        System.out.println("Hello, " + playerName + "! I have chosen a number between " + SECRET_NUMBER_MIN + " and " + SECRET_NUMBER_MAX + ". Try to guess it!");

        int previousHighScore = loadHighScore();

        while (gameActive) { // Loop until the game is still active
            System.out.print("Enter your guess (you have " + TIMER_DURATION + " seconds): ");

            long startTime = System.currentTimeMillis(); // Get the current time

            int guess;
            try {
                String input = scanner.nextLine();
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Handle non-integer input
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            long endTime = System.currentTimeMillis(); // Get the current time again
            long elapsedSeconds = (endTime - startTime) / 1000; // Calculate elapsed seconds

            if (elapsedSeconds >= TIMER_DURATION) {
                System.out.println("Time's up! You didn't enter a guess within " + TIMER_DURATION + " seconds.");
                gameActive = false; // Set the game as not active
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                System.out.println("Congratulations, " + playerName + "! You guessed the correct number " + secretNumber + " in " + attempts + " attempts!");

                currentHighScore = Math.min(attempts, currentHighScore);
                System.out.println("Current High Score: " + currentHighScore + " attempts!");

                if (currentHighScore < previousHighScore) {
                    saveHighScore(currentHighScore);
                }

                break;
            } else {
                System.out.println("Incorrect guess. Please try again.");

                if (attempts >= 3) {
                    System.out.println("Hint: The secret number is " + (secretNumber > guess ? "greater" : "smaller") + " than " + guess + ".");
                }
            }
        }

        System.out.println("Previous High Score: " + previousHighScore + " attempts!");
        System.out.print("Do you want to play again, " + playerName + "? (y/n): ");
        String playAgain = scanner.next();

        if (playAgain.equalsIgnoreCase("y")) {
            playNumberGuessGame();
        } else {
            System.out.println("Thank you for playing, " + playerName + "!");
        }

        scanner.close();
    }

    private static int loadHighScore() {
        try {
            File file = new File(HIGH_SCORE_FILE);
            if (file.exists()) {
                try (Scanner fileScanner = new Scanner(file)) {
                    return fileScanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 1000; // If the file doesn't exist or there's an error, return a default high score
    }

    private static void saveHighScore(int highScore) {
        try {
            File file = new File(HIGH_SCORE_FILE);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(Integer.toString(highScore));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
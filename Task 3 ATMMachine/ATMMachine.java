import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private float amount;

    public Transaction(String type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": " + amount;
    }
}

class ATM {
    private float balance;
    private final int PIN = 2222;
    private List<Transaction> transactionHistory;

    public ATM(float initialBalance) {
        balance = initialBalance;
        transactionHistory = new ArrayList<>();
    }

    public boolean validatePIN(int enteredPIN) {
        return enteredPIN == PIN;
    }

    public float getBalance() {
        return balance;
    }

    public void withdraw(float amount) {
        if (amount <= balance) {
            balance -= amount;
            Transaction transaction = new Transaction("Withdrawal", amount); // Note the positive amount here
            transactionHistory.add(transaction);
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient balance to withdraw.");
        }
    }

    public void deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            Transaction transaction = new Transaction("Deposit", amount);
            transactionHistory.add(transaction);
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void depositCash(float amount) {
        deposit(amount); // Reuse the deposit method for depositing cash
    }

    public void payBill(float billAmount, String billType) {
        if (billAmount <= balance) {
            balance -= billAmount;
            Transaction transaction = new Transaction("Bill Payment: " + billType, -billAmount);
            transactionHistory.add(transaction);
            System.out.println("Bill payment successful!");
        } else {
            System.out.println("Insufficient balance to pay the bill.");
        }
    }

    // Other methods...
}

public class ATMMachine {
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final long PIN_ATTEMPT_TIMEOUT = 30000; // 30 seconds in milliseconds

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM(1000.0f);

        System.out.println("Welcome to CodSoft ATM\n");

        boolean exit = false;
        while (!exit) {
    int pinAttempts = 0;
    long lastInvalidAttemptTime = 0;
    boolean pinEntered = false;

    while (pinAttempts < MAX_PIN_ATTEMPTS && !pinEntered) {
        if (System.currentTimeMillis() - lastInvalidAttemptTime >= PIN_ATTEMPT_TIMEOUT) {
            System.out.print("Enter Your PIN: ");
            int enteredPIN = readIntegerInput(scanner);

            if (atm.validatePIN(enteredPIN)) {
                pinEntered = true;
                break;
            } else {
                System.out.println("Invalid PIN. Please try again.");
                pinAttempts++;
                lastInvalidAttemptTime = System.currentTimeMillis();
            }
        } else {
            long remainingTime = (PIN_ATTEMPT_TIMEOUT - (System.currentTimeMillis() - lastInvalidAttemptTime)) / 5000;
            System.out.println("Too many incorrect PIN attempts. Please try again after " + remainingTime + " seconds.");
        }
    }

    if (!pinEntered) {
        System.out.println("Too many incorrect PIN attempts.");
    } else {
        handleTransactions(scanner, atm);
    }

    System.out.print("Do you want to continue? (yes/no): ");
    String continueChoice = scanner.next();
    exit = continueChoice.equalsIgnoreCase("no");

    if (exit) {
        System.out.println("Thank you for using CodSoft ATM!");
    }
}

        scanner.close();
    }

    public static void handleTransactions(Scanner scanner, ATM atm) {
        while (true) {
            System.out.println("\n--------------- Our Services ---------------");
            System.out.println("1. Check A/C Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Display Transaction History");
            System.out.println("5. Deposit Cash");
            System.out.println("6. Pay Bill");
            System.out.println("7. Exit");
            System.out.print("Enter Your Choice: ");
            int choice = readIntegerInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Your Balance: " + atm.getBalance());
                    break;
                case 2:
                    // Withdraw Money option
                    System.out.print("Enter the amount to withdraw: ");
                    float withdrawAmount = readFloatInput(scanner);
                    atm.withdraw(withdrawAmount);
                    break;
                case 3:
                    // Deposit Money option
                    System.out.print("Enter the amount to deposit: ");
                    float depositAmount = readFloatInput(scanner);
                    atm.deposit(depositAmount);
                    break;
                case 4:
                    // Display Transaction History
                    atm.displayTransactionHistory();
                    break;
                case 5:
                    // Deposit Cash option
                    System.out.print("Enter the amount to deposit: ");
                    float depositCashAmount = readFloatInput(scanner);
                    atm.depositCash(depositCashAmount);
                    System.out.println("Cash deposited successfully!");
                    break;
                case 6:
                    // Pay Bill option
                    System.out.print("Enter bill amount: ");
                    float billAmount = readFloatInput(scanner);
                    System.out.print("Enter bill type: ");
                    String billType = scanner.next();
                    atm.payBill(billAmount, billType);
                    System.out.println("Bill paid successfully!");
                    break;
                case 7:
                    return; // Exit the transaction handling loop
                default:
                    System.out.println("Enter a valid choice!");
                    break;
            }
        }
    }

    public static int readIntegerInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public static float readFloatInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
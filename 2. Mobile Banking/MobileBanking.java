// MOBILE BANKING:
import java.lang.classfile.instruction.ReturnInstruction;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MobileBanking {

    static ArrayList<String[]> users = new ArrayList<>();
    static  String[] currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Mobile Bank!");

        while (true) {
            String command = scanner.nextLine().trim();
            String[] parts = command.split("\\s+");

            if (parts.length == 0) continue;
            String action = parts[0];
            if (action.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            else if (action.equals("register")) {
                HandleRegister(parts);
            }
            else if (action.equals("login")) {
                HandleLogin(parts);
            }
            else if (action.equals("logout")) {
                HandleLogout();
            }
            else if (action.equals("show") && parts.length > 1 && parts[1].equals("balance")) {
                HandleShowBalance();
            }
            else if (action.equals("deposit")) {
                HandleDeposit(parts);
            }
            else if (action.equals("withdraw")) {
                HandleWithdraw(parts);
            }
            else if (action.equals("transfer")) {
                HandleTransfer(parts);
            }
            else {
                System.out.println("Invalid Command.");
            }
        }
    }

    private static void HandleRegister(String[] parts) {
        if (parts.length < 5) {
            System.out.println("Error: Invalid register format.");
            return;
        }

        String username = parts[1];
        String password = parts[2];
        String email = parts[parts.length - 1];
        String phoneNumber = parts[parts.length - 2];

        StringBuilder fullNameBuilder = new StringBuilder();
        for (int i = 3; i < parts.length - 2; i++) {
            fullNameBuilder.append(parts[i]);
            if (i < parts.length - 3) {
                fullNameBuilder.append(".");
            }
        }
        String fullName = fullNameBuilder.toString();

        for (String[] user : users) {
            if (user[0].equals(username)) {
                System.out.println("Error: username already exists.");
                return;
            }
        }
        if (!PasswordValidity(password)) {
            System.out.println("Error: Invalid password format.");
            return;
        }
        if (!PhoneNumberValidity(phoneNumber)) {
            System.out.println("Error: Invalid phone number.");
            return;
        }
        if (!EmailValidity(email)) {
            System.out.println("Error: Invalid email format.");
            return;
        }

        String cardNumber = GenerateUniqueCardNumber();
        String initialBalance = "0";

        String[] newUser = {username, password, fullName, phoneNumber, email, cardNumber, initialBalance};
        users.add(newUser);

        System.out.println("Registered successfully.");
        System.out.println("Assigned card number: " + cardNumber);
    }

    private static void HandleLogin(String[] parts) {
        if (parts.length != 3) {
            System.out.println("Error: Invalid login format.");
            return;
        }

        if (currentUser != null) {
            System.out.println("Error: You are already logged in. Please logout first.");
            return;
        }
        String username = parts[1];
        String password = parts[2];

        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                currentUser = user;
                System.out.print("Login successful.");
                return;
            }
        }
        System.out.println("Error: Invalid username or password.");
    }

    private static void HandleLogout() {
        if (currentUser == null) {
            System.out.println("Error: You alre not logged in.");
        }
        else {
            currentUser = null;
            System.out.println("Logout successful.");
        }
    }

    private static void HandleShowBalance() {
        if (currentUser == null) {
            System.out.println("Error: You Should login first.");
            return;
        }
        System.out.println("Current balance: " + Double.parseDouble(currentUser[6]));
    }

    private static void HandleDeposit(String[] parts) {
        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }
        if (parts.length != 2) {
            System.out.println("Error: Invalid command format.");
            return;
        }

        double amount = Double.parseDouble(parts[1]);
        double currentBalance = Double.parseDouble(currentUser[6]);
        double newBalance = currentBalance + amount;

        currentUser[6] = String.valueOf(newBalance);
        System.out.println("Deposit successful. Current balance: " + newBalance);
    }

    private static void HandleWithdraw(String[] parts) {
        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }
        if (parts.length != 2) {
            System.out.println("Error: Invalid command format.");
            return;
        }

        double amount = Double.parseDouble(parts[1]);
        double currentBalance = Double.parseDouble(currentUser[6]);

        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            currentUser[6] = String.valueOf(newBalance);
            System.out.println("Withdrawal Successful. Current balance: " + newBalance);
        }
        else {
            System.out.println("Error: Not enough balance.");
        }
    }

    private static void HandleTransfer(String[] parts) {
        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }
        if (parts.length != 3) {
            System.out.println("Error: Invalid transfer format.");
            return;
        }

        String destCardNumber = parts[1];
        double amount = Double.parseDouble(parts[2]);
        double currentBalance = Double.parseDouble(currentUser[6]);

        String[] destUser = null;
        for (String[] user : users) {
            if (user[5].equals(destCardNumber)) {
                destUser = user;
                break;
            }
        }
        if (destUser == null) {
            System.out.println("Error: Destination card number not found.");
            return;
        }
        if (currentBalance < amount) {
            System.out.println("Error: Not enough balance.");
            return;
        }
        double newSourceBalance = currentBalance - amount;
        currentUser[6] = String.valueOf(newSourceBalance);

        double destBalannce = Double.parseDouble(destUser[6]);
        double newDestBalance = destBalannce + amount;
        destUser[6] = String.valueOf(newDestBalance);

        System.out.println("Transferred successfully.");
    }

    private static boolean PasswordValidity(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch))  hasDigit = true;
            else hasSpecial = true; // if not alphabet or digit, then it's special char.
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private static boolean PhoneNumberValidity(String phone) {
        if (phone.length() != 11) return false;
        if (!phone.startsWith("09")) return false;

        for (int i = 0; i <phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean EmailValidity(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex < 1) return false;
        if (email.startsWith(".")) return false;
        String domain = email.substring(atIndex + 1);
        return domain.equals("aut.com");
    }

    private static String GenerateUniqueCardNumber() {
        Random random = new Random();
        while (true) {
            StringBuilder sb = new StringBuilder("6037");
            for (int i = 0; i < 12; i++) {
                sb.append(random.nextInt(10));
            }
            String generated = sb.toString();

            boolean uniqueness = false;
            for (String[] user: users) {
                if (user[5].equals(generated)) {
                    uniqueness = true;
                    break;
                }
            }
            if (!uniqueness) return generated;
        }
    }
}
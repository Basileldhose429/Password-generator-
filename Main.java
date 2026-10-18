import java.util.Scanner;

/** Console interface for the Password Generator. */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PasswordGenerator generator = new PasswordGenerator();

        System.out.println("=====================================");
        System.out.println("        PASSWORD GENERATOR");
        System.out.println("=====================================");

        boolean again = true;
        while (again) {
            int length = readLength(sc);
            boolean upper   = readYesNo(sc, "Include uppercase letters (A-Z)?");
            boolean lower   = readYesNo(sc, "Include lowercase letters (a-z)?");
            boolean numbers = readYesNo(sc, "Include numbers (0-9)?");
            boolean special = readYesNo(sc, "Include special characters (!@#$...)?");

            try {
                String password = generator.generate(length, upper, lower, numbers, special);
                System.out.println("\nGenerated Password : " + password);
                System.out.println("Strength           : " + PasswordGenerator.rateStrength(password));
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
            }

            again = readYesNo(sc, "\nGenerate another password?");
            System.out.println();
        }
        System.out.println("Goodbye! Stay secure.");
        sc.close();
    }

    private static int readLength(Scanner sc) {
        while (true) {
            System.out.print("Enter password length (" + PasswordGenerator.MIN_LENGTH
                    + "-" + PasswordGenerator.MAX_LENGTH + "): ");
            String line = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n >= PasswordGenerator.MIN_LENGTH && n <= PasswordGenerator.MAX_LENGTH) return n;
            } catch (NumberFormatException ignored) { }
            System.out.println("Invalid length, try again.");
        }
    }

    private static boolean readYesNo(Scanner sc, String question) {
        while (true) {
            System.out.print(question + " (y/n): ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no"))  return false;
            System.out.println("Please type y or n.");
        }
    }
}

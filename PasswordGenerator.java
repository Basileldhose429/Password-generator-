import java.security.SecureRandom;

/**
 * Core logic: builds a random password from the character types the user selects.
 * Demonstrates classes, objects, methods, strings, arrays, loops and conditionals.
 */
public class PasswordGenerator {

    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>?/";

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 128;

    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a password.
     * Guarantees at least one character from every selected type.
     *
     * @throws IllegalArgumentException if length is out of range or no type is selected
     */
    public String generate(int length, boolean useUpper, boolean useLower,
                           boolean useNumbers, boolean useSpecial) {

        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                "Length must be between " + MIN_LENGTH + " and " + MAX_LENGTH + ".");
        }

        // Collect the selected character sets
        String[] sets = new String[4];
        int count = 0;
        if (useUpper)   sets[count++] = UPPER;
        if (useLower)   sets[count++] = LOWER;
        if (useNumbers) sets[count++] = NUMBERS;
        if (useSpecial) sets[count++] = SPECIAL;

        if (count == 0) {
            throw new IllegalArgumentException("Select at least one character type.");
        }

        // Combined pool of all allowed characters
        StringBuilder pool = new StringBuilder();
        for (int i = 0; i < count; i++) {
            pool.append(sets[i]);
        }

        char[] password = new char[length];

        // Step 1: one guaranteed character from each selected set
        for (int i = 0; i < count; i++) {
            password[i] = sets[i].charAt(random.nextInt(sets[i].length()));
        }

        // Step 2: fill the rest from the full pool
        for (int i = count; i < length; i++) {
            password[i] = pool.charAt(random.nextInt(pool.length()));
        }

        // Step 3: shuffle (Fisher-Yates) so guaranteed characters aren't always first
        for (int i = length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = password[i];
            password[i] = password[j];
            password[j] = temp;
        }

        return new String(password);
    }

    /** Simple strength rating based on length and variety of characters. */
    public static String rateStrength(String password) {
        boolean up = false, low = false, num = false, spec = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) up = true;
            else if (Character.isLowerCase(c)) low = true;
            else if (Character.isDigit(c)) num = true;
            else spec = true;
        }
        int types = (up ? 1 : 0) + (low ? 1 : 0) + (num ? 1 : 0) + (spec ? 1 : 0);
        int len = password.length();

        if (len >= 16 && types >= 3) return "Very Strong";
        if (len >= 12 && types >= 3) return "Strong";
        if (len >= 8  && types >= 2) return "Medium";
        return "Weak";
    }
}

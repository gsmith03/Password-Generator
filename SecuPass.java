import java.util.Scanner;

/*
********** How to Create a Random Character **********

        count = (max - min) + 1
        randomNumber = (int)(Math.random() * count + min)
        Math.random() → gives 0.0 to <1.0
        Multiply by count → gives 0 to <count
        Cast to int → gives 0 to (count - 1)
        Add min → shifts into correct ASCII range

*/

public class SecuPass {

    // 1. Get password requirements
    public static int[] getPasswordRequirements(Scanner input) {

        int[] requirements = new int[5];

        // Ask user for password requirements
         int passwordLength = getNumberCheck(input, "What is the desired length of your password? ", 1);
         int uppers = getNumberCheck(input, "How many Uppercase letters would you like? ", 0);
         int lowers = getNumberCheck(input, "How many Lowercase letters would you like? ", 0);
         int digits = getNumberCheck(input, "How many digits would you like? ", 0);
         int specials = getNumberCheck(input, "How many special characters would you like? ", 0);

        int characterCount = uppers + lowers + digits + specials;
        while (characterCount > passwordLength) {
            System.out.printf("%nInvalid. Your desired password length is: %d, but you entered" +
                    " %d desired characters. %n", passwordLength, characterCount);

            passwordLength = getNumberCheck(input, "What is the desired length of your password? ", 1);
            uppers = getNumberCheck(input, "How many Uppercase letters would you like? ", 0);
            lowers = getNumberCheck(input, "How many Lowercase letters would you like? ", 0);
            digits = getNumberCheck(input, "How many digits would you like? ", 0);
            specials = getNumberCheck(input, "How many special characters would you like? ", 0);

            characterCount = uppers + lowers + digits + specials;
        }

        // Add user inputs to the requirements array
        requirements[0] = passwordLength;
        requirements[1] = uppers;
        requirements[2] = lowers;
        requirements[3] = digits;
        requirements[4] = specials;

        // Return requirements array
        return requirements;
    }

    // 2. Fill the required characters
    public static int fillRequiredCharacters(char[] password, int upper, int lower, int digit, int special) {

        int currentIndex = 0;

        for (int i = 0; i < upper; i++) {
            password[currentIndex] = generateUpper();
            currentIndex++;
        }

        for (int i = 0; i < lower; i++) {
            password[currentIndex] = generateLower();
            currentIndex++;
        }

        for (int i = 0; i < digit; i++) {
            password[currentIndex] = generateDigit();
            currentIndex++;
        }

        for (int i = 0; i < special; i++) {
            password[currentIndex] = generateSpecial();
            currentIndex++;
        }

       return currentIndex;
    }

    // 3. upper characters
    public static char generateUpper() {
       int randomUpper = (int)(Math.random() * 26 + 65);
       return (char)randomUpper;
    }

    // 4. lower characters
    public static char generateLower() {
        int randomLower = (int)(Math.random() * 26 + 97);
        return (char)randomLower;
    }

    // 5. digits
    public static char generateDigit() {
        int randomDigit = (int)(Math.random() * 10 + 48);
        return (char)randomDigit;
    }

    // 6. special characters
    public static char generateSpecial() {
        char[] symbols = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-',
                '_', '=', '+', '[', ']', '{', '}', '\\',';', ':', '.',
                '<', '>', '/', '?', '`' ,'~'};
        int randomSpecial = (int)(Math.random() * symbols.length);
        return symbols[randomSpecial];
    }

    // 7. Helper method to whether the number is valid
    public static int getNumberCheck(Scanner input, String prompt, int minimumNumber ) {
        System.out.print(prompt);
        int desiredNumber = input.nextInt();
        while (desiredNumber < minimumNumber) {
            System.out.printf("Invalid entry. Number must be at least %d.Please try again: ", minimumNumber);
            desiredNumber = input.nextInt();

        }
        return desiredNumber;
    }

    // 8. Fill remaining characters
    public static void fillRemainingCharacters(char[] password, int indexStart) {
        for (int i = indexStart; i < password.length; i++) {
            // password[i] = (char)(Math.random() * 26 + 97);
            int category = (int)(Math.random() * 3) + 1;

            if (category == 1) {
                password[i] = generateUpper();
            }
            else if (category == 2) {
                password[i] = generateLower();
            }
            else {
                password[i] = generateDigit();
            }
        }
    }

    // 9. Shuffle password
    public static void shufflePassword(char[] password) {
        // Swap positions of elements in the array
        for (int i = 0; i < password.length; i ++) {
            // Assign value stored in starting index assigned to temp variable
            char temp = password[i];
            // Generate a random index to swap with
            int randomIndex = (int)(Math.random() * password.length);
            // Assign the value stored in random index to the starting index
            password[i] = password[randomIndex];
            // Assign value stored in temp variable to random index
            password[randomIndex] = temp;
        }
    }

    // 10. Print password
    public static void printPassword(char[] password) {
        String charPassword  = new String(password);
        System.out.println("Your new password is: " + charPassword);
    }

    // Ask if user wants a reshuffle
    public static void reshufflePassword(Scanner input, char[] password) {
        System.out.print("Would you like to reshuffle your password? Y/N: ");
        String reshuffle = input.next();

        // Check that entry is valid. If not, ask user to reenter.
        while (!((reshuffle.equalsIgnoreCase("y")||(reshuffle.equalsIgnoreCase("n"))))) {
            System.out.println("Invalid input. Please try again. ");
            System.out.print("Would you like to reshuffle your password? Y/N: ");
            reshuffle = input.next();
        }

        // For valid entries, reshuffle and reprint.
        while (reshuffle.equalsIgnoreCase("y")) {

            shufflePassword(password);
            printPassword(password);
            // Ask again if user wants to reshuffle
            System.out.print("Would you like to reshuffle your password? Y/N: ");
            reshuffle = input.next();
            // Check that entry is valid.
            while (!((reshuffle.equalsIgnoreCase("y")||(reshuffle.equalsIgnoreCase("n"))))) {
                System.out.println("Invalid input. Please try again. ");
                System.out.print("Would you like to reshuffle your password? Y/N: ");
                reshuffle = input.next();
            }

        }
    }

    public static void main(String[] args) {

        // Create scanner and requirements array
        Scanner input = new Scanner(System.in);

        int[] requirements = getPasswordRequirements(input);

        int passwordLength = requirements[0];
        int upper = requirements[1];
        int lower = requirements[2];
        int digit = requirements[3];
        int special = requirements[4];

        char[] password = new char[passwordLength];

        int startIndex = fillRequiredCharacters(password, upper, lower, digit, special);
        fillRemainingCharacters(password, startIndex);
        shufflePassword(password);
        printPassword(password);
        reshufflePassword(input, password);
        printPassword(password);

        input.close();

    }

}

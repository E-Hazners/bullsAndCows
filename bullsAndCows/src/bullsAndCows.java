import java.util.*;
import java.util.Random;

public class bullsAndCows {
    public static void main(String[] args) {

//        asking user for a code's length
        int secretNum, amountOfSymbols = 0;
        secretNum = askNumber();
        if (secretNum == 0) {
        } else {
            amountOfSymbols = askSymbols();
            if(amountOfSymbols == 0) {
            } else if (amountOfSymbols < secretNum) {
                System.out.println("Error: it's not possible to generate a code with a length of " + secretNum + " with " + amountOfSymbols + " unique symbols.");
            } else if (secretNum > 0){
                // creating a new arr where will be placed secret code
                char[] secretNumArr = new char[secretNum];

                generateNumbers(secretNum, secretNumArr, amountOfSymbols);

                System.out.println("Okay, let's start a game!");

                int bulls = 0;
                int turn = 1;
                while(bulls != secretNum) {
                    System.out.println("Turn " + turn);
                    bulls = game(secretNum, secretNumArr);
                    if (bulls == -1) {
                        break;
                    }
                    turn++;
                }
            }
        }


    }
//    method for inputting code's length
    public static int askNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int secretNum = 0;
        while (true) {
            try {
                secretNum = Integer.parseInt(input);
                if (secretNum > 0) {
                    break;
                } else {
                    System.out.println("Error: You can write only positive length");
                    return 0;
                }

                // not positive.
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", input);
                return 0;
            }
        }

        if (secretNum >= 37) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)");
            return 0;
        } else {
            return secretNum;
        }
    }

    // method for asking user for the number of possible symbols
    public static int askSymbols() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the number of possible symbols in the code:");
        String input = scanner.nextLine();
        int amountOfSymbols = 0;
        while (true) {
            try {
                amountOfSymbols = Integer.parseInt(input);
                if (amountOfSymbols > 0) {
                    break;
                } else {
                    System.out.println("Error: You can write only positive length");
                    return 0;
                }

                // not positive.
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", input);
                return 0;
            }
        }

        if (amountOfSymbols >= 37) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)");
            return 0;
        } else {
            return amountOfSymbols;
        }
    }

    //method for generating numbers and creating number combination
    public static void generateNumbers(int secretNum, char[] secretNumArr, int amountOfSymbols) {
        Random rand = new Random();
        ArrayList<Character> numbers = new ArrayList<>();
        Collections.addAll(numbers, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        ArrayList<Character> alphabet = new ArrayList<>();
        Collections.addAll(alphabet, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
        Collections.addAll(alphabet, 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't');
        Collections.addAll(alphabet, 'u', 'v', 'w', 'x', 'y', 'z');

        if (amountOfSymbols >= 10) {
            for (int i = 10; i < amountOfSymbols; i++) {
                numbers.add(alphabet.get(i-10));
            }
        }

        ArrayList<Character> secretNumArrayList = new ArrayList<>();
        int randomInt;
        for (int i = 0; i < secretNum; i++) {
            randomInt = rand.nextInt(numbers.size() - 1);
            while (secretNumArrayList.contains(numbers.get(randomInt))) {
                randomInt = rand.nextInt(numbers.size() - 1);
            }
            secretNumArrayList.add(numbers.get(randomInt));
            secretNumArr[i] = numbers.get(randomInt);
        }
        System.out.println("The secret is prepared: ");
        for(int i = 0; i < secretNumArr.length; i++) {
            System.out.print("*");
        }
        System.out.println(" (0-9, a-" + numbers.get(numbers.size() - 1) + ").");
    }

    // method for asking user for number and adding his cows and bulls
    public static int game(int secretNum, char[] secretNumArr){
        Scanner scanner = new Scanner(System.in);

        StringBuilder secretCode = new StringBuilder();
        for (char j : secretNumArr) {
            secretCode.append(j);
        }
        String givenNumber = scanner.nextLine();

        if (check(givenNumber)) {
            // iterating number of bulls and cows
            int bulls = 0;
            int cows = 0;

            // going through loop and checking the values;
            for (int i = 0; i < secretNum; i++) {
                if (givenNumber.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                } else {
                    for (int j = 0; j < secretNum; j++) {
                        if (givenNumber.charAt(i) == secretCode.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }

            String gradeString = grade(bulls, cows);
            System.out.println(gradeString);


            return bulls;
        } else {
            return -1;
        }

    }

    // method for output which contains users result
    public static String grade(int bulls, int cows) {
        StringBuilder bullsString = new StringBuilder();
        StringBuilder cowString = new StringBuilder();
        switch (bulls){
            case 0:
                break;
            case 1:
                bullsString.append("1 bull");
                break;
            default:
                bullsString.append(bulls).append(" bulls");
                break;
        }
        switch (cows) {
            case 0:
                break;
            case 1:
                cowString.append("1 cow");
                break;
            default:
                cowString.append(cows).append(" cows");
                break;
        }

        String gradeString;
        if (cows == 1 && bulls == 1) {
            gradeString = "Grade: " + bullsString + " and " + cowString;
        } else if (cows == 0 && bulls == 0) {
            gradeString = "Grade: None";
        } else if (cows == 0 || bulls == 0) {
            gradeString = "Grade: " + bullsString + "" + cowString;
        } else {
            gradeString = "Grade: " + bullsString + " and " + cowString;
        }
        return gradeString;
    }

    // method checks if the given string contains only numbers or integers
    static boolean check(String givenNumber) {
        if (givenNumber == null) // checks if the String is null {
            return false;

        int len = givenNumber.length();
        for (int i = 0; i < len; i++) {
            // checks whether the character is neither a letter nor a digit
            // if it is neither a letter nor a digit then it will return false
            if ((!Character.isLetterOrDigit(givenNumber.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
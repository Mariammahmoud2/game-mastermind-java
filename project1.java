     
package  project1;

 
import java.util.Random;
import java.util.Scanner;

public class  project1 {
    private static final int CODE_LENGTH = 4; // طول الشفرة
    private static final int MAX_ATTEMPTS = 10; // عدد المحاولات
    private static final int NUM_COLORS = 6; // عدد الألوان المتاحة
    private final char[] code = new char[CODE_LENGTH]; // الشفرة السرية
    private final char[] colors = { 'R', 'G', 'B', 'Y', 'O', 'P' }; // الألوان المتاحة
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    // توليد شيفرة سرية عشوائية
    private void generateCode() {
        for (int i = 0; i < CODE_LENGTH; i++) {
            code[i] = colors[random.nextInt(NUM_COLORS)];
        }
    }

    // تقييم التخمين
    private void evaluateGuess(char[] guess, int[] balls) {
        balls[0] = 0; // blackball
        balls[1] = 0; // Whiteball

        // حساب المطابقات في المكان الصحيح
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guess[i] == code[i]) {
                balls[0]++;
            } else {
                for (int j = 0; j < CODE_LENGTH; j++) {
                    if (guess[i] == code[j]) {
                        balls[1]++;
                        break;
                    }
                }
            }
        }
    }

    // طباعة الشفرة السرية
    private void printCode() {
        for (char c : code) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private boolean isValidColor(char color) {
        for (char c : colors) {
            if (color == c) {
                return true;
            }
        }
        return false;
    }

    private boolean askToPlayAgain() {
        System.out.print("\nDo you want to play again? (Y/N): ");
        char choice = scanner.nextLine().toUpperCase().charAt(0);

        while (choice != 'Y' && choice != 'N') {
            System.out.print("Invalid input. Please enter Y or N: ");
            choice = scanner.nextLine().toUpperCase().charAt(0);
        }

        return choice == 'Y';
    }

    public void play() {
        try (scanner) {
            System.out.println("Welcome to Mastermind!");
            System.out.println("Available colors: R, G, B, Y, O, P");
            System.out.println("Guess the code of 4 colors.");
            do {
                generateCode();
                
                for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
                    char[] guess = new char[CODE_LENGTH];
                    System.out.println("\nAttempt " + attempt + "/" + MAX_ATTEMPTS + ": ");
                    
                    for (int i = 0; i < CODE_LENGTH; i++) {
                        System.out.print("Enter color for position " + (i+1) + ": ");
                        try
                        {
                            guess[i] = scanner.nextLine().toUpperCase().charAt(0);
                        }
                        catch(Exception e)
                        {
                            i--;
                            continue;
                            
                        }
                        
                        
                        if (!isValidColor(guess[i])) {
                            System.out.println("Invalid color. Please enter only R, G, B, Y, O, P.");
                            i--;
                        }
                    }
                    
                    int[] balls = new int[2]; // balls[0] = Blackball, balls[1] = Whiteball
                    evaluateGuess(guess, balls);
                    
                    if (balls[0] == CODE_LENGTH) { 
                        System.out.println("Congratulations! You guessed the code.");
                        break;
                    } else {
                        System.out.println("Black Ball: " + balls[0] + ", White Ball: " + balls[1]);
                        if (attempt == MAX_ATTEMPTS) {
                            System.out.println("\nGame Over! The correct code was: ");
                            printCode();
                            System.out.println("Better luck next time!");
                        }
                    }
                }
            } while (askToPlayAgain());
        }
    }

    public static void main(String[] args) {
         project1 game = new  project1();
        game.play();
    }
}
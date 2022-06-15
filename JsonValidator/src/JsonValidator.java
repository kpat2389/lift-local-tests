import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

public class JsonValidator {

    private static void version() {
        System.out.println(1);
    }

    private static void applicable() {
        System.out.println("true");
    }

    private static void run(Scanner sc) {
        Stack<String> storeBrackets = new Stack<>();
        while(sc.hasNext()) {
            String[] splitInformation = sc.nextLine().split("\\s+");
            for (int i = 0; i < splitInformation.length; i++) {
                if ((splitInformation[i].equals("{") || splitInformation[i].equals("["))) {
                    storeBrackets.push(splitInformation[i]);
                    if (splitInformation.length != (i + 1)) {
                        System.out.println("No");
                        return;
                    }
                }
                if (splitInformation[i].equals("}") || splitInformation[i].equals("},")) {
                    if (storeBrackets.isEmpty() || !storeBrackets.pop().equals("{")) {
                        System.out.println("No");
                        return;
                    }
                }
                if (splitInformation[i].equals("]") || splitInformation[i].equals("],")) {
                    if (storeBrackets.isEmpty() || !storeBrackets.pop().equals("[")) {
                        System.out.println("No");
                        return;
                    }
                }
            }
        }
        System.out.println("Yes");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String command = sc.next();
            if (command.equals("version")) {
                version();
            } else if (command.equals("applicable")) {
                applicable();
            } else if (command.equals("run")) {
                System.out.println("Please entire the path to a valid txt file: ");
                File file = new File("JsonValidator/src/test.txt");
                Scanner scFile = new Scanner(file);
                run(scFile);
            } else if (command.equals("q")) {
                System.out.println("Program ended");
                break;
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }
}

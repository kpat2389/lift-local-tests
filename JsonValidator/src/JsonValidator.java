import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class JsonValidator {

    private static boolean checkBrackets(Stack<String> storeBrackets, String[] splitInformation, Scanner sc) {
        for (int i = 0; i < splitInformation.length; i++) {
            if ((splitInformation[i].equals("{") || splitInformation[i].equals("["))) {
                storeBrackets.push(splitInformation[i]);
                if (splitInformation.length != (i + 1)) {
                    return false;
                }
            }
            if (splitInformation[i].equals("}") || splitInformation[i].equals("},")) {
                if (storeBrackets.isEmpty() || !storeBrackets.pop().equals("{")) {
                    return false;
                }
            }
            if (splitInformation[i].equals("]") || splitInformation[i].equals("],")) {
                if (storeBrackets.isEmpty() || !storeBrackets.pop().equals("[")) {
                    return false;
                }
            }
            if (splitInformation[i].equals("}") && (sc.hasNext() || splitInformation.length != (i + 1))) {
                return false;
            }
            if ((splitInformation[i].equals("],") || splitInformation[i].equals("},")) && (!sc.hasNext() ||
                    splitInformation.length != (i + 1))) {
                return false;
            }
        }
        return true;
    }

    private static void version() {
        System.out.println(1);
    }

    private static void applicable() {
        System.out.println("true");
    }

    private static void run(Scanner sc, File file) {
        boolean valid = true;
        int line = 1;
        ArrayList<String> brackets = new ArrayList<>();
        brackets.add("["); brackets.add("{"); brackets.add("]"); brackets.add("}"); brackets.add("],"); brackets.add("},");

        Stack<String> storeBrackets = new Stack<>();
        while(sc.hasNext()) {
            String[] splitInformation = sc.nextLine().replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
            System.out.println(splitInformation[0]);
            if (splitInformation.length > 2 || (!brackets.contains(splitInformation[0]) && splitInformation[0].charAt(0)
                    != '\"')) {
                valid = false;
                break;
            }
            if (!checkBrackets(storeBrackets, splitInformation, sc)) {
                valid = false;
                break;
            }
            if (splitInformation[0].charAt(0) == '\"' && (splitInformation[0].charAt(splitInformation[0].length() - 2)
                    != '\"' || splitInformation[0].charAt(splitInformation[0].length() - 1) != ':')) {
                valid = false;
                break;
            }
            String checker = splitInformation[splitInformation.length - 1];
            if ((sc.hasNext("]") || sc.hasNext("],") || sc.hasNext("}") || sc.hasNext("},"))
                    && checker.charAt(checker.length() - 1) != '\"') {
                valid = false;
                break;
            }
            line++;
        }
        if (!storeBrackets.isEmpty()) {
            valid = false;
        }
        if (!valid) {
            String output = "[ { \"type\": \"Invalid Json format\"\n";
            output = output + "    \"message\": \"Your txt file is not in Json format\"\n";
            output = output + "    \"file\": \"" + file.getPath() + "\"\n";
            output = output + "    \"line\": " + line + "\n";
            output = output + "    \"details_url\": null\n";
            output = output + "  }\n";
            output = output + "]\n";
            System.out.println(output);
        } else {
            System.out.println("This txt file is correctly formatted.");
        }
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
                System.out.println("Please entire the path to a txt file: ");
                File file = new File("JsonValidator/src/test.txt");
                Scanner scFile = new Scanner(file);
                run(scFile, file);
            } else if (command.equals("q")) {
                System.out.println("Program ended");
                break;
            } else {
                System.out.println("Please enter a valid command.");
            }
        }
    }
}

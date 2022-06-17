import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonChecker {

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

    private static Optional<ToolNote> run(Scanner sc, File file) {
        boolean valid = true;
        int line = 1;
        ArrayList<String> brackets = new ArrayList<>();
        brackets.add("["); brackets.add("{"); brackets.add("]"); brackets.add("}"); brackets.add("],"); brackets.add("},");

        Stack<String> storeBrackets = new Stack<>();
        while(sc.hasNext()) {
            String[] splitInformation = sc.nextLine().replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
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
            ToolNote addNote = new ToolNote(file.getPath(), line);
            return Optional.of(addNote);
        }
        return Optional.empty();
    }

    public static void main(String[] args) throws IOException {
        List<ToolNote> store = new ArrayList<>();
        switch (args[0]) {
            case "version" -> version();
            case "applicable" -> applicable();
            case "run" -> {
                File directoryPath = new File(args[1]);
                File[] allFiles = directoryPath.listFiles();
                if (allFiles == null) {
                    return;
                }
                for (File allFile : allFiles) {
                    if (allFile.getName().endsWith(".txt")) {
                        Scanner scFile = new Scanner(allFile);
                        run(scFile, allFile).ifPresent(store::add);
                    }
                }
                StringBuilder output = new StringBuilder();
                output.append("[\n");
                for (int i = 0; i < store.size(); i++) {
                    output.append("{ \"type\": \"Invalid Json format\",\n");
                    output.append("  \"message\": \"Your txt file is not in Json format\",\n");
                    output.append("  \"file\": \"").append(store.get(i).filePath).append("\",\n");
                    output.append("  \"line\": ").append(store.get(i).lineNumber).append(",\n");
                    output.append("  \"details_url\": null\n");
                    if (i != store.size() - 1) {
                        output.append("},\n");
                    } else {
                        output.append("}\n");
                    }
                }
                output.append("]\n");
                System.out.println(output);
            }
            default -> System.out.println("Please enter a valid command.");
        }
    }
}

package ModuleFour;

import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <Java source file>");
            System.exit(1);
        }
        String filename = args[0];
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("The number of keywords in " + filename + " is " + countKeywords(file));
        } else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        String[] keywordString = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum", "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        try (Scanner input = new Scanner(file)) {
			boolean insideComment = false;
			while (input.hasNextLine()) {
			    String line = input.nextLine().trim();
			    StringBuilder sb = new StringBuilder(line);
			    // Check for comments and remove them
			    int index = sb.indexOf("//");
			    if (index != -1) {
			        sb.delete(index, sb.length());
			    }
			    if (sb.indexOf("/*") != -1) {
			        insideComment = true;
			        sb.delete(0, sb.indexOf("/*") + 2);
			    }
			    if (insideComment) {
			        if (sb.indexOf("*/") != -1) {
			            insideComment = false;
			            sb.delete(0, sb.indexOf("*/") + 2);
			        } else {
			            continue;
			        }
			    }
			    // Check for strings and remove them
			    while (sb.indexOf("\"") != -1) {
			        sb.delete(0, sb.indexOf("\"") + 1);
			        if (sb.indexOf("\"") != -1) {
			            sb.delete(0, sb.indexOf("\"") + 1);
			        }
			    }
			    String[] words = sb.toString().split("\\s+");
			    for (String word : words) {
			        if (keywordSet.contains(word)) {
			            count++;
			        }
			    }
			}
		}
        return count;
    }
}
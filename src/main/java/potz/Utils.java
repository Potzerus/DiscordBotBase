package potz;

import java.util.Scanner;

public class Utils {

    public static String trimMention(String untrimmed) {
        if (untrimmed.startsWith("<@!") && untrimmed.endsWith(">")) {
            return untrimmed.substring(3, untrimmed.length() - 1);
        } else if (untrimmed.startsWith("<@") && untrimmed.endsWith(">")) {
            return untrimmed.substring(2, untrimmed.length() - 1);
         } else if (untrimmed.startsWith("<#") && untrimmed.endsWith(">")) {
            return untrimmed.substring(2, untrimmed.length() - 1);
        } else {
            return untrimmed;
        }
    }

    public static String[] parseArgsArray(String message) {
        Scanner ctrl = new Scanner(message);
        int indx;
        indx = 0;
        while (ctrl.hasNext()) {
            ctrl.next();
            indx++;
        }
        String[] args = new String[indx];
        ctrl = new Scanner(message);
        for (indx = 0; indx < args.length; indx++) {
            args[indx] = ctrl.next();
        }
        return args;
    }

}

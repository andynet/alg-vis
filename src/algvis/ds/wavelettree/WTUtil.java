package algvis.ds.wavelettree;

import java.util.List;
import java.util.Vector;

public class WTUtil {

    public static Vector<Character> getAlphabet(String s) {
        Vector<Character> result = new Vector<>();
        for (int i = 0; i < s.length(); i++) {
            if (!result.contains(s.charAt(i))) {
                result.add(s.charAt(i));
            }
        }
        return result;
    }

    public static String getBitstring(String s, List<Character> part2) {
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            sb.append(part2.contains(c) ? "1" : "0");
        }
        return sb.toString();
    }

    public static String getPart(String s, String bits, char bit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            if (bit == bits.charAt(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

}

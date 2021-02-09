package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.ui.view.REL;

import java.util.*;

public class WaveletTreeConstruct extends Algorithm {
    private final WaveletTree WT;
    private String s;

    public WaveletTreeConstruct(WaveletTree WT, String s) {
        super(WT.panel);
        this.WT = WT;
        this.s = s;
    }

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

    public void createSplit(WaveletTreeNode node, String s) {
        node.setString(s);
        Vector<Character> alphabet = getAlphabet(s);
        if (alphabet.size() == 1) {
            node.setBits("");
            // addStep(node, REL.TOP, "trierootstart");
            node.reposition();
            pause();
        } else {
            Collections.sort(alphabet);
            List<Character> alphabet_part1 = alphabet.subList(0, alphabet.size() / 2);
            List<Character> alphabet_part2 = alphabet.subList(alphabet.size() / 2, alphabet.size());
            String bits = getBitstring(s, alphabet_part2);
            node.setBits(bits);
            // addStep(node, REL.TOP, "trierootstart");
            node.reposition();
            pause();

            String part1 = getPart(s, bits, '0');
            WaveletTreeNode u = new WaveletTreeNode(WT);
            u.setParent(node);
            node.setChild(u);
            createSplit(u, part1);

            String part2 = getPart(s, bits, '1');
            WaveletTreeNode w = new WaveletTreeNode(WT);
            w.setParent(node);
            u.setRight(w);
            createSplit(w, part2);
        }
    }

    @Override
    public void runAlgorithm () {
        addToScene(WT);
        WaveletTreeNode v = WT.getRoot();
        createSplit(v, s);
    }
}


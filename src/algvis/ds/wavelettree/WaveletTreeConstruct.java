package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.ui.view.REL;

import java.util.*;

public class WaveletTreeConstruct extends Algorithm {
    private final WaveletTree WT;
    private String s;
    private WaveletTreeNode node;

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
            addToScene(node);
            // addStep(node, REL.TOP, "trierootstart");
            pause();
        } else {
            List<Character> alphabet_part1 = alphabet.subList(0, alphabet.size() / 2);
            List<Character> alphabet_part2 = alphabet.subList(alphabet.size() / 2, alphabet.size());
            String bits = getBitstring(s, alphabet_part2);
            node.setBits(bits);
            addToScene(node);
            // addStep(node, REL.TOP, "trierootstart");
            pause();

            String part1 = getPart(s, bits, '0');
            WaveletTreeNode u = new WaveletTreeNode(WT);
            u.setParent(node);
            node.setChild(u);
            u.x = node.x - s.length() * 20;
            u.y = node.y + 80;
            createSplit(u, part1);

            String part2 = getPart(s, bits, '1');
            WaveletTreeNode w = new WaveletTreeNode(WT);
            w.setParent(node);
            u.setRight(w);
            w.x = node.x + s.length() * 20;
            w.y = node.y + 80;
            createSplit(w, part2);
        }
    }

    @Override
    public void runAlgorithm () {
        WaveletTreeNode v = WT.getRoot();
        v.x = 0;
        v.y = 0;
        createSplit(v, s);

        /*
        v.mark();
        addNote("waveletconstructnote");
        addStep(v, REL.TOP, "waveletrootstart");
        pause();
        v.unmark();

        node = new WaveletTreeNode(WT, s);
        node.setColor(NodeColor.INSERT);
        addToScene(node);
        node.goNextTo(v);



        v.setString("kshavbdfjb");


        addToScene(v);
        */


            // WaveletTreeNode w = new WaveletTreeNode(s);

        /*
        while (s.compareTo("$") != 0) {
            final char ch = s.charAt(0);
            node.setAndGoNextTo(s, v);
            TrieNode w = v.getChildWithCH(ch);
            if (w != null) {
                addStep(v, REL.TOP, "trieinsertwch", "" + ch);
            } else {
                addStep(v, REL.TOP, "trieinsertwoch", "" + ch);
                w = v.addChild(ch, node.x, node.y);
            }
            w.setColor(NodeColor.CACHED);
            WT.reposition();
            pause();
            v = w;
            v.setColor(NodeColor.INSERT);
            WT.reposition();
            s = s.substring(1);
        }
        node.setAndGoNextTo(s, v);
        final TrieNode w = v.getChildWithCH('$');
        if (w == null) {
            addStep(v, REL.TOP, "trieinserteow");
        } else {
            addStep(v, REL.TOP, "trieinsertneow");
        }
        */

        /*-style graph layou
        v.setColor(NodeColor.NORMAL);
        v = v.addChild('$', node.x, node.y);
        */

        // WT.reposition();
            // hw.setAndGoNextTo(s, v);
            // beforeReturn();
    }
}


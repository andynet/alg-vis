package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.core.Array;
import algvis.core.NodeColor;
import algvis.ui.view.REL;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.IntStream;

public class WaveletTreeConstruct extends Algorithm {
    private final WaveletTree WT;
    private String s;
    private WaveletTreeNode node;

    public WaveletTreeConstruct(WaveletTree WT, String s) {
        super(WT.panel);
        this.WT = WT;
        this.s = s;
    }

    Vector<Character> getAlphabet(String s) {
        Vector<Character> result = new Vector<>();
        for (int i = 0; i < s.length(); i++) {
            if (!result.contains(s.charAt(i))) {
                result.add(s.charAt(i));
            }
        }
        return result;
    }

    String getBitstring(String s, List<Character> part2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (part2.contains(s.charAt(i))) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    String create_part(String s, String bits, char bit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            if (bit == bits.charAt(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    @Override
    public void runAlgorithm() {
        // setHeader("wavelettreeconstruct", s.substring(0, s.length() - 1));

        WaveletTreeNode v = WT.getRoot();
        v.setString(s);

        Vector<Character> alphabet = getAlphabet(s);
        List<Character> alphabet_part1 = alphabet.subList(0, alphabet.size()/2);
        List<Character> alphabet_part2 = alphabet.subList(alphabet.size()/2, alphabet.size());
        String bits = getBitstring(s, alphabet_part2);
        v.setBits(bits);

        v.x = -15;
        v.y = 20;
        addToScene(v);


        String part1 = create_part(s, bits, '0');
        String part2 = create_part(s, bits, '1');

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
        pause();
        /*
        v.setColor(NodeColor.NORMAL);
        v = v.addChild('$', node.x, node.y);
        */

        WT.reposition();
        // hw.setAndGoNextTo(s, v);
        // beforeReturn();


    }
}

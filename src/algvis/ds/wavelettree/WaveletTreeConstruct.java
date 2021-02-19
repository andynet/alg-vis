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

    public void createSplit(WaveletTreeNode node, String s) {
        node.setString(s);
        node.setBits("");
        node.reposition();
        addStep(node, REL.TOP, "wtconstruct1");    // Create a node representing string
        pause();

        Vector<Character> alphabet = WTUtil.getAlphabet(s);
        if (alphabet.size() == 1) {
            node.setString(s);
            node.setBits(alphabet.get(0).toString());
            node.reposition();
            addStep(node, REL.TOP, "wtconstruct2", alphabet.get(0).toString());    // The node contains letter #1, therefore it is a leaf.
            pause();
        } else {
            Collections.sort(alphabet);
            List<Character> alphabet_part2 = alphabet.subList(alphabet.size() / 2, alphabet.size());
            String bits = WTUtil.getBitstring(s, alphabet_part2);
            node.setBits(bits);
            node.reposition();
            addStep(node, REL.TOP, "wtconstruct3");     // Split the alphabet to 2 parts. Construct a bitvector by assigning 0 to letters from first part and 1 to letters from second part.
            pause();

            String part1 = WTUtil.getPart(s, bits, '0');
            WaveletTreeNode u = new WaveletTreeNode(WT);
            u.setParent(node);
            node.setLeftChild(u);

            u.reposition();
            addStep(u, REL.TOP, "wtconstruct4");    // Letters from first part go left.
            pause();
            createSplit(u, part1);

            String part2 = WTUtil.getPart(s, bits, '1');
            WaveletTreeNode w = new WaveletTreeNode(WT);
            w.setParent(node);
            node.setRightChild(w);

            w.reposition();
            addStep(w, REL.TOP, "wtconstruct5");    // Letters from second part go right
            pause();
            createSplit(w, part2);
        }
    }

    @Override
    public void runAlgorithm () {
        WT.clear();
        addToScene(WT);
        WaveletTreeNode v = WT.getRoot();
        v.reposition();
        addStep(v, REL.TOP, "wtconstruct0", s);
        pause();
        createSplit(v, s);
    }
}


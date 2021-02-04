package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.ds.trie.TrieNode;
import algvis.ds.trie.TrieWordNode;
import algvis.ui.view.REL;

public class WaveletTreeConstruct extends Algorithm {
    private final WaveletTree WT;
    private String s;
    private WaveletTreeNode node;

    public WaveletTreeConstruct(WaveletTree WT, String s) {
        super(WT.panel);
        this.WT = WT;
        this.s = s;
    }

    @Override
    public void runAlgorithm() {
        // example in algvis.ds.trie.TrieInsert
        setHeader("wavelettreeconstruct", s.substring(0, s.length() - 1));

        WaveletTreeNode v = WT.getRoot();
        v.mark();
        addNote("waveletconstructnote");
        addStep(v, REL.TOP, "waveletrootstart");
        pause();
        v.unmark();

        node = new WaveletTreeNode(WT, s);
        node.setColor(NodeColor.INSERT);
        addToScene(node);
        node.goNextTo(v);

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

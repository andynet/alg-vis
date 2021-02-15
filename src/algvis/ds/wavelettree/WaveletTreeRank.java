package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.ui.view.REL;

public class WaveletTreeRank extends Algorithm {
    private final WaveletTree WT;
    private int index;
    private char letter;

    public WaveletTreeRank(WaveletTree WT, int index, char letter) {
        super(WT.panel);
        this.WT = WT;
        this.index = index;
        this.letter = letter;
    }

    public static int rank(String bits, int currentIndex, char rankOf) {
        int rank = 0;
        for (int i = 0; i < currentIndex; i++) {
            if (bits.charAt(i) == rankOf) {
                rank++;
            }
        }
        return rank;
    }

    @Override
    public void runAlgorithm () {
        WaveletTreeNode node = WT.getRoot();
        node.unmarkTree();
        addStep(node, REL.TOP, "wtrank0", "" + this.letter, "" + this.index, WT.getBinRepr(this.letter));
        pause();

        int index = this.index;
        addStep(node, REL.TOP, "wtrank1", "" + index);
        node.setMarkedPos(index);
        pause();

        String binRepr = WT.getBinRepr(letter);
        for (int i = 0; i < binRepr.length(); i++) {
            if (binRepr.charAt(i) == '0') {
                addStep(node, REL.TOP, "wtrank2", "" + this.letter, "0", "left");
                pause();

                int nextIndex = rank(node.getBits(), index, '0');
                node = node.getLeftChild();
                addStep(node, REL.TOP, "wtaccess3", "" + nextIndex, "0", "" + index);
                pause();
                node.setMarkedPos(nextIndex);
                pause();
                node.getParent().setMarkedPos(-1);
                index = nextIndex;
            }
            if (binRepr.charAt(i) == '1') {
                addStep(node, REL.TOP, "wtrank2", "" + this.letter, "1", "right");
                pause();

                int nextIndex = rank(node.getBits(), index, '1');
                node = node.getRightChild();
                addStep(node, REL.TOP, "wtaccess3", "" + nextIndex, "1", "" + index);
                pause();
                node.setMarkedPos(nextIndex);
                pause();
                node.getParent().setMarkedPos(-1);
                index = nextIndex;
            }
        }
        addStep(node, REL.TOP, "wtrank4", "" + this.letter, "" + this.index, "" + index);
        pause();
        node.setMarkedPos(-1);
    }
}

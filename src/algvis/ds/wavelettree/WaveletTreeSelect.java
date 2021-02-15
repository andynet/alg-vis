package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.ui.view.REL;

public class WaveletTreeSelect extends Algorithm {
    private final WaveletTree WT;
    private int rank;
    private char letter;

    public WaveletTreeSelect(WaveletTree WT, int rank, char letter) {
        super(WT.panel);
        this.WT = WT;
        this.rank = rank;
        this.letter = letter;
    }

    public WaveletTreeNode getLeaf(char letter) {
        WaveletTreeNode node = WT.getRoot();
        while (node.getLeftChild() != null) {
            int tmp = node.getString().indexOf(letter);
            char currentBit = node.getBits().charAt(tmp);
            if (currentBit == '0') {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return node;
    }

    public static int select(String bits, int rank, char ch) {
        int pos = -1;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == ch) {
                pos++;
                if (pos == rank) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void runAlgorithm () {
        // start at the bottom
        WaveletTreeNode node = getLeaf(letter);
        int rank = this.rank;
        node.setMarkedPos(rank);
        addStep(node, REL.TOP, "wtselect0", "" + letter);    // We start at the leaf representing the letter
        pause();

        while (node.getParent() != null) {
            if (node == node.getParent().getLeftChild()) {
                addStep(node, REL.TOP, "wtselect1", "left", "0");    // This is the #1 child with corresponding bit was #2.
                pause();
                int nextRank = select(node.getParent().getBits(), rank, '0');
                node.getParent().setMarkedPos(nextRank);
                addStep(node, REL.TOP, "wtselect2", "" + nextRank, "0", "" + rank);
                // Next position is #1, the select of #2 bit with rank #3 in the parent node
                pause();
                rank = nextRank;
                node.setMarkedPos(-1);
            } else {
                addStep(node, REL.TOP, "wtselect1", "right", "1");
                pause();
                int nextRank = select(node.getParent().getBits(), rank, '1');
                node.getParent().setMarkedPos(nextRank);
                addStep(node, REL.TOP, "wtselect2", "" + nextRank, "1", "" + rank);
                pause();
                rank = nextRank;
                node.setMarkedPos(-1);
            }
            node = node.getParent();
        }
        node.markLetter(rank);
        addStep(node, REL.TOP, "wtselect3", "" + rank);
        pause();
        node.unmarkLetter(rank);
        node.setMarkedPos(-1);
    }
}

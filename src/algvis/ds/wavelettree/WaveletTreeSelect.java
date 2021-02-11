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
        addStep(node, REL.TOP, "wtselect0");    // We start at the leaf representing the letter
        pause();

        // String repr = WT.getBinRepr(letter);
        while (node.getParent() != null) {
            if (node == node.getParent().getLeftChild()) {
                addStep(node, REL.TOP, "wtselect1_0");    // Corresponding bit was 0.
                pause();
                rank = select(node.getParent().getBits(), rank, '0');
                node.getParent().setMarkedPos(rank);
                addStep(node, REL.TOP, "wtselect2_0");    // Next position is the rank of 0 in the parent node
                pause();
            } else {
                addStep(node, REL.TOP, "wtselect1_1");    // Corresponding bit was 1.
                pause();
                rank = select(node.getParent().getBits(), rank, '1');
                node.getParent().setMarkedPos(rank);
                addStep(node, REL.TOP, "wtselect2_1");    // Next position is the rank of 1 in the parent node
                pause();
            }
            node = node.getParent();
        }
        node.markLetter(rank);
        addStep(node, REL.TOP, "wtselect3");    // We return the marked position
        pause();
    }
}

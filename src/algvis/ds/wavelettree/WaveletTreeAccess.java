package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.ui.view.REL;

public class WaveletTreeAccess extends Algorithm {
    private final WaveletTree WT;
    private int index;

    public WaveletTreeAccess(WaveletTree WT, int index) {
        super(WT.panel);
        this.WT = WT;
        this.index = index;
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

    public void walkDown(WaveletTreeNode node, int pos) {
        if (node.isLeaf()) {
            node.markLetter(0);
            addStep(node, REL.TOP, "wtaccess4");
            pause();
            node.unmarkLetter(0);
        } else {
            char bit = node.getBits().charAt(pos);
            node.markLetter(pos);
            addStep(node, REL.TOP, "wtaccess1", "" + pos);
            pause();

            if (bit == '0') {
                addStep(node, REL.TOP, "wtaccess2_0");
                pause();
                node.unmarkLetter(pos);

                WaveletTreeNode nextNode = node.getLeftChild();
                int nextPos = rank(node.getBits(), pos, '0');
                if (!nextNode.isLeaf()) {
                    node.setMarkedPos(pos + 1);
                    addStep(nextNode, REL.TOP, "wtaccess3_0");
                    pause();
                }
                walkDown(nextNode, nextPos);
            } else {
                addStep(node, REL.TOP, "wtaccess2_1");
                pause();
                node.unmarkLetter(pos);

                WaveletTreeNode nextNode = node.getRightChild();
                int nextPos = rank(node.getBits(), pos, '1');
                if (!nextNode.isLeaf()) {
                    node.setMarkedPos(pos + 1);
                    addStep(nextNode, REL.TOP, "wtaccess3_1");
                    pause();
                }
                walkDown(nextNode, nextPos);
            }
            node.setMarkedPos(-1);
        }
    }

    @Override
    public void runAlgorithm () {
        WaveletTreeNode root = WT.getRoot();
        addStep(root, REL.TOP, "wtaccess0");
        pause();
        walkDown(root, this.index);
    }
}

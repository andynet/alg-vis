package algvis.ds.wavelettree;

import algvis.core.Algorithm;

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

    @Override
    public void runAlgorithm () {
        WaveletTreeNode currentNode = WT.getRoot();
        int currentIndex = this.index;
        currentNode.markLetter(currentIndex);
        while (currentNode.getChild() != null) {
            char currentBit = currentNode.bits.charAt(currentIndex);
            if (currentBit == '0') {
                currentIndex = rank(currentNode.bits, currentIndex, '0');
                currentNode = currentNode.getChild();
            } else {
                currentIndex = rank(currentNode.bits, currentIndex, '1');
                currentNode = currentNode.getChild().getRight();
            }
            currentNode.markLetter(currentIndex);
        }
    }
}

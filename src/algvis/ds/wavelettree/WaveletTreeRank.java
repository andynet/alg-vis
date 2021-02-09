package algvis.ds.wavelettree;

import algvis.core.Algorithm;

public class WaveletTreeRank extends Algorithm {
    private final WaveletTree WT;
    private int index;

    public WaveletTreeRank(WaveletTree WT, int index) {
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
        while (currentNode.getLeftChild() != null) {
            char currentBit = currentNode.getBits().charAt(currentIndex);
            if (currentBit == '0') {
                currentIndex = rank(currentNode.getBits(), currentIndex, '0');
                currentNode = currentNode.getLeftChild();
            } else {
                currentIndex = rank(currentNode.getBits(), currentIndex, '1');
                currentNode = currentNode.getRightChild();
            }
            currentNode.markLetter(currentIndex);
        }
    }
}

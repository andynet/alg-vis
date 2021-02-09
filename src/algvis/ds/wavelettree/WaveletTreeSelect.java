package algvis.ds.wavelettree;

import algvis.core.Algorithm;

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
        WaveletTreeNode currentNode = getLeaf(letter);
        currentNode.markLetter(rank);
        while (currentNode.getParent() != null) {
            if (currentNode == currentNode.getParent().getLeftChild()) {
                rank = select(currentNode.getParent().getBits(), rank, '0');
            } else {
                rank = select(currentNode.getParent().getBits(), rank, '1');
            }
            currentNode = currentNode.getParent();
            currentNode.markLetter(rank);
        }
    }
}

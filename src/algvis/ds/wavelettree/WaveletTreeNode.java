package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.history.HashtableStoreSupport;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;
import java.util.BitSet;

import java.awt.*;
import java.util.Hashtable;

public class WaveletTreeNode extends Node {
    private WaveletTreeNode parent = null, leftChild = null, rightChild = null;
    private String string, bits;
    private BitSet markedLetters;
    private int markedPos = -1;
    int char_w = 10, char_h, box_w, box_h;

    public WaveletTreeNode(DataStructure D) {
        super(D, 0, 0, 50, ZDepth.NODE);
        char_h = Fonts.TYPEWRITER.fm.getHeight();
    }

    // <editor-fold desc="setters and getters">
    public void setParent(WaveletTreeNode node) { this.parent = node; }
    public WaveletTreeNode getParent() { return this.parent; }
    public void setLeftChild(WaveletTreeNode node) { this.leftChild = node; }
    public WaveletTreeNode getLeftChild() { return this.leftChild; }
    public void setRightChild(WaveletTreeNode node) { this.rightChild = node; }
    public WaveletTreeNode getRightChild() { return this.rightChild; }
    public void setString(String string) {
        this.string = string;
    }
    public String getString() { return this.string; }
    public void setBits(String bits) {
        this.bits = bits;
        this.markedLetters = new BitSet(bits.length());
    }
    public String getBits() { return this.bits; }
    // </editor-fold>

    // <editor-fold desc="drawing methods">
    public void drawTree(View v) {
        drawEdges(v);
        drawNodes(v);
    }

    public void drawEdges(View view) {
        if (this.getParent() != null) {
            view.setColor(Color.BLACK);
            view.drawLine(this.getParent().x, this.getParent().y, this.x, this.y);
        }
        if (this.getLeftChild() != null) {
            this.getLeftChild().drawEdges(view);
        }
        if (this.getRightChild() != null) {
            this.getRightChild().drawEdges(view);
        }
    }

    public void drawNodes(View view) {
        this.draw(view);
        if (this.getLeftChild() != null) {
            this.getLeftChild().drawNodes(view);
        }
        if (this.getRightChild() != null) {
            this.getRightChild().drawNodes(view);
        }
    }

    @Override   // Overrides method in Node
    public void draw(View v) {
        if (state == Node.INVISIBLE || getKey() == NULL || this.string == null || this.bits == null) {
            return;
        }

        box_w = string.length() * char_w / 2;
        box_h = char_h;

        v.setColor(Color.WHITE);
        v.fillRoundRectangle(x, y, box_w, box_h, 6, 10);

        v.setColor(Color.GRAY);
        for (int i = 0; i < string.length(); i++) {
            int yPos = y - (char_h / 2);
            drawLetter(v, i, string, yPos);
        }

        for (int i = 0; i < bits.length(); i++) {
            if (markedLetters.get(i)) {
                v.setColor(Color.RED);
            } else {
                v.setColor(getFgColor());
            }
            double yPos = y + (char_h / 2.0);
            drawLetter(v, i, bits, yPos);
        }
        drawPos(v);
    }

    public void drawLetter(View view, int i, String s, double yPos) {
        double xPos = x + (i * char_w) - (((s.length() - 1) / 2.0) * char_w);
        view.drawString(s.substring(i, i+1), xPos, yPos, Fonts.TYPEWRITER);
    }

    public void drawPos(View view) {
        if (0 <= markedPos && markedPos <= string.length()) {
            double xPos = x + (markedPos * char_w) - (((string.length()) / 2.0) * char_w);
            double yPos = y - char_h;
            view.drawLine(xPos, yPos, xPos, yPos + 2*char_h, 2, Color.RED);
        }
    }
    // </editor-fold>
    public void markLetter(int i) {
        markedLetters.set(i);
    }

    public void unmarkLetter(int i) {
        markedLetters.clear(i);
    }

    public void setMarkedPos(int i) {
        markedPos = i;
    }

    public void unmarkTree() {
        this.markedLetters.clear();
        this.markedPos = -1;
        if (!this.isLeaf()) {
            this.getLeftChild().unmarkTree();
            this.getRightChild().unmarkTree();
        }
    }

    public void getBinRepr(StringBuilder sb, char letter) {
        int occ = this.getString().indexOf(letter);
        if (occ != -1) {
            char bit = this.getBits().charAt(occ);
            if (bit == '0') {
                sb.append(bit);
                this.getLeftChild().getBinRepr(sb, letter);
            }
            if (bit == '1') {
                sb.append(bit);
                this.getRightChild().getBinRepr(sb, letter);
            }
        }
    }

    public boolean isLeaf() { return this.getLeftChild() == null && this.getRightChild() == null;}

    public void reposition() {
        if (this.getParent() == null) {
            this.x = tox = 0;
            this.y = toy = 100;
        } else {
            if ( this == this.getParent().getLeftChild() ) {
                this.x = this.getParent().x - this.getParent().string.length() * char_w / 2;
            } else {
                this.x = this.getParent().x + this.getParent().string.length() * char_w / 2;
            }
            this.y = this.getParent().y + char_h * 6;
            this.tox = this.x;
            this.toy = this.y;
        }

        if (this.getLeftChild() != null) {
            this.getLeftChild().reposition();
        }
        if (this.getRightChild() != null) {
            this.getRightChild().reposition();
        }
    }

    @Override   // Overrides method in Node
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash + "parent", parent);
        HashtableStoreSupport.store(state, hash + "leftChild", leftChild);
        HashtableStoreSupport.store(state, hash + "rightChild", rightChild);
        HashtableStoreSupport.store(state, hash + "string", string);
        HashtableStoreSupport.store(state, hash + "bits", bits);
        if (markedLetters != null) {
            HashtableStoreSupport.store(state, hash + "markedLetters", markedLetters.clone());
        }
        HashtableStoreSupport.store(state, hash + "markedPos", markedPos);
        if (leftChild != null) {
            leftChild.storeState(state);
        }
        if (rightChild != null) {
            rightChild.storeState(state);
        }
    }

    @Override   // Overrides method in Node
    public void restoreState(Hashtable<?, ?> state) {
        super.restoreState(state);
        final Object parent = state.get(hash + "parent");
        if (parent != null) { this.parent = (WaveletTreeNode) HashtableStoreSupport.restore(parent); }
        final Object leftChild = state.get(hash + "leftChild");
        if (leftChild != null) { this.leftChild = (WaveletTreeNode) HashtableStoreSupport.restore(leftChild); }
        final Object rightChild = state.get(hash + "rightChild");
        if (rightChild != null) { this.rightChild = (WaveletTreeNode) HashtableStoreSupport.restore(rightChild); }
        final Object string = state.get(hash + "string");
        if (string != null) { this.string = (String) HashtableStoreSupport.restore(string); }
        final Object bits = state.get(hash + "bits");
        if (bits != null) { this.bits = (String) HashtableStoreSupport.restore(bits); }
        final Object markedLetters = state.get(hash + "markedLetters");
        if (markedLetters != null) { this.markedLetters = (BitSet) HashtableStoreSupport.restore(markedLetters); }
        final Object markedPos = state.get(hash + "markedPos");
        if (markedPos != null) { this.markedPos = (int) HashtableStoreSupport.restore(markedPos); }


        if (this.leftChild != null) {
            this.leftChild.restoreState(state);
        }
        if (this.rightChild != null) {
            this.rightChild.restoreState(state);
        }
    }

    public void moveTree() {
        if (this.getLeftChild() != null) {
            this.getLeftChild().moveTree();
        }
        if (this.getRightChild() != null) {
            this.getRightChild().moveTree();
        }
        move();
    }
}

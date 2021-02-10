package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.history.HashtableStoreSupport;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;

import java.awt.*;
import java.util.Hashtable;

public class WaveletTreeNode extends Node {
    private WaveletTreeNode parent = null, leftChild = null, rightChild = null;
    private String string, bits;
    private int markedLetter = -1;
    int char_w = 8, char_h, box_w, box_h;

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
            setLetter(v, i, string, yPos);
        }

        v.setColor(getFgColor());
        for (int i = 0; i < bits.length(); i++) {
            if (i == markedLetter) {
                v.setColor(Color.RED);
            } else {
                v.setColor(getFgColor());
            }
            int yPos = y + (char_h / 2);
            setLetter(v, i, bits, yPos);
        }

    }

    public void setLetter(View view, int i, String s, int yPos) {
        view.drawString(s.substring(i, i+1), x + (i * char_w) - (((s.length() - 1) / 2.0) * char_w), yPos, Fonts.TYPEWRITER);
    }
    // </editor-fold>

    public void markLetter(int i) {
        markedLetter = i;
    }

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

package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.TreeNode;
import algvis.core.history.HashtableStoreSupport;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;

import java.awt.*;
import java.util.Hashtable;

public class WaveletTreeNode extends TreeNode {
    public String string;
    public String bits;
    private int markedLetter = -1;
    int char_w, char_h, box_w, box_h;

    public void setString(String text) {
        this.string = text;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public WaveletTreeNode(DataStructure D) {
        super(D, 0, ZDepth.NODE);
    }

    @Override   // Overrides method in TreeNode
    public void drawTree(View v) {
        drawEdges(v);
        drawNodes(v);
    }

    public void drawEdges(View view) {
        if (this.getParent() != null) {
            view.setColor(Color.BLACK);
            view.drawLine(this.getParent().x, this.getParent().y, this.x, this.y);
        }
        if (this.getChild() != null) {
            this.getChild().drawEdges(view);
            if (this.getChild().getRight() != null) {
                this.getChild().getRight().drawEdges(view);
            }
        }
    }

    public void drawNodes(View view) {
        this.draw(view);
        if (this.getChild() != null) {
            this.getChild().drawNodes(view);
            if (this.getChild().getRight() != null) {
                this.getChild().getRight().drawNodes(view);
            }
        }
    }

    @Override   // Overrides method in Node
    public void draw(View v) {
        if (state == Node.INVISIBLE || getKey() == NULL || this.string == null || this.bits == null) {
            return;
        }

        char_w = 8;
        char_h = Fonts.TYPEWRITER.fm.getHeight();
        box_w = string.length() * char_w;
        box_h = (int) (1.5 * char_h);

        v.setColor(Color.WHITE);
        v.fillRoundRectangle(x, y, box_w, box_h, 6, 10);

        v.setColor(getFgColor());
        for (int i = 0; i < string.length(); i++) {
            int yPos = y - (char_h / 2);
            setLetter(v, i, string, yPos);
        }
        for (int i = 0; i < bits.length(); i++) {
            int yPos = y + (char_h / 2);
            setLetter(v, i, bits, yPos);
        }

    }

    public void setLetter(View view, int i, String s, int yPos) {
        if (i == markedLetter) {
            view.setColor(Color.RED);
        } else {
            view.setColor(getFgColor());
        }
        view.drawString(s.substring(i, i+1), x + (i * char_w) - ((s.length() / 2.0) * char_w), yPos, Fonts.TYPEWRITER);
    }

    public void markLetter(int i) {
        markedLetter = i;
    }


    @Override   // Overrides method in TreeNode
    public WaveletTreeNode getChild() {
        return (WaveletTreeNode) super.getChild();
    }

    @Override   // Overrides method in TreeNode
    public WaveletTreeNode getRight() {
        return (WaveletTreeNode) super.getRight();
    }

    @Override   // Overrides method in TreeNode
    public WaveletTreeNode getParent() {
        return (WaveletTreeNode) super.getParent();
    }

    @Override   // Overrides method in TreeNode
    public void reposition() {
        if (this.getParent() == null) {
            this.x = 0;
            this.y = 0;
        } else {
            if ( this == this.getParent().getChild() ) {
                // is left child
                this.x = this.getParent().x - this.getParent().string.length() * 8;
                this.tox = this.x;
            } else {
                // is right child
                this.x = this.getParent().x + this.getParent().string.length() * 8;
                this.tox = this.x;
            }
            this.y = this.getParent().y + 80;
            this.toy = this.y;
        }
        if (this.getChild() != null) {
            this.getChild().reposition();
            if (this.getChild().getRight() != null) {
                this.getChild().getRight().reposition();
            }
        }
    }

    @Override   // Overrides method in TreeNode
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash + "str", string);
    }

    @Override   // Overrides method in TreeNode
    public void restoreState(Hashtable<?, ?> state) {
        super.restoreState(state);
        final Object ch = state.get(hash + "str");
        if (ch != null) {
            this.string = (String) HashtableStoreSupport.restore(ch);
        }
    }

}

package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.NodeColor;
import algvis.core.TreeNode;
import algvis.core.history.HashtableStoreSupport;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;

import java.awt.*;
import java.util.Collections;
import java.util.Hashtable;

public class WaveletTreeNode extends TreeNode {
    public String string;
    public String bits;
    private static final int ordinaryNode = -7;     // WTF
    private int markedLetter = -1;

    int midx, midy, char_w, char_h, box_w, box_h;

    public void setString(String text) {
        this.string = text;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public WaveletTreeNode(DataStructure D) {
        super(D, ordinaryNode, ZDepth.NODE);
        this.string = " ";
    }

    @Override
    public void drawTree(View v) {
        this.draw(v);
        if (this.getChild() != null) {
            this.getChild().drawTree(v);
            if (this.getChild().getRight() != null) {
                this.getChild().getRight().drawTree(v);
            }
        }
        super.drawEdges(v);
    }

    @Override
    public void draw(View v) {
        if (state == Node.INVISIBLE || getKey() == NULL) {
            return;
        }
        if (this.string == null || this.bits == null) {
            return;
        }

        midx = x; // x - ((x - u.x) / 15);
        midy = y; // y - ((y - u.y) / 5 * 2) - 1;
        char_w = 8;
        char_h = Fonts.TYPEWRITER.fm.getHeight();
        box_w = string.length() * char_w;
        box_h = (int) (1.5 * char_h);

        // int[] widths = Fonts.TYPEWRITER.fm.getWidths();
        // int max = (int) Collections.max(widths);

        v.setColor(getBgColor());
        v.fillRoundRectangle(midx, midy, box_w, box_h, 6, 10);
        v.setColor(Color.BLACK);
        v.drawRoundRectangle(midx, midy, box_w, box_h, 6, 10);

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


    @Override
    public WaveletTreeNode getChild() {
        return (WaveletTreeNode) super.getChild();
    }

    @Override
    public WaveletTreeNode getRight() {
        return (WaveletTreeNode) super.getRight();
    }

    @Override
    public WaveletTreeNode getParent() {
        return (WaveletTreeNode) super.getParent();
    }

    @Override
    public void reposition() {
        if (this.getParent() == null) {
            this.x = 0;
            this.y = 0;
        } else {
            if ( this == this.getParent().getChild() ) {
                // is left child
                this.x = this.getParent().x - this.getParent().string.length() * 8;
            } else {
                // is right child
                this.x = this.getParent().x + this.getParent().string.length() * 8;
            }
            this.y = this.getParent().y + 80;
        }
        if (this.getChild() != null) {
            this.getChild().reposition();
            if (this.getChild().getRight() != null) {
                this.getChild().getRight().reposition();
            }
        }
    }

    @Override
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash, string);
    }

    @Override
    public void restoreState(Hashtable<?, ?> state) {
        super.restoreState(state);
        final Object ch = state.get(hash);
        if (ch != null) {
            this.string = (String) HashtableStoreSupport.restore(ch);
        }
    }

}

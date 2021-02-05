package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.NodeColor;
import algvis.core.TreeNode;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;

import java.awt.*;
import java.util.Collections;

public class WaveletTreeNode extends TreeNode {
    public String string;
    public String bits;
    private static final int ordinaryNode = -7;     // WTF

    public void setString(String text) {
        this.string = text;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    protected WaveletTreeNode(DataStructure D, int key, int zDepth) {
        super(D, key, zDepth);
    }

    protected WaveletTreeNode(DataStructure D, int key, String string) {
        super(D, key, ZDepth.NODE);
        this.string = string;
    }

    public WaveletTreeNode(DataStructure D) {
        super(D, ordinaryNode, ZDepth.NODE);
        this.string = "some_string";
    }

    public WaveletTreeNode(DataStructure D, String string) {
        super(D, ordinaryNode, ZDepth.NODE);
        this.string = string;
    }

    @Override
    public void drawTree(View v) {
        drawGrey(v);
        super.drawTree(v);
    }

    void drawGrey(View v) {
        WaveletTreeNode w = getChild();
        while (w != null) {
            w.drawGrey(v);
            w = w.getRight();
        }
        if (getParent() != null) {
            v.drawWideLine(x, y, getParent().x, getParent().y, 10.0f);
        }
    }

    @Override
    public void draw(View v) {
        if (state == Node.INVISIBLE || getKey() == NULL) {
            return;
        }

        int midx, midy, char_w, char_h, box_w, box_h;
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
            v.drawString(string.substring(i, i+1), midx + (i * char_w) - ((string.length() / 2) * char_w) , midy - (char_h / 2.0), Fonts.TYPEWRITER);
        }
        for (int i = 0; i < bits.length(); i++) {
            v.drawString(bits.substring(i, i+1), midx + (i * char_w) - ((bits.length() / 2) * char_w), midy + (char_h / 2.0), Fonts.TYPEWRITER);
        }
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



}

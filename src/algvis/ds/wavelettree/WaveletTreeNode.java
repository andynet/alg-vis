package algvis.ds.wavelettree;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.NodeColor;
import algvis.core.TreeNode;
import algvis.core.visual.ZDepth;
import algvis.ui.Fonts;
import algvis.ui.view.View;

import java.awt.*;

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
        drawBg(v);
        drawLabel(v);
    }

    public void drawLabel(View v) {
        // final WaveletTreeNode u = getParent();

        int midx, midy, w, h;
        midx = 0; // x - ((x - u.x) / 15);
        midy = 0; // y - ((y - u.y) / 5 * 2) - 1;
        w = string.length() * 6;
        h = 10;

        v.setColor(getBgColor());
        v.fillRoundRectangle(midx, midy, w, h, 6, 10);
        v.setColor(Color.BLACK);
        v.drawRoundRectangle(midx, midy, w, h, 6, 10);

        v.setColor(getFgColor());
        v.drawString(string + '\n' + bits, midx, midy - 1, Fonts.TYPEWRITER);

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

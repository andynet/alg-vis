package algvis.ds.wavelettree;

import algvis.core.DataStructure;       // why it cannot find this?

import algvis.internationalization.Languages;
import algvis.core.StringUtils;
import algvis.ui.VisPanel;
import algvis.ui.Fonts;
import algvis.ui.view.ClickListener;
import algvis.ui.view.Layout;
import algvis.ui.view.View;

import java.awt.geom.Rectangle2D;
import java.util.ConcurrentModificationException;

public class WaveletTree extends DataStructure implements ClickListener{
    public static String dsName = "wavelettree";
    // private WaveletNode root = null;

    public WaveletTree(VisPanel M) {
        super(M);
        M.screen.V.setDS(this);
    }

    @Override
    public String getName() {
        return dsName;
    }

    @Override
    public String stats() {
        return "";
    }

    @Override
    public void insert(int x) {
        // this is not implemented
        // should it be in the DataStructure interface then?
    }

    @Override
    public void draw(View View) {
        /*
        final WaveletNode v = getRoot();
        if (v != null) {
            v.drawTree(View);
            View.drawString("\u025B", v.x, v.y - 8, Fonts.NORMAL);
        }
        */
    }

    @Override
    public void clear() {
        /*
        root = new WaveletNode(this);
        root.reposition();
         */
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return null;
        // return root == null ? null : root.getBoundingBox();
    }

    @Override
    protected void move() {
        
    }

    @Override
    public void mouseClicked(int x, int y) {
        System.out.print(x);
        System.out.print(y);
    }

    /* wavelet tree related methods */
    public void access(int i){

    }

    public void rank(int i){

    }

    public void select(int i){

    }
}

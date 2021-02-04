package algvis.ds.wavelettree;

import algvis.core.DataStructure;       // why it cannot find this?

import algvis.core.WordGenerator;
import algvis.ds.trie.TrieInsert;
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
    private WaveletTreeNode root = null;

    public WaveletTree(VisPanel M) {
        super(M);
        clear();
        // M.screen.V.setDS(this);
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
    public void draw(View View) {
        final WaveletTreeNode v = getRoot();
        if (v != null) {
            v.drawTree(View);
            View.drawString("\u025B", v.x, v.y - 8, Fonts.NORMAL);
        }
    }

    @Override
    public void clear() {
        root = new WaveletTreeNode(this);
        root.reposition();
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

    /* helper methods */
    public WaveletTreeNode getRoot() {
        return this.root;
    }

    public void reposition() {
        getRoot().reposition();
    }

    /* wavelet tree related methods */
    @Override
    public void insert(int x) {
        // this is not implemented
        // should it be in the DataStructure interface then?
    }

    public void construct(String s) {
        start(new WaveletTreeConstruct(this, s));
    }

    public void access(int i){

    }

    public void rank(int i){

    }

    public void select(int i){

    }

}

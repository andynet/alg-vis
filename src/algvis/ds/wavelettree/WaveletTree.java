package algvis.ds.wavelettree;

import algvis.core.DataStructure;

import algvis.core.history.HashtableStoreSupport;
import algvis.internationalization.Languages;
import algvis.core.StringUtils;
import algvis.ui.VisPanel;
import algvis.ui.Fonts;
import algvis.ui.view.ClickListener;
import algvis.ui.view.Layout;
import algvis.ui.view.View;

import java.awt.geom.Rectangle2D;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class WaveletTree extends DataStructure implements ClickListener{
    public static String dsName = "wavelettree";
    private WaveletTreeNode root = null;

    public WaveletTree(VisPanel M) {
        super(M);
        clear();
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
    public void draw(View View) {
//        final WaveletTreeNode v = getRoot();
//        if (v != null) {
//            v.drawTree(View);
//            // View.drawString("\u025B", v.x, v.y - 8, Fonts.NORMAL);
//        }
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

    @Override
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash + "root", root);
        if (root != null) {
            root.storeState(state);
        }
    }

    @Override
    public void restoreState(Hashtable<?, ?> state) {
        super.restoreState(state);
        final Object root = state.get(hash + "root");
        if (root != null) {
            this.root = (WaveletTreeNode) HashtableStoreSupport.restore(root);
        }
        if (this.root != null) {
            this.root.restoreState(state);
        }
    }
}

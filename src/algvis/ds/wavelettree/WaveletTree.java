package algvis.ds.wavelettree;

import algvis.core.DataStructure;

import algvis.core.WordGenerator;
import algvis.core.history.HashtableStoreSupport;
import algvis.ds.trie.TrieNode;
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

public class WaveletTree extends DataStructure {
    public static String dsName = "wavelettree";
    private WaveletTreeNode root = null;

    //<editor-fold desc="things mirroring Trie">
    public WaveletTree(VisPanel M) {
        super(M);
        clear();
        // M.screen.V.setDS(this);  // this was in bst from ClickListener
    }

    @Override   // inherited from DataStructure
    public String getName() {
        return dsName;
    }

    @Override   // inherited from DataStructure
    public String stats() {
        return "";
    }

    @Override   // inherited from DataStructure
    public void insert(int x) {
        throw new java.lang.UnsupportedOperationException("Not supported.");
    }

    @Override   // inherited from DataStructure
    public void draw(View View) {
        final WaveletTreeNode v = getRoot();
        if (v != null) {
            v.drawTree(View);
            v.drawEdges(View);
        }
    }

    @Override   // inherited from DataStructure
    public void clear() {
        panel.scene.clear();
        root = new WaveletTreeNode(this);
    }

    @Override   // inherited from DataStructure
    public void random(int n) {
        clear();
        construct(WordGenerator.getWord());
    }

    @Override   // inherited from VisualElement
    public Rectangle2D getBoundingBox() {
        return root == null ? null : root.getBoundingBox();
    }

    @Override   // inherited from VisualElement
    protected void move() {
        if (root != null) {
            root.moveTree();
        }
    }

    @Override   // inherited from VisualElement
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash + "root", root);
        if (root != null) {
            root.storeState(state);
        }
    }

    @Override   // inherited from VisualElement
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

    public WaveletTreeNode getRoot() {
        return root;
    }
    public void setRoot(WaveletTreeNode root) {
        this.root = root;
    }
    //</editor-fold>

    public void construct(String s) {
        start(new WaveletTreeConstruct(this, s));
    }

    public void access(int i){
        start(new WaveletTreeAccess(this, i));
    }

    public void rank(int i){
        start(new WaveletTreeRank(this, i));
    }

    public void select(int i, char ch){
        start(new WaveletTreeSelect(this, i, ch));
    }

}

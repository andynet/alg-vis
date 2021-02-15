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
import java.util.*;

public class WaveletTree extends DataStructure {
    public static String dsName = "wavelettree";
    private WaveletTreeNode root = null;

    //<editor-fold desc="things mirroring Trie">
    public WaveletTree(VisPanel M) {
        super(M);
        clear();
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
    public void draw(View view) {
        final WaveletTreeNode v = getRoot();
        if (v != null) {
            v.drawTree(view);
            drawRepr(view);
        }
    }

    public void drawRepr(View view) {
        final WaveletTreeNode v = getRoot();
        double xPos = v.tox - (v.getString().length() + 10) * v.char_w, yPos = v.toy - v.char_h / 2.0;
        Vector<Character> alphabet = WaveletTreeConstruct.getAlphabet(v.getString());
        Collections.sort(alphabet);
        for(Character c : alphabet) {
            view.drawString(c.toString() + ": " + getBinRepr(c), xPos, yPos, Fonts.TYPEWRITER);
            yPos += v.char_h;
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
        Random random = new Random();
        String generatedString = random
                .ints('A', 'A' + n)
                .limit(20)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        construct(generatedString);
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

    public String getBinRepr(char letter) {
        StringBuilder stringBuilder = new StringBuilder();
        WaveletTreeNode root = getRoot();
        root.getBinRepr(stringBuilder, letter);
        return stringBuilder.toString();
    }

    public void construct(String s) {
        start(new WaveletTreeConstruct(this, s));
    }

    public void access(int i){
        start(new WaveletTreeAccess(this, i));
    }

    public void rank(int index, char letter){
        start(new WaveletTreeRank(this, index, letter));
    }

    public void select(int i, char ch){
        start(new WaveletTreeSelect(this, i, ch));
    }

}

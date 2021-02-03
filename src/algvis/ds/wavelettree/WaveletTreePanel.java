package algvis.ds.wavelettree;

/* this will be WaveletTree */
import algvis.ds.trie.Trie;
import algvis.ds.trie.TrieButtons;
/*  */

import algvis.ui.VisPanel;

public class WaveletTreePanel extends VisPanel{

    @Override
    public void initDS() {
        D = new Trie(this);
        scene.add(D);
        buttons = new TrieButtons(this);
    }

    @Override
    public void start() {
        super.start();
        D.random(10);
    }
}

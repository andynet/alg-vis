package algvis.ds.wavelettree;

import algvis.ui.VisPanel;

public class WaveletTreePanel extends VisPanel{
    private static final long serialVersionUID = -8652425552838569507L;
    // WaveletTree D = new WaveletTree(this);

    @Override
    public void initDS() {
        // this.D = new WaveletTree(this);
        D = new WaveletTree(this);
        scene.add(this.D);
        buttons = new WaveletTreeButtons(this);
    }

    @Override
    public void start() {
        super.start();
        ((WaveletTree)D).construct("mississippi");
    }
}

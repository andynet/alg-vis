package algvis.ds.wavelettree;

import algvis.ui.VisPanel;

public class WaveletTreePanel extends VisPanel{

    @Override
    public void initDS() {
        D = new WaveletTree(this);
        scene.add(this.D);
        buttons = new WaveletTreeButtons(this);
    }

    @Override
    public void start() {
        super.start();
        D.random(4);
    }
}

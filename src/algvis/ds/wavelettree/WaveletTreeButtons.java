package algvis.ds.wavelettree;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JPanel;

import algvis.ds.trie.Trie;
import algvis.internationalization.IButton;
import algvis.ui.Buttons;
import algvis.ui.VisPanel;

public class WaveletTreeButtons extends Buttons {
    private static final long serialVersionUID = -368670840649549217L; // WTF is this
    private IButton accessB;
    private IButton rankB;
    private IButton selectB;

    public WaveletTreeButtons(VisPanel M) {
        super(M);
    }

    @Override
    public void actionButtons(JPanel P) {
        accessB = new IButton("button-access");
        accessB.setMnemonic(KeyEvent.VK_I);
        accessB.addActionListener(this);

        rankB = new IButton("button-rank");
        rankB.setMnemonic(KeyEvent.VK_F);
        rankB.addActionListener(this);

        selectB = new IButton("button-select");
        selectB.setMnemonic(KeyEvent.VK_D);
        selectB.addActionListener(this);

        P.add(accessB);
        P.add(rankB);
        P.add(selectB);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        if (evt.getSource() == accessB) {
            final Vector<String> args = I.getVS();
            panel.history.saveEditId();
            for (final String s : args) {
                ((Trie) D).insert(s);
            }
            if (panel.pauses && !args.isEmpty()) {
                panel.history.rewind();
            }
        } else if (evt.getSource() == rankB) {
            final Vector<String> args = I.getVS();
            if (args.size() > 0) {
                panel.history.saveEditId();
                for (final String s : args) {
                    ((Trie) D).find(s);
                }
                if (panel.pauses) {
                    panel.history.rewind();
                }
            }
        } else if (evt.getSource() == selectB) {
            final Vector<String> args = I.getVS();
            if (args.size() > 0) {
                panel.history.saveEditId();
                for (final String s : args) {
                    ((Trie) D).delete(s);
                }
                if (panel.pauses) {
                    panel.history.rewind();
                }
            }
        }
    }

    @Override
    public void setOtherEnabled(boolean enabled) {
        super.setOtherEnabled(enabled);
        accessB.setEnabled(enabled);
        rankB.setEnabled(enabled);
        selectB.setEnabled(enabled);
    }
}

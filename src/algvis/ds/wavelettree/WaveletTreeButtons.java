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
    private IButton insertB;
    private IButton findB;
    private IButton deleteB;

    public WaveletTreeButtons(VisPanel M) {
        super(M);
    }

    @Override
    public void actionButtons(JPanel P) {
        insertB = new IButton("button-insert");
        insertB.setMnemonic(KeyEvent.VK_I);
        insertB.addActionListener(this);

        findB = new IButton("button-find");
        findB.setMnemonic(KeyEvent.VK_F);
        findB.addActionListener(this);

        deleteB = new IButton("button-delete");
        deleteB.setMnemonic(KeyEvent.VK_D);
        deleteB.addActionListener(this);

        P.add(insertB);
        P.add(findB);
        P.add(deleteB);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        if (evt.getSource() == insertB) {
            final Vector<String> args = I.getVS();
            panel.history.saveEditId();
            for (final String s : args) {
                ((Trie) D).insert(s);
            }
            if (panel.pauses && !args.isEmpty()) {
                panel.history.rewind();
            }
        } else if (evt.getSource() == findB) {
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
        } else if (evt.getSource() == deleteB) {
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
        insertB.setEnabled(enabled);
        findB.setEnabled(enabled);
        deleteB.setEnabled(enabled);
    }
}

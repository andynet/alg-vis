package algvis.ds.wavelettree;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JPanel;

import algvis.internationalization.IButton;
import algvis.ui.Buttons;
import algvis.ui.VisPanel;

public class WaveletTreeButtons extends Buttons {
    private static final long serialVersionUID = -368670840649549217L; // WTF is this
    private IButton constructB;
    private IButton accessB;
    private IButton rankB;
    private IButton selectB;

    public WaveletTreeButtons(VisPanel M) {
        super(M);
    }

    @Override
    public void actionButtons(JPanel P) {
        constructB = new IButton("button-construct");
        constructB.setMnemonic(KeyEvent.VK_C);
        constructB.addActionListener(this);
        P.add(constructB);

        accessB = new IButton("button-access");
        accessB.setMnemonic(KeyEvent.VK_A);
        accessB.addActionListener(this);
        P.add(accessB);

        rankB = new IButton("button-rank");
        rankB.setMnemonic(KeyEvent.VK_R);
        rankB.addActionListener(this);
        P.add(rankB);

        selectB = new IButton("button-select");
        selectB.setMnemonic(KeyEvent.VK_S);
        selectB.addActionListener(this);
        P.add(selectB);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
        if (event.getSource() == constructB) {
            System.out.print("Construct button was pressed\n");
            String string = I.getText();
            ((WaveletTree) D).construct(string);
        } else if (event.getSource() == accessB) {
            System.out.print("Access button was pressed\n");
//            final Vector<String> args = I.getVS();
//            panel.history.saveEditId();
//            for (final String s : args) {
//                ((Trie) D).insert(s);
//            }
//            if (panel.pauses && !args.isEmpty()) {
//                panel.history.rewind();
//            }
        } else if (event.getSource() == rankB) {
            System.out.print("Rank button was pressed\n");
//            final Vector<String> args = I.getVS();
//            if (args.size() > 0) {
//                panel.history.saveEditId();
//                for (final String s : args) {
//                    ((Trie) D).find(s);
//                }
//                if (panel.pauses) {
//                    panel.history.rewind();
//                }
//            }
        } else if (event.getSource() == selectB) {
            System.out.print("Select button was pressed\n");
//            final Vector<String> args = I.getVS();
//            if (args.size() > 0) {
//                panel.history.saveEditId();
//                for (final String s : args) {
//                    ((Trie) D).delete(s);
//                }
//                if (panel.pauses) {
//                    panel.history.rewind();
//                }
//            }
        }
    }

    @Override
    public void setOtherEnabled(boolean enabled) {
        super.setOtherEnabled(enabled);
        constructB.setEnabled(enabled);
        accessB.setEnabled(enabled);
        rankB.setEnabled(enabled);
        selectB.setEnabled(enabled);
    }
}

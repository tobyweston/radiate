package bad.robot.radiate.ui;

import javax.swing.*;

class TransparentJScrollPane extends JScrollPane {
    public TransparentJScrollPane(JTextArea text) {
        super(text, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
        setComponentZOrder(getVerticalScrollBar(), 0);
        setComponentZOrder(getViewport(), 1);
        setLayout(new TransparentScrollPaneLayout());
        setOpaque(false);
        getViewport().setOpaque(false);
//        setBackground(Color.black);
        getVerticalScrollBar().setOpaque(false);
        getVerticalScrollBar().setUI(new TransparentScrollBarUI());
    }
}

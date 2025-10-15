package hu.webarticum.inno.valuegeneratordemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ValueSetStatePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private static final String EMPTY_CARD_NAME = "empty";
    
    private static final String SOURCE_CARD_NAME = "source";
    
    private static final String ERROR_CARD_NAME = "error";
    
    private final ValueSetState state = ValueSetState.empty();
    
    private final CardLayout cardLayout = new CardLayout();
    
    public ValueSetStatePanel() {
        
        // TODO
        setLayout(cardLayout);
        setOpaque(false);
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);
        JPanel emptyContentPanel = new JPanel(new BorderLayout());
        emptyContentPanel.setOpaque(false);
        emptyContentPanel.add(new JLabel("EMPTY!"), BorderLayout.CENTER);
        emptyPanel.add(emptyContentPanel, BorderLayout.PAGE_START);
        add(emptyPanel, EMPTY_CARD_NAME);
        cardLayout.show(this, EMPTY_CARD_NAME);
        
    }
    
    public void setState() {
        // TODO
    }
    
    public synchronized ValueSetState getState() {
        return state;
    }
    
}

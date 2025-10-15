package hu.webarticum.inno.valuegeneratordemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ValueSetStatePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    
    private static final String EMPTY_CARD_NAME = "empty";
    
    private static final String SOURCE_CARD_NAME = "source";
    
    private static final String ERROR_CARD_NAME = "error";
    
    private final CardLayout cardLayout = new CardLayout();

    private final JLabel errorLabel = new JLabel();
    
    private ValueSetState state = ValueSetState.empty();
    
    public ValueSetStatePanel() {
        setLayout(cardLayout);
        setOpaque(false);
        add(createEmptyPanel(), EMPTY_CARD_NAME);
        add(createSourcePanel(), SOURCE_CARD_NAME);
        add(createErrorPanel(), ERROR_CARD_NAME);
        cardLayout.show(this, EMPTY_CARD_NAME);
        
    }
    
    private JPanel createEmptyPanel() {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setOpaque(false);
        JPanel emptyContentPanel = new JPanel(new BorderLayout());
        emptyContentPanel.setOpaque(false);
        JLabel emptyLabel = new JLabel("Create a value set first");
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(Color.BLUE);
        emptyLabel.setFont(DEFAULT_FONT);
        emptyContentPanel.add(emptyLabel, BorderLayout.CENTER);
        emptyPanel.add(emptyContentPanel, BorderLayout.PAGE_START);
        return emptyPanel;
    }

    private JPanel createSourcePanel() {
        JPanel sourcePanel = new JPanel(new BorderLayout());
        sourcePanel.setOpaque(false);
        JPanel sourceContentPanel = new JPanel(new BorderLayout());
        sourceContentPanel.setOpaque(false);
        sourceContentPanel.add(new JLabel("SOURCE!"), BorderLayout.CENTER);
        sourcePanel.add(sourceContentPanel, BorderLayout.PAGE_START);
        return sourcePanel;
    }
    
    private JPanel createErrorPanel() {
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setOpaque(false);
        JPanel errorContentPanel = new JPanel(new BorderLayout());
        errorContentPanel.setOpaque(false);
        errorLabel.setOpaque(false);
        errorLabel.setFont(DEFAULT_FONT);
        errorLabel.setForeground(Color.RED);
        errorContentPanel.add(errorLabel, BorderLayout.CENTER);
        errorPanel.add(errorContentPanel, BorderLayout.PAGE_START);
        return errorPanel;
    }
    
    public void setState(ValueSetState state) {
        synchronized (this) {
            this.state = state;
        }
        SwingUtilities.invokeLater(() -> {
            switch (state.status()) {
                case SOURCE:
                    
                    // TODO
                    cardLayout.show(this, SOURCE_CARD_NAME);
                    
                case ERROR:
                    errorLabel.setText(state.errorMessage());
                    cardLayout.show(this, ERROR_CARD_NAME);
                case EMPTY:
                default:
                    cardLayout.show(this, EMPTY_CARD_NAME);
            }
        });
    }
    
    public synchronized ValueSetState getState() {
        return state;
    }
    
}

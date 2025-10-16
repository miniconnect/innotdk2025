package hu.webarticum.inno.valuegeneratordemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import hu.webarticum.holodb.core.data.source.Source;
import hu.webarticum.miniconnect.lang.LargeInteger;

public class ValueSetStatePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    
    private static final String EMPTY_CARD_NAME = "empty";
    
    private static final String SOURCE_CARD_NAME = "source";
    
    private static final String ERROR_CARD_NAME = "error";
    
    private final CardLayout cardLayout = new CardLayout();

    private final JLabel errorLabel = new JLabel();
    
    private final ValueSetTableModel tableModel = new ValueSetTableModel();
    
    private final JComboBox<String> maxItemCombo = new JComboBox<>();
    
    private final JSpinner fromSpinner = new JSpinner();

    private volatile ValueSetState state = ValueSetState.empty();
    
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
        JTable valuesTable = new JTable(tableModel);
        setColumnWidth(valuesTable, 0, 70);
        valuesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        valuesTable.getColumnModel().getColumn(0).setCellRenderer(this::renderCell);
        valuesTable.getColumnModel().getColumn(1).setCellRenderer(this::renderCell);
        valuesTable.getTableHeader().setReorderingAllowed(false);
        valuesTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(valuesTable);
        scrollPane.setPreferredSize(new Dimension(200, 700));
        sourcePanel.add(scrollPane, BorderLayout.CENTER);
        JPanel sourceNavPanel = new JPanel(new FlowLayout());
        sourceNavPanel.setOpaque(false);
        sourcePanel.add(sourceNavPanel, BorderLayout.PAGE_END);
        sourceNavPanel.add(new JLabel("Items per page:"));
        maxItemCombo.addItem("20");
        maxItemCombo.addItem("50");
        maxItemCombo.addItem("100");
        maxItemCombo.addItem("500");
        maxItemCombo.setSelectedIndex(1);
        maxItemCombo.addItemListener(e -> tableModel.fireTableDataChanged());
        sourceNavPanel.add(maxItemCombo);
        sourceNavPanel.add(new JLabel("   Start from:"));
        fromSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        fromSpinner.setPreferredSize(new Dimension(100, fromSpinner.getPreferredSize().height));
        fromSpinner.addChangeListener(e -> tableModel.fireTableDataChanged());
        sourceNavPanel.add(fromSpinner);
        return sourcePanel;
    }
    
    private void setColumnWidth(JTable table, int i, int width) {
        TableColumn column = table.getColumnModel().getColumn(i);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setPreferredWidth(width);
    }
    
    private Component renderCell(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel(value != null ? value.toString() : "NULL");
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(0, 3, 0, 3));
        if (value == null) {
            label.setForeground(new Color(0x999999));
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (hasFocus || isSelected) {
            label.setBackground(new Color(0x39698A));
            label.setForeground(Color.WHITE);
        } else if (row % 2 == 1) {
            label.setBackground(new Color(0xF2F2F2));
        } else {
            label.setBackground(Color.WHITE);
        }
        if (value instanceof Number) {
            label.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return label;
    }
    
    private JPanel createErrorPanel() {
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setOpaque(false);
        JPanel errorContentPanel = new JPanel(new BorderLayout());
        errorContentPanel.setOpaque(false);
        errorLabel.setOpaque(false);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(DEFAULT_FONT);
        errorLabel.setForeground(new Color(0x90333F));
        errorContentPanel.add(errorLabel, BorderLayout.CENTER);
        errorPanel.add(errorContentPanel, BorderLayout.PAGE_START);
        return errorPanel;
    }
    
    public void setState(ValueSetState state) {
        synchronized (this) {
            this.state = state;
        }
        ValueSetState.Status status = state.status();
        SwingUtilities.invokeLater(() -> {
            switch (status) {
                case SOURCE:
                    cardLayout.show(this, SOURCE_CARD_NAME);
                    fromSpinner.setValue(0);
                    tableModel.fireTableDataChanged();
                    break;
                case ERROR:
                    errorLabel.setText(state.errorMessage());
                    cardLayout.show(this, ERROR_CARD_NAME);
                    break;
                case EMPTY:
                default:
                    cardLayout.show(this, EMPTY_CARD_NAME);
                    break;
            }
        });
    }
    
    public synchronized ValueSetState getState() {
        return state;
    }
    
    private class ValueSetTableModel extends AbstractTableModel {
        
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            ValueSetState.Status status = state.status();
            if (status != ValueSetState.Status.SOURCE) {
                return 0;
            }
            Source<?> finalSource = state.finalSource();
            LargeInteger from = LargeInteger.of((int) fromSpinner.getValue());
            LargeInteger size = finalSource.size();
            LargeInteger remainingSize = size.subtract(from);
            LargeInteger maxItemCount = LargeInteger.of((String) maxItemCombo.getModel().getSelectedItem());
            LargeInteger rowCount = maxItemCount.min(remainingSize);
            return rowCount.intValueExact();
        }
    
        @Override
        public Object getValueAt(int row, int col) {
            LargeInteger from = LargeInteger.of((int) fromSpinner.getValue());
            LargeInteger index = from.add(LargeInteger.of(row));
            if (col == 0) {
                return index;
            } else if (col == 1) {
                return state.finalSource().get(index);
            } else {
                return "";
            }
        }
        
        @Override
        public String getColumnName(int column) {
            return switch (column) {
                case 0 -> "Index";
                case 1 -> "Value";
                default -> "";
            };
        }
    }
    
}

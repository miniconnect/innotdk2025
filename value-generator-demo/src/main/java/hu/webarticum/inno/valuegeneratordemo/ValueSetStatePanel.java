package hu.webarticum.inno.valuegeneratordemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.JButton;
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
    
    private final JTable valuesTable = new JTable();
    
    private final ValueSetTableModel tableModel = new ValueSetTableModel();
    
    private final JComboBox<String> maxItemCombo = new JComboBox<>();
    
    private final JSpinner fromSpinner = new JSpinner();
    
    private final JButton firstPageButton = new JButton();
    
    private final JButton previousPageButton = new JButton();
    
    private final JButton nextPageButton = new JButton();
    
    private final JButton lastPageButton = new JButton();

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
        valuesTable.setModel(tableModel);
        setColumnWidth(valuesTable, 0, 70);
        valuesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        valuesTable.getColumnModel().getColumn(0).setCellRenderer(SimpleCellRenderer.instance());
        valuesTable.getColumnModel().getColumn(1).setCellRenderer(SimpleCellRenderer.instance());
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
        maxItemCombo.addItemListener(e -> onTableDataUpdated());
        sourceNavPanel.add(maxItemCombo);
        JLabel fromLabel = new JLabel("Start from:");
        fromLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
        sourceNavPanel.add(fromLabel);
        firstPageButton.setText("\u23EE");
        sourceNavPanel.add(firstPageButton);
        firstPageButton.addActionListener(e -> moveToFirstPage());
        previousPageButton.setText("\u23F4");
        sourceNavPanel.add(previousPageButton);
        previousPageButton.addActionListener(e -> moveToPreviousPage());
        fromSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        fromSpinner.setPreferredSize(new Dimension(100, fromSpinner.getPreferredSize().height));
        fromSpinner.addChangeListener(e -> onTableDataUpdated());
        sourceNavPanel.add(fromSpinner);
        nextPageButton.setText("\u23F5");
        sourceNavPanel.add(nextPageButton);
        nextPageButton.addActionListener(e -> moveToNextPage());
        lastPageButton.setText("\u23ED");
        sourceNavPanel.add(lastPageButton);
        lastPageButton.addActionListener(e -> moveToLastPage());
        return sourcePanel;
    }
    
    private void moveToFirstPage() {
        fromSpinner.setValue(0);
    }

    private void moveToPreviousPage() {
        LargeInteger currentOffset = LargeInteger.of((int) fromSpinner.getValue());
        LargeInteger maxItemCount = LargeInteger.of((String) maxItemCombo.getModel().getSelectedItem());
        LargeInteger currentPage = getContainingPageCount(currentOffset, maxItemCount);
        LargeInteger previousPage = currentPage.decrement();
        LargeInteger targetPage = previousPage.max(LargeInteger.ZERO);
        LargeInteger targetOffset = targetPage.multiply(maxItemCount);
        fromSpinner.setValue(targetOffset.intValueExact());
    }

    private void moveToNextPage() {
        Source<?> finalSource = state.finalSource();
        LargeInteger size = finalSource.size();
        LargeInteger currentOffset = LargeInteger.of((int) fromSpinner.getValue());
        LargeInteger maxItemCount = LargeInteger.of((String) maxItemCombo.getModel().getSelectedItem());
        LargeInteger currentPage = currentOffset.divide(maxItemCount);
        LargeInteger pageCount = getContainingPageCount(size, maxItemCount);
        LargeInteger lastPage = pageCount.decrement();
        LargeInteger nextPage = currentPage.increment();
        LargeInteger targetPage = nextPage.min(lastPage);
        LargeInteger targetOffset = targetPage.multiply(maxItemCount);
        fromSpinner.setValue(targetOffset.intValueExact());
    }

    private void moveToLastPage() {
        Source<?> finalSource = state.finalSource();
        LargeInteger size = finalSource.size();
        LargeInteger maxItemCount = LargeInteger.of((String) maxItemCombo.getModel().getSelectedItem());
        LargeInteger pageCount = getContainingPageCount(size, maxItemCount);
        LargeInteger lastPage = pageCount.decrement();
        LargeInteger targetOffset = lastPage.multiply(maxItemCount);
        fromSpinner.setValue(targetOffset.intValueExact());
    }
    
    private LargeInteger getContainingPageCount(LargeInteger size, LargeInteger maxItemCount) {
        LargeInteger[] divisionResult = size.divideAndRemainder(maxItemCount);
        LargeInteger result = divisionResult[0];
        if (divisionResult[1].isNonZero()) {
            result = result.increment();
        }
        return result;
    }
    
    private void onTableDataUpdated() {
        tableModel.fireTableDataChanged();
        refreshNavButtons();
    }
    
    private void refreshNavButtons() {
        Source<?> finalSource = state.finalSource();
        LargeInteger size = finalSource.size();
        LargeInteger currentOffset = LargeInteger.of((int) fromSpinner.getValue());
        LargeInteger maxItemCount = LargeInteger.of((String) maxItemCombo.getModel().getSelectedItem());
        LargeInteger currentPage = currentOffset.divide(maxItemCount);
        LargeInteger pageCount = getContainingPageCount(size, maxItemCount);
        LargeInteger lastPage = pageCount.decrement();
        boolean hasPreviousPage = currentPage.isPositive();
        firstPageButton.setEnabled(hasPreviousPage);
        previousPageButton.setEnabled(hasPreviousPage);
        boolean hasNextPage = currentPage.isLessThan(lastPage);
        nextPageButton.setEnabled(hasNextPage);
        lastPageButton.setEnabled(hasNextPage);
    }
    
    private void setColumnWidth(JTable table, int i, int width) {
        TableColumn column = table.getColumnModel().getColumn(i);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setPreferredWidth(width);
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
            valuesTable.getSelectionModel().clearSelection();
            switch (status) {
                case SOURCE:
                    cardLayout.show(this, SOURCE_CARD_NAME);
                    fromSpinner.setValue(0);
                    onTableDataUpdated();
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
    
    public void addSelectListener(Consumer<ValueInfo> listener) {
        valuesTable.getSelectionModel().addListSelectionListener(e -> {
            int[] selectedIndices = valuesTable.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length == 0) {
                listener.accept(null);
                return;
            }
            LargeInteger rowIndex = LargeInteger.of(selectedIndices[0]);
            LargeInteger from = LargeInteger.of((int) fromSpinner.getValue());
            LargeInteger index = from.add(rowIndex);
            Object value = state.finalSource().get(index);
            LargeInteger orderedIndex = state.permutation().indexOf(index);
            LargeInteger originalIndex = value != null ? state.monotonic().at(orderedIndex) : null;
            listener.accept(new ValueInfo(value, index, orderedIndex, originalIndex));
        });
    }
    
    public synchronized ValueSetState getState() {
        return state;
    }
    
    private class ValueSetTableModel extends AbstractTableModel {
        
        private static final long serialVersionUID = 1L;

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
    
    public static class ValueInfo {
        
        private final Object value;
        
        private final LargeInteger index;
        
        private final LargeInteger orderedIndex;
        
        private final LargeInteger originalIndex;
        
        private ValueInfo(Object value, LargeInteger index, LargeInteger orderedIndex, LargeInteger originalIndex) {
            this.value = value;
            this.index = index;
            this.orderedIndex = orderedIndex;
            this.originalIndex = originalIndex;
        }

        public Object value() {
            return value;
        }

        public LargeInteger index() {
            return index;
        }

        public LargeInteger orderedIndex() {
            return orderedIndex;
        }

        public LargeInteger originalIndex() {
            return originalIndex;
        }
        
    }
    
}

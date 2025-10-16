package hu.webarticum.inno.valuegeneratordemo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public final class SimpleCellRenderer implements TableCellRenderer {

    private static final SimpleCellRenderer INSTANCE = new SimpleCellRenderer();
    
    private SimpleCellRenderer() {
        // use instance()
    }
    
    public static SimpleCellRenderer instance() {
        return INSTANCE;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
    
}

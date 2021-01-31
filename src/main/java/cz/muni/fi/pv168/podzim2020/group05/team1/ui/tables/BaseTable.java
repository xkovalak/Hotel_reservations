package cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables;

import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.ColorPicker;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.sql.Date;

public abstract class BaseTable extends JTable {

    private int numberOfColumns;

    public BaseTable(AbstractTableModel viewModel) {
        super(viewModel);

        numberOfColumns = viewModel.getColumnCount();

        this.setAutoCreateRowSorter(true);
        this.setRowHeight(30);
        this.setFont(new Font("Serif", Font.BOLD, 15));

        initialize();
    }

    protected abstract JPopupMenu createPeopleTablePopupMenu();

    protected void initialize() {
        centerCols();
        customizeHeader();
        this.setShowGrid(false);
        this.setIntercellSpacing(new Dimension(0,0));
        this.setGridColor(Color.red);
        this.getTableHeader().setReorderingAllowed(false);
        this.setComponentPopupMenu(createPeopleTablePopupMenu());
    }

    public void centerCols() {
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyy");
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {

                if(value instanceof Date) {
                    value = f.format(value);
                }

                this.setHorizontalAlignment(JLabel.CENTER);
                return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
            }
        };

        for (int i = 0; i < numberOfColumns; i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }
    }

    private void customizeHeader() {
        this.getTableHeader().setFont(new Font("sansserif", Font.BOLD, 18));
        this.getTableHeader().setBackground(ColorPicker.MAIN_COLOR);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component comp = super.prepareRenderer(renderer, row, col);

        if (row % 2 == 0 && !isCellSelected(row, col)) {
            comp.setBackground(ColorPicker.SECONDARY_COLOR);
        }
        else if (!isCellSelected(row, col)) {
            comp.setBackground(ColorPicker.TERNARY_COLOR);
        } else {
            comp.setBackground(ColorPicker.SELECTION_COLOR);
        }

        return comp;
    }
}

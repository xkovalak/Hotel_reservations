package cz.muni.fi.pv168.podzim2020.group05.team1.utilities;

import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class CustomTableRowSorter extends TableRowSorter<TableModel> {

    public CustomTableRowSorter(TableModel model) {
        super(model);
    }

    @Override
    public void toggleSortOrder(int column) {
        List<SortKey> newSortKeys = new ArrayList<>();
        newSortKeys.add(new SortKey(4, SortOrder.DESCENDING));

        SortKey currentKey = getSortKey(column);
        SortOrder sortOrder = SortOrder.ASCENDING;

        if (currentKey != null) {
            sortOrder = currentKey.getSortOrder() == SortOrder.ASCENDING ? SortOrder.DESCENDING : SortOrder.ASCENDING;
        }

        currentKey = new SortKey(column, sortOrder);
        newSortKeys.add(currentKey);

        super.setSortKeys(newSortKeys);

        super.sort();
    }

    private SortKey getSortKey (int column)
    {
        for (SortKey key : super.getSortKeys()) {
            if (key.getColumn() == column) {
                return key;
            }
        }

        return null;
    }
}

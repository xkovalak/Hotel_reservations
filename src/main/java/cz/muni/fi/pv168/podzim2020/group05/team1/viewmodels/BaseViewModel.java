package cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels;

import cz.muni.fi.pv168.podzim2020.group05.team1.models.BaseModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewModel<T extends BaseModel> extends AbstractTableModel {

    protected final List<Column<?, T>> columns;
    protected final List<T> context;

    public BaseViewModel(List<Column<?, T>> columns) {
        this(new ArrayList<T>(), columns);
    }

    public BaseViewModel(List<T> context, List<Column<?, T>> columns) {
        this.context = context;
        this.columns = columns;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).getValue(getEntity(rowIndex));
    }

    public T getEntity(int rowIndex) {
        return context.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return context.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnClass();
    }

    public T getRow(int index){
        return context.get(index);
    }

    public List<T> getContext(){
        return context;
    }
}

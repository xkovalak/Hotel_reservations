package cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels;

import java.util.Objects;
import java.util.function.Function;

public class Column<T, E> {

    private final String columnName;
    private final Class<T> columnClass;
    private final Function<E, T> valueGetter;

    public Column(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        this.columnName = Objects.requireNonNull(columnName, "columnName");
        this.columnClass = Objects.requireNonNull(columnClass, "columnClass");
        this.valueGetter = Objects.requireNonNull(valueGetter, "valueGetter");
    }

    public static <T, E> Column<T, E> newColumn(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        return new Column<>(columnName, columnClass, valueGetter);
    }

    public String getColumnName() {
        return columnName;
    }

    public Class<T> getColumnClass() {
        return columnClass;
    }

    public Object getValue(E entity) {
        return valueGetter.apply(entity);
    }
}

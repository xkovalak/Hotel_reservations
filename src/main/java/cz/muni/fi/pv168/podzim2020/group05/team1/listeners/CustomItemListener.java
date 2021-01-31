package cz.muni.fi.pv168.podzim2020.group05.team1.listeners;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomItemListener implements ItemListener {

    TableType type;

    public CustomItemListener(TableType type) {
        this.type = type;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        ServiceLayer.getInstance().refreshTableRowSorter(type);
    }
}

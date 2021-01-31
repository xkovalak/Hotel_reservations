package cz.muni.fi.pv168.podzim2020.group05.team1.listeners;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CustomKeyListener extends KeyAdapter {

    TableType type;

    public CustomKeyListener(TableType type) {
        this.type = type;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ServiceLayer.getInstance().refreshTableRowSorter(type);
    }
}

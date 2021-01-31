package cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels.RoomsFilterPanel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.RoomTable;

public class RoomsCard extends BaseCard {

    public RoomsCard(RoomTable table) {
        super();

        addTable(table);
        addFilter(new RoomsFilterPanel());
    }
}

package cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards;

import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.PeopleListSelectionListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels.PeopleFilterPanel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.PeopleTable;

public class PeopleCard extends BaseCard {

    public PeopleCard(PeopleTable table) {
        super();

        addTable(table);
        PeopleFilterPanel panel = new PeopleFilterPanel();
        table.getSelectionModel().addListSelectionListener(new PeopleListSelectionListener(panel.getButtonAccommodate(), table));
        addFilter(panel);
    }
}

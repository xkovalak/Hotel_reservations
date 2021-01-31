package cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels.ReservationsFilterPanel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.ReservationTable;

public class ReservationsCard extends BaseCard {

    public ReservationsCard(ReservationTable table) {
        super();

        addTable(table);
        addFilter(new ReservationsFilterPanel());
    }
}

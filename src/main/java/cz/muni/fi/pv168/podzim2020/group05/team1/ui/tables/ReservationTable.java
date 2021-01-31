package cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.CheckInReservationAction;

import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;


public class ReservationTable extends BaseTable {

    private CheckInReservationAction checkAction;

    public ReservationTable(AbstractTableModel viewModel) {
        super(viewModel);
        getSelectionModel().addListSelectionListener(this::selectionChanged);
    }

    protected JPopupMenu createPeopleTablePopupMenu() {
        checkAction = new CheckInReservationAction();
        var menu = new JPopupMenu();
        menu.add(checkAction);
        return menu;
    }

    private void selectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var indices = selectionModel.getSelectedIndices();

        checkAction.setEnabled(indices.length == 1 && getValueAt(indices[0], 6) == ReservationStatus.RESERVED);
    }
}

package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.ReservationsModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import java.awt.event.ActionEvent;

public class EditReservationAction extends BaseAction {

    public EditReservationAction(BaseTable table) {
        super("Edit", Icons.EDIT_ICON, table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }

        var window = new ReservationsModifyDialog(false);
        window.setVisible(true);
    }
}

package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.ReservationsModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import java.awt.event.ActionEvent;

public class AddReservationAction extends BaseAction {

    public AddReservationAction() {
        super("Add reservation", Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var rmf = new ReservationsModifyDialog(true);
        rmf.setVisible(true);
    }
}

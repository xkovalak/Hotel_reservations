package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.CheckInDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import java.awt.event.ActionEvent;

public class CheckInReservationAction extends BaseAction {

    public CheckInReservationAction() {
        super("Check-in reservation", Icons.EDIT_ICON);
        this.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var checkIn = new CheckInDialog();
        checkIn.setVisible(true);
        this.setEnabled(false);
    }
}

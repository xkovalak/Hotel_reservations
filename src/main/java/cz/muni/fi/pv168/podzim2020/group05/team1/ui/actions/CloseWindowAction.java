package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.BaseJDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class CloseWindowAction extends AbstractAction {

    private BaseJDialog dialog;

    public CloseWindowAction(BaseJDialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
        ServiceLayer.getInstance().updateReservationStatus(ReservationStatus.CHECKED_IN);
    }
}

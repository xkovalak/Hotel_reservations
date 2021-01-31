package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class CancelReservationAction extends BaseAction {

    private static I18N I18n = new I18N(CancelReservationAction.class);

    public CancelReservationAction() {
        super("Cancel reservation", Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ServiceLayer.getInstance().getSelectedRowCount(TableType.RESERVATIONS_TABLE) != 1) {
            JOptionPane.showMessageDialog(null,
                    I18n.getString("warningMessage"),
                    I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null,
                I18n.getString("cancelSelectedReservation"),I18n.getString("inaneQuestion"), dialogButton);

        if (dialogResult == JOptionPane.YES_OPTION) {
            ServiceLayer.getInstance().updateReservationStatus(ReservationStatus.CANCELED);
        }
    }
}

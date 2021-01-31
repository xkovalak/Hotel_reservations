package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.EditRoomDialog;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class ConfirmEditRoomAction extends BaseAction {

    private static I18N I18n = new I18N(ConfirmEditRoomAction.class);

    private EditRoomDialog editRoomDialog;

    public ConfirmEditRoomAction(EditRoomDialog editRoomDialog) {
        super("Confirm room");

        this.editRoomDialog = editRoomDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (validateData()) {
            ServiceLayer.getInstance().updateRoom(new RoomModel(Integer.parseInt(editRoomDialog.getNumber()),
                    editRoomDialog.getTypeOfRoom().getSelectedItem(), Integer.parseInt(editRoomDialog.getBeds().getSelectedItem()),
                    editRoomDialog.getFreeCheckBox().isSelected(), Double.parseDouble(editRoomDialog.getPrice().getField())));
            editRoomDialog.dispose();
        }
    }

    private boolean validateData() {
        String price = editRoomDialog.getPrice().getField();

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(editRoomDialog, I18n.getString("warningMessage"), I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}

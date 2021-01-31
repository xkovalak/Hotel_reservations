package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.BaseJDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.EditRoomDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;

import java.awt.event.ActionEvent;

public class EditRoomAction extends BaseAction {

    public EditRoomAction(BaseTable table) {
        super("Edit", Icons.EDIT_ICON, table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
        var model = (RoomTableViewModel) table.getModel();
        RoomModel roomModel = model.getRow(selectedRow);

        BaseJDialog dialog = new EditRoomDialog(roomModel);
        dialog.show();
    }
}

package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.RoomDetailDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;

import java.awt.event.ActionEvent;

public class ShowRoomDetailAction extends BaseAction {

    public ShowRoomDetailAction(BaseTable table) {
        super("Show detail", Icons.EDIT_ICON, table);  //TODO Stajlo najst vhodnu info ikonku
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
        var roomDetailDialog = new RoomDetailDialog(((RoomTableViewModel) table.getModel()).getRow(selectedRow));
        roomDetailDialog.setVisible(true);
    }

    public void run() {
        actionPerformed(null);
    }
}

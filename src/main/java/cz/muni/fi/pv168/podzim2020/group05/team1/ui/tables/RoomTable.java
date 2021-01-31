package cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables;

import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomMouseListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.EditRoomAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.ShowRoomDetailAction;

import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

public class RoomTable extends BaseTable {

    private ShowRoomDetailAction showRoomDetailAction;
    private EditRoomAction editRoomAction;

    public RoomTable(AbstractTableModel viewModel) {
        super(viewModel);
        selectionChanged(null);
        getSelectionModel().addListSelectionListener(this::selectionChanged);
        addMouseListener(new CustomMouseListener(showRoomDetailAction));
    }

    protected JPopupMenu createPeopleTablePopupMenu() {
        var menu = new JPopupMenu();
        showRoomDetailAction = new ShowRoomDetailAction(this);
        editRoomAction = new EditRoomAction(this);
        menu.add(editRoomAction);
        menu.add(showRoomDetailAction);
        return menu;
    }

    public void selectionChanged(ListSelectionEvent listSelectionEvent) {
        showRoomDetailAction.setEnabled(getSelectedRows().length == 1);
        editRoomAction.setEnabled(getSelectedRows().length == 1);
    }
}

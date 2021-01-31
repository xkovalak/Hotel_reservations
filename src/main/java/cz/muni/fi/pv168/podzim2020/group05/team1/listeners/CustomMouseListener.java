package cz.muni.fi.pv168.podzim2020.group05.team1.listeners;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.ShowRoomDetailAction;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMouseListener extends MouseAdapter {

    private ShowRoomDetailAction showRoomDetailAction;

    public CustomMouseListener(ShowRoomDetailAction showRoomDetailAction) {
        this.showRoomDetailAction = showRoomDetailAction;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JTable table =(JTable) e.getSource();
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            showRoomDetailAction.run();
        }
    }
}

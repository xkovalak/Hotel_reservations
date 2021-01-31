package cz.muni.fi.pv168.podzim2020.group05.team1.listeners;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.MainFrame;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class PeopleTableListener implements TableModelListener {

    private ServiceLayer inst;

    public PeopleTableListener() {
        inst = ServiceLayer.getInstance();
    }

    public void tableChanged(TableModelEvent e) {
        switch (e.getType()) {
            case TableModelEvent.INSERT:
            case TableModelEvent.DELETE:
            case TableModelEvent.UPDATE:
                inst.updatePeopleAndRooms();
                MainFrame.getInfoPanel().updateCurrentPeople(inst.getCurrentPeople());
                MainFrame.getInfoPanel().updateRoomCapacity(inst.getOccupiedRooms(), inst.getAllRooms());
                break;
            default:
        }
    }
}


package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.CheckOutDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;

import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

public class CheckOutAction extends BaseAction {

    public CheckOutAction() {
        super("Check Out");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var roomTable = ServiceLayer.getInstance().getRoomTable();
        var row = roomTable.convertRowIndexToModel(roomTable.getSelectedRow());

        var roomNumber = (int) ServiceLayer.getInstance().getRoomTable().getModel()
                .getValueAt(row, 0);

        var room = ((RoomTableViewModel)ServiceLayer.getInstance().getRoomTable().getModel())
                .getContext()
                .stream()
                .filter(x -> x.getNumber() == roomNumber)
                .findFirst()
                .get();

        if (room.isFree())
        {
            return;
        }

        var people = ((PersonTableViewModel) ServiceLayer.getInstance().getPeopleTable().getModel())
                .getContext()
                .stream()
                .filter(x -> x.getRoom().equals(String.valueOf(roomNumber)))
                .collect(Collectors.toList());

        ServiceLayer.getInstance().updateRoomAvailability(room);
        room.setFree(true);
        
        for(var person : people){
            person.setRoom("-");

            ServiceLayer.getInstance().updatePerson(person);
        }

        ServiceLayer.getInstance().refreshRoomTable();

        var dialog = new CheckOutDialog(people.get(0).getReservation_id(), room);
        dialog.show();
    }
}

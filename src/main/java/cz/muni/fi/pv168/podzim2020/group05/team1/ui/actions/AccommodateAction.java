package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccommodateAction extends BaseAction {

    private static I18N I18n = new I18N(AccommodateAction.class);

    public AccommodateAction() {
        super(I18n.getString("accommodate"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var indices = ServiceLayer.getInstance().getPeopleTable().getSelectionModel().getSelectedIndices();

        int j = 0;
        for (int i : indices) {
            indices[j++] = ServiceLayer.getInstance().getPeopleTable().convertRowIndexToModel(i);
        }

        var selectedPeople = ServiceLayer.getInstance().getPeopleTable().getSelectedPeople();

        var allFreeRooms = ((RoomTableViewModel) ServiceLayer.getInstance().getRoomTable().getModel())
                .getContext()
                .stream()
                .filter(RoomModel::isFree)
                .filter(x -> x.getBeds() >= (long) selectedPeople.size())
                .collect(Collectors.toCollection(ArrayList::new));

        if (allFreeRooms.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    I18n.getString("fullRooms"),
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        var room = allFreeRooms.get(0);

        int i = 0;

        ServiceLayer.getInstance().updateRoomAvailability(room);

        for(var person : selectedPeople){
            if (!person.getRoom().equals("X")){
                return;
            }

            person.setRoom(String.valueOf(room.getNumber()));

            ServiceLayer.getInstance().updatePerson(person, indices[i++]);
        }
    }
}

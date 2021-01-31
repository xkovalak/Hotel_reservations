package cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;

import java.util.List;

public class RoomTableViewModel extends BaseViewModel<RoomModel> {

    private static I18N I18n = new I18N(RoomTableViewModel.class);

    private static final List<Column<?, RoomModel>> COLUMNS = List.of(
            Column.newColumn(I18n.getString("number"), Integer.class, RoomModel::getNumber),
            Column.newColumn(I18n.getString("typeOfRoom"), TypeOfRoom.class, RoomModel::getTypeOfRoom),
            Column.newColumn(I18n.getString("beds"), Integer.class, RoomModel::getBeds),
            Column.newColumn(I18n.getString("price"), Double.class, RoomModel::getPrice),
            Column.newColumn(I18n.getString("free"), Boolean.class, RoomModel::isFree)
    );

    public RoomTableViewModel() {
        super(COLUMNS);
    }

    public RoomTableViewModel(List<RoomModel> rooms) {
        super(rooms, COLUMNS);
    }

    public int getRowByRoomNumber(int roomNumber) {
        int i;
        for (i = 0; i < context.size(); i++) {
            if (context.get(i).getNumber() == roomNumber) {
                return i;
            }
        }
        return -1;
    }
}

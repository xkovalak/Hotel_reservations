package cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.ReservationModel;

import java.sql.Date;
import java.util.List;

public class ReservationTableViewModel extends BaseViewModel<ReservationModel> {

    private static I18N I18n = new I18N(ReservationTableViewModel.class);

    private static final List<Column<?, ReservationModel>> COLUMNS = List.of(
            Column.newColumn(I18n.getString("id"), Long.class, ReservationModel::getId),
            Column.newColumn(I18n.getString("dateFrom"), Date.class, ReservationModel::getDateFrom),
            Column.newColumn(I18n.getString("dateTo"), Date.class, ReservationModel::getDateTo),
            Column.newColumn(I18n.getString("customer"), PersonModel.class, ReservationModel::getCustomer),
            Column.newColumn(I18n.getString("phone"), String.class, ReservationModel::getPhone),
            Column.newColumn(I18n.getString("mail"), String.class, ReservationModel::getEmail),
            Column.newColumn(I18n.getString("status"), ReservationStatus.class, ReservationModel::getStatus)
    );

    public ReservationTableViewModel() {
        super(COLUMNS);
    }

    public ReservationTableViewModel(List<ReservationModel> reservations) {
        super(reservations, COLUMNS);
    }

    public void addRow(ReservationModel reservation) {
        int newRowIndex = context.size();
        context.add(reservation);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }
}

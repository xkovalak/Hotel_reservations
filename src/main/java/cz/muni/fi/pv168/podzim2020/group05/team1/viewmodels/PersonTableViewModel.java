package cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;

import java.util.List;

public class PersonTableViewModel extends BaseViewModel<PersonModel> {

    private static I18N I18n = new I18N(PersonTableViewModel.class);

    private static final List<Column<?, PersonModel>> COLUMNS = List.of(
            Column.newColumn(I18n.getString("firstName"), String.class, PersonModel::getName),
            Column.newColumn(I18n.getString("surname"), String.class, PersonModel::getSurname),
            Column.newColumn(I18n.getString("idDoc"), String.class, PersonModel::getIdDoc),
            Column.newColumn(I18n.getString("phone"), String.class, PersonModel::getPhoneNumber),
            Column.newColumn(I18n.getString("room"), String.class, PersonModel::getRoom)
    );

    public PersonTableViewModel() {
        super(COLUMNS);
    }

    public PersonTableViewModel(List<PersonModel> data) {
        super(data, COLUMNS);
    }

    public void addRow(PersonModel person) {
        int newRowIndex = context.size();
        context.add(person);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public int getRowById(long id) {
        int i;
        for (i = 0; i < context.size(); i++) {
            if (context.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}

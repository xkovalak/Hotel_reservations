package cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.EditPersonAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;

import javax.swing.JPopupMenu;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PeopleTable extends BaseTable {

    public PeopleTable(AbstractTableModel viewModel) {
        super(viewModel);
    }

    protected JPopupMenu createPeopleTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(new EditPersonAction(this));
        return menu;
    }

    private PersonModel getRow(int index){
        var model = (PersonTableViewModel) getModel();
        return model.getRow(index);
    }

    public ArrayList<PersonModel> getSelectedPeople(){
        var indices = getSelectionModel().getSelectedIndices();

        for (int i = 0; i < indices.length; i++) {
            indices[i] = ServiceLayer.getInstance().getPeopleTable().convertRowIndexToModel(indices[i]);
        }

        ArrayList<PersonModel> ppl = new ArrayList<>();

        for(var index : indices){
            ppl.add(getRow(index));
        }

        return ppl;
    }
}

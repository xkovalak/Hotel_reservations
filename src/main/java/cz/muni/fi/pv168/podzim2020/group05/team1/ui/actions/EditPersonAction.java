package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ConfirmPerson;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.BaseJDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.PeopleModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class EditPersonAction extends BaseAction {

    private static I18N I18n = new I18N(EditPersonAction.class);

    public EditPersonAction(BaseTable table) {
        super("Edit", Icons.EDIT_ICON, table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showMessageDialog(null,
                    I18n.getString("warningMessage"),
                    I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
        } else {
            int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
            var model = (PersonTableViewModel) table.getModel();
            PersonModel person = model.getRow(selectedRow);

            BaseJDialog dialog = new PeopleModifyDialog(person, ConfirmPerson.UPDATE_PERSON, model, selectedRow);
            dialog.show();
        }
    }
}

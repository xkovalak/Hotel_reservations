package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.PeopleModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import java.awt.event.ActionEvent;

public class OpenPeopleModifyDialogAction extends BaseAction {

    private PeopleModifyDialog modifyDialog;

    public OpenPeopleModifyDialogAction(PeopleModifyDialog modifyDialog) {
        super("Modify person", Icons.EDIT_ICON);

        this.modifyDialog = modifyDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        modifyDialog.setVisible(true);
    }
}

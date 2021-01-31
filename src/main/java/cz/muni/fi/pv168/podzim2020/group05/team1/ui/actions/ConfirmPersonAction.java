package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ConfirmPerson;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.PeopleModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class ConfirmPersonAction extends BaseAction {

    private static I18N I18n = new I18N(ConfirmPersonAction.class);

    private static final String NAME_PATTERN = "(\\p{L}+)";
    private static final String PHONE_PATTERN1 = "^\\+[0-9]{12}";
    private static final String PHONE_PATTERN2 = "[0-9]{10}";
    private static final String EMAIL_PATTERN = "^(.+)@(.+).[a-z]{2,4}$";

    private PeopleModifyDialog dialog;
    private ConfirmPerson confirmPerson;
    private long personId = 0;

    public ConfirmPersonAction(PeopleModifyDialog dialog, ConfirmPerson confirmPerson, long personId) {
        super("Confirm Person", Icons.ADD_ICON);

        this.dialog = dialog;
        this.confirmPerson = confirmPerson;
        this.personId = personId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var nameS = dialog.getPName().getField();
        var surnameS = dialog.getPSurname().getField();
        var idcardS = dialog.getPIdcard().getField();
        var phoneS = dialog.getPPhone().getField();
        var emailS = dialog.getPEmail().getField();

        if (validatePersonInput(nameS, surnameS, idcardS, phoneS, emailS)) {
            var person = new PersonModel(nameS, surnameS, idcardS, phoneS, emailS);

            if (confirmPerson == ConfirmPerson.ADD_PERSON) {
                var reserTable = ServiceLayer.getInstance().getReservationTable();
                var selectedRow = reserTable.convertRowIndexToModel(reserTable.getSelectedRow());
                var selectedReservation = (long) ServiceLayer.getInstance()
                        .getReservationTable().getModel().getValueAt(selectedRow, 0);

                person.setReservation_id(selectedReservation);

                ServiceLayer.getInstance().createNewPerson(person);
                dialog.getCheckInDialog().addPerson(person);
                dialog.setPerson(new PersonModel());

            } else {
                person.setId(personId);
                ServiceLayer.getInstance().updatePerson(person);
            }

            dialog.dispose();
        }
    }

    private boolean validatePersonInput(String name, String surname, String IDcard, String phone, String email) {
        if (!name.matches(NAME_PATTERN)) {
            return warningMessageWithFalse(I18n.getString("wrongName"));
        }

        else if (!surname.matches(NAME_PATTERN)) {
            return warningMessageWithFalse(I18n.getString("wrongSurname"));
        }

        else if (!phone.isEmpty() && !phone.matches(PHONE_PATTERN1) && !phone.matches(PHONE_PATTERN2)) {
            return warningMessageWithFalse(I18n.getString("wrongPhone"));
        }

        else if (!email.isEmpty() && email != "@" && !email.matches(EMAIL_PATTERN)) {
            return warningMessageWithFalse(I18n.getString("wrongMail"));
        }

        return true;
    }

    private boolean warningMessageWithFalse(String message) {
        JOptionPane.showMessageDialog(dialog, message, I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
        return false;
    }
}

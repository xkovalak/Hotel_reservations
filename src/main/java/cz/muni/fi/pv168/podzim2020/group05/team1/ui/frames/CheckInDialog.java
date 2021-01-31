package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ConfirmPerson;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.CloseWindowAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.OpenPeopleModifyDialogAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.ButtonWithButtonComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.StringWithStringComponent;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

public class CheckInDialog extends BaseJDialog {

    private static I18N I18n = new I18N(CheckInDialog.class);

    private PeopleModifyDialog modifyDialog;

    private ButtonWithButtonComponent buttons;

    private ArrayList<String> people;

    private CloseWindowAction closeWindowAction;
    private OpenPeopleModifyDialogAction openPeopleModifyDialogAction;

    private GridBagLayout layout;
    private GridBagConstraints gbc;

    public CheckInDialog() {
        super(I18n.getString("title"), Color.DARK_GRAY, 250, 250);

        this.modifyDialog = new PeopleModifyDialog(this, new PersonModel(), ConfirmPerson.ADD_PERSON);
        modifyDialog.setVisible(false);

        people = new ArrayList<>();

        closeWindowAction = new CloseWindowAction(this);
        openPeopleModifyDialogAction = new OpenPeopleModifyDialogAction(modifyDialog);

        layout = new GridBagLayout();
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        setLayout(layout);
        setResizable(true);

        SetTextView();
    }

    @Override
    protected void SetTextView() {
        var customer = ServiceLayer.getInstance().getSelectedRowDataFromReservationTable();
        String nameOfCustomer = customer.getCustomer().getName() + " " + customer.getCustomer().getSurname();

        new StringWithStringComponent(this, I18n.getString("reservationBy") + " : ", nameOfCustomer, gbc);
        addButtons();
    }

    public void addPerson(PersonModel person){
        new StringWithStringComponent(this, I18n.getString("person") + " : ",
                person.getName() + " " + person.getSurname(), gbc);

        addButtons();
    }

    private void addButtons(){
        if (buttons != null){
            remove(buttons.getFirstButton());
            remove(buttons.getSecondButton());
        }

        buttons = new ButtonWithButtonComponent(this, I18n.getString("ok"), I18n.getString("addPerson"), gbc);
        buttons.getFirstButton().addActionListener(closeWindowAction);
        buttons.getSecondButton().addActionListener(openPeopleModifyDialogAction);
        revalidate();
    }
}

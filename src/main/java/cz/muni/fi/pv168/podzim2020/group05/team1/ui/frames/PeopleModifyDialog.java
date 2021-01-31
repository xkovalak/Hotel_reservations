package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ConfirmPerson;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.ConfirmPersonAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.ButtonWithButtonComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithStringComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;

public class PeopleModifyDialog extends BaseJDialog {

    private static I18N I18n = new I18N(PeopleModifyDialog.class);

    private TextboxWithStringComponent name;
    private TextboxWithStringComponent surname;
    private TextboxWithStringComponent idcard;
    private TextboxWithStringComponent phone;
    private TextboxWithStringComponent email;
    private TextboxWithStringComponent roomId;
    private JLabel reservationID;
    private ButtonWithButtonComponent buttons;
    private PersonModel person;
    private ConfirmPerson confirmPerson;
    private PersonTableViewModel model;
    private CheckInDialog checkInDialog;
    private int row;
    private long reservationId;

    public PeopleModifyDialog(CheckInDialog checkInDialog, PersonModel person, ConfirmPerson confirmPerson) {
        super(I18n.getString("title"), Color.DARK_GRAY, 300, 275);

        this.person = person;
        this.confirmPerson = confirmPerson;
        this.checkInDialog = checkInDialog;

        setLayout(new GridLayout(7, 2));
        setResizable(false);
        SetTextView();
        setPersonData();
    }

    public PeopleModifyDialog(PersonModel person, ConfirmPerson confirmPerson, PersonTableViewModel model, int row) {
        this(null, person, confirmPerson);
        this.model = model;
        this.row = row;
    }

    @Override
    protected void SetTextView() {
        name = new TextboxWithStringComponent(this, I18n.getString("name") + " : ", 15);
        surname = new TextboxWithStringComponent(this, I18n.getString("surname") + " : ", 15);
        idcard = new TextboxWithStringComponent(this,I18n.getString("idDoc") + " : ", 15);
        phone = new TextboxWithStringComponent(this,I18n.getString("phone") + " : ", 15);
        email = new TextboxWithStringComponent(this,I18n.getString("mail") + " : ", 15);
        add(new JLabel(I18n.getString("reservationID") + " : "));
        reservationID = new JLabel();
        add(reservationID);
        buttons = new ButtonWithButtonComponent(this, I18n.getString("ok"), I18n.getString("cancel"));
        buttons.getFirstButton().addActionListener(new ConfirmPersonAction(this, confirmPerson, person.getId()));
    }

    private void setPersonData(){
        name.setField(person.getName());
        surname.setField(person.getSurname());
        idcard.setField(person.getIdDoc());
        phone.setField(person.getPhoneNumber());
        email.setField(person.getEmail());
        if (confirmPerson == ConfirmPerson.UPDATE_PERSON){
            reservationID.setText(String.valueOf(person.getReservation_id()));
        }
    }

    public CheckInDialog getCheckInDialog() {return checkInDialog;}

    public TextboxWithStringComponent getPName() {
        return name;
    }

    public TextboxWithStringComponent getPSurname() {
        return surname;
    }

    public TextboxWithStringComponent getPIdcard() {
        return idcard;
    }

    public TextboxWithStringComponent getPPhone() {
        return phone;
    }

    public TextboxWithStringComponent getPEmail() {
        return email;
    }

    public TextboxWithStringComponent getPRoomId() {
        return roomId;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
        setPersonData();
    }

    public PersonTableViewModel getModel() {
        return model;
    }

    public int getRow() {
        return row;
    }
}

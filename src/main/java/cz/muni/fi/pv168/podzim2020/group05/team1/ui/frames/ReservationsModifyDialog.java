package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.CheckInReservationAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.ConfirmReservationAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.ButtonWithButtonComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithDatePicker;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithStringComponent;

import java.awt.Color;
import java.awt.GridLayout;

public class ReservationsModifyDialog extends BaseJDialog {

    private static I18N I18n = new I18N(ReservationsModifyDialog.class);

    private TextboxWithStringComponent textCustomer;
    private TextboxWithStringComponent textPhone;
    private TextboxWithStringComponent textEmail;
    private TextboxWithDatePicker textDateFrom;
    private TextboxWithDatePicker textDateTo;
    private TextboxWithStringComponent textHowManyPpl;

    private ButtonWithButtonComponent buttons;

    private ConfirmReservationAction confirmAction;
    private CheckInReservationAction checkInAction;

    private boolean createReservation;

    public ReservationsModifyDialog(boolean create) {
        super(I18n.getString("title"), Color.DARK_GRAY, 300, 275);
        this.confirmAction = new ConfirmReservationAction(this);
        this.checkInAction = new CheckInReservationAction();

        createReservation = create;

        setLayout(new GridLayout(7, 2));
        setResizable(false);
        SetTextView();
        pack();
    }

    @Override
    protected void SetTextView(){
        textCustomer = new TextboxWithStringComponent(this,I18n.getString("customer") + " : ", 15);
        textPhone = new TextboxWithStringComponent(this,I18n.getString("phone") + " : ", 15);
        textEmail = new TextboxWithStringComponent(this,I18n.getString("mail") + " : ", 15);
        textDateFrom = new TextboxWithDatePicker(this,I18n.getString("dateFrom") + " : ");
        textDateTo = new TextboxWithDatePicker(this,I18n.getString("dateTo") + " : ");
        textHowManyPpl = new TextboxWithStringComponent(this, I18n.getString("forHowManyPeople") + " :", 15);

        buttons = new ButtonWithButtonComponent(this, I18n.getString("ok"));
        buttons.getFirstButton().addActionListener(confirmAction);
    }

    public TextboxWithStringComponent getTextCustomer() {
        return textCustomer;
    }

    public TextboxWithStringComponent getTextPhone() {
        return textPhone;
    }

    public TextboxWithStringComponent getTextEmail() {
        return textEmail;
    }

    public TextboxWithDatePicker getTextDateFrom() {
        return textDateFrom;
    }

    public TextboxWithDatePicker getTextDateTo() {
        return textDateTo;
    }

    public TextboxWithStringComponent getTextHowManyPpl() {
        return textHowManyPpl;
    }
}

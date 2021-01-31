package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.ReservationModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.ReservationsModifyDialog;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConfirmReservationAction extends BaseAction {

    private static I18N I18n = new I18N(ConfirmReservationAction.class);

    private static final String CUSTOMER_PATTERN = "(\\p{L}+)\\s+(\\p{L}+)";
    private static final String PHONE_PATTERN1 = "^\\+[0-9]{12}";
    private static final String PHONE_PATTERN2 = "[0-9]{10}";
    private static final String EMAIL_PATTERN = "^(.+)@(.+).[a-z]{2,4}$";
    private static final String HMP_PATTERN = "^\\+?([1-9]\\d*)$";

    int roomsNumber = 0;
    private ReservationsModifyDialog dialog;

    public ConfirmReservationAction(ReservationsModifyDialog dialog) {
        super("Confirm reservation", Icons.EDIT_ICON);

        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var customer = dialog.getTextCustomer().getField();
        var phone = dialog.getTextPhone().getField();
        var email = dialog.getTextEmail().getField();
        var dateFromS = dialog.getTextDateFrom().getDateString();
        var dateToS = dialog.getTextDateTo().getDateString();
        var howManyPpl = dialog.getTextHowManyPpl().getField();

        try {
            if (validateInputs(customer, phone, email, dateFromS, dateToS, howManyPpl)) {
                java.util.Date utilDate1 = new SimpleDateFormat("dd.MM.yyyy").parse(dateFromS);
                java.sql.Date date1 = new java.sql.Date(utilDate1.getTime());
                java.util.Date utilDate2 = new SimpleDateFormat("dd.MM.yyyy").parse(dateToS);
                java.sql.Date date2 = new java.sql.Date(utilDate2.getTime());


                if (capacityGood(date1, date2, howManyPpl)) {
                    try {
                        String[] names = customer.split("\\s+");

                        PersonModel person = new PersonModel(names[0], names[1], phone, email);

                        ServiceLayer.getInstance().createNewReservation(
                                new ReservationModel(date1, date2, person, ReservationStatus.RESERVED, roomsNumber));
                        dialog.dispose(); //close frame after pressing OK button

                    } catch (IndexOutOfBoundsException xe) {
                        System.err.println("Add reservation: Error when parsing name: " + xe);
                        warningMessage(I18n.getString("IDKWhatHappenedWarningMessage"));
                    }
                } else {
                    int occupiedRooms = ServiceLayer.getInstance().getSumRooms(date1, date2);
                    warningMessage(I18n.getString("peopleWarningMessage1") + " " +
                            (ServiceLayer.getInstance().getAllRooms() - occupiedRooms)*2 + " " + I18n.getString("peopleWarningMessage2"));
                }
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void warningMessage(String message) {
        JOptionPane.showMessageDialog(dialog, message, I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
    }

    private boolean validateInputs(String customer, String phone, String email,
                                   String dateFrom, String dateTo, String hmp) throws ParseException {
        if (!customer.matches(CUSTOMER_PATTERN)) {
            warningMessage(I18n.getString("wrongName"));
            return false;
        }

        else if (!phone.matches(PHONE_PATTERN1) && !phone.matches(PHONE_PATTERN2)) {
            warningMessage(I18n.getString("wrongPhone"));
            return false;
        }

        else if (!email.matches(EMAIL_PATTERN)) {
            warningMessage(I18n.getString("wrongMail"));
            return false;
        }

        else if (dateFrom.isEmpty() || dateTo.isEmpty()) {
            warningMessage(I18n.getString("emptyDate"));
            return false;
        }

        java.util.Date utilDate1 = new SimpleDateFormat("dd.MM.yyyy").parse(dateFrom);
        java.sql.Date date1 = new java.sql.Date(utilDate1.getTime());
        java.util.Date utilDate2 = new SimpleDateFormat("dd.MM.yyyy").parse(dateTo);
        java.sql.Date date2 = new java.sql.Date(utilDate2.getTime());

        if (!date2.after(date1)) {
            warningMessage(I18n.getString("sameDate"));
            return false;
        }

        if (!hmp.matches(HMP_PATTERN)) {
            warningMessage(I18n.getString("wrongFHMP"));
            return false;
        }

        return true;
    }

    private boolean capacityGood(Date date1, Date date2, String hmp) {
        roomsNumber = (Integer.parseInt(hmp) + 2 - 1) / 2;
        int occupiedRooms = ServiceLayer.getInstance().getSumRooms(date1, date2);
        return (occupiedRooms + roomsNumber <= ServiceLayer.getInstance().getAllRooms());
    }
}

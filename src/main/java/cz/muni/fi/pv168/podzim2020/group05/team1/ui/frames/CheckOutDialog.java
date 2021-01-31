package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.StringWithStringComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.ReservationTableViewModel;

import java.awt.*;
import java.sql.Date;

public class CheckOutDialog extends BaseJDialog {

    private static I18N I18n = new I18N(CheckOutDialog.class);

    private double total;
    private long reservationId;
    private RoomModel room;
    private int days;
    private Date startDate;
    private Date endDate;

    public CheckOutDialog(long reservationId, RoomModel room) {
        super(I18n.getString("title"), Color.DARK_GRAY, 250, 250);

        this.room = room;
        this.reservationId = reservationId;

        setLayout(new GridLayout(3, 2));

        setTotal();
    }

    @Override
    protected void SetTextView() {
        new StringWithStringComponent(this, I18n.getString("textWithPrice") + " :", (String.valueOf(total)) + "€");
        new StringWithStringComponent(this, I18n.getString("textWithNights1") +  " ", days + " " + I18n.getString("textWithNights2"));
        new StringWithStringComponent(this, startDate.toString(), endDate.toString());
    }

    private void setTotal(){
        var pricePerNight = room.getPrice();

        var reservation = ((ReservationTableViewModel) ServiceLayer.getInstance()
                .getReservationTable().getModel())
                .getContext()
                .stream()
                .filter(x -> x.getId() == reservationId)
                .findFirst()
                .get();

        if (reservation == null){
            return;
        }

        startDate = reservation.getDateFrom();
        endDate = reservation.getDateTo();

        days = (int) (endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24;

        total = days * pricePerNight;

        SetTextView();
    }
}

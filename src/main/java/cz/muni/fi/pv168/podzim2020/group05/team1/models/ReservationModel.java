package cz.muni.fi.pv168.podzim2020.group05.team1.models;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;

import java.sql.Date;

public class ReservationModel extends BaseModel{

    private Date dateFrom;
    private Date dateTo;
    private PersonModel customer;
    private String phone;
    private String email;
    private ReservationStatus status;
    private int roomsCount;

    public ReservationModel(Date dateFrom, Date dateTo, PersonModel customer,
                            ReservationStatus status, int roomsCount) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.customer = customer;
        this.phone = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.status = status;
        this.roomsCount = roomsCount;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public PersonModel getCustomer() {
        return customer;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public ReservationStatus getStatus() { return status; }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }
}

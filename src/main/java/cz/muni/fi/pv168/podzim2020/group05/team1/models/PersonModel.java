package cz.muni.fi.pv168.podzim2020.group05.team1.models;

public class PersonModel extends BaseModel {

    private String name;
    private String surname;
    private String idDoc = "EK"; // identify document
    private String phoneNumber;

    private String email = "@";
    private String room = "X"; // toto bude aj tak typu RoomModel
    private long reservation_id = -1;

    public PersonModel() {}

    public PersonModel(String name, String surname, String idDoc, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.idDoc = idDoc;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Constructor for person created for reservation (has no IdDoc) but has contacts filled
    public PersonModel(String name, String surname, String phoneNumber, String email) {
        this(name, surname, "EK", phoneNumber, email);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getRoom() {
        return room;
    }

    public long getReservation_id() {
        return reservation_id;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}

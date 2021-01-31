package cz.muni.fi.pv168.podzim2020.group05.team1.models;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;

public class RoomModel extends BaseModel {
    private final int number;
    private TypeOfRoom typeOfRoom;
    private int beds;
    private boolean free;
    private double price;

    public RoomModel(int number, TypeOfRoom typeOfRoom, int beds, boolean free, double price) {
        this.number = number;
        this.typeOfRoom = typeOfRoom;
        this.beds = beds;
        this.free = free;
        this.price = price;
    }

    public void setTypeOfRoom(TypeOfRoom typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getNumber() {
        return number;
    }

    public TypeOfRoom getTypeOfRoom() {
        return typeOfRoom;
    }

    public int getBeds() {
        return beds;
    }

    public boolean isFree() {
        return free;
    }

    public double getPrice() {
        return price;
    }
}

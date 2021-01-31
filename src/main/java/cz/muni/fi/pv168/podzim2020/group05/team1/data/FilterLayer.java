package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import javax.swing.RowFilter;

public class FilterLayer {

    public static FilterLayer instance;

    private RowFilter peopleFilter;
    private RowFilter roomsFilter;
    private RowFilter reservationsFilter;

    private FilterLayer() {

    }

    public static FilterLayer getInstance() {
        if (instance == null) {
            instance = new FilterLayer();
        }
        return instance;
    }

    public void setPeopleFilter(RowFilter peopleFilter) {
        this.peopleFilter = peopleFilter;
    }

    public void setReservationsFilter(RowFilter reservationsFilter) {
        this.reservationsFilter = reservationsFilter;
    }

    public void setRoomsFilter(RowFilter roomsFilter) {
        this.roomsFilter = roomsFilter;
    }

    public RowFilter getPeopleFilter() {
        return peopleFilter;
    }

    public RowFilter getReservationsFilter() {
        return reservationsFilter;
    }

    public RowFilter getRoomsFilter() {
        return roomsFilter;
    }
}

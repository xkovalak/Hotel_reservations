package cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.PeopleTableListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.PeopleTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.ReservationTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.RoomTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.ReservationTableViewModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;

import javax.swing.JPanel;

public class MainCard extends JPanel {

    private RoomsCard rooms;
    private ReservationsCard reservations;
    private PeopleCard people;

    public MainCard() {
        this.setBounds(0,60,1280,660);

        RoomTable roomTable = new RoomTable(new RoomTableViewModel());
        ReservationTable reservationTable = new ReservationTable(new ReservationTableViewModel());
        PeopleTable peopleTable = new PeopleTable(new PersonTableViewModel());

        ServiceLayer.instanciate(roomTable, reservationTable, peopleTable);

        // lines <31,32> force change in people table so that the data in info panel show up while starting app
        peopleTable.getModel().addTableModelListener(new PeopleTableListener());
        var a = (PersonTableViewModel)peopleTable.getModel();
        a.fireTableDataChanged();

        people = new PeopleCard(peopleTable);
        reservations = new ReservationsCard(reservationTable);
        rooms = new RoomsCard(roomTable);
        peopleTable.centerCols();
        reservationTable.centerCols();
        roomTable.centerCols();

        this.add(reservations, "1");
        this.add(rooms, "2");
        this.add(people, "3");
    }

    public RoomsCard getRooms() {
        return rooms;
    }

    public ReservationsCard getReservations() {
        return reservations;
    }

    public PeopleCard getPeople() {
        return people;
    }
}

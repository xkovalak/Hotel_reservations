package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.ReservationModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.PeopleTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.ReservationTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.RoomTable;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.CustomTableRowSorter;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.ReservationTableViewModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.RoomTableViewModel;
import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.sql.DataSource;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.util.List;

public class ServiceLayer {

    private static ServiceLayer instance;

    private RoomTable roomTable;
    private ReservationTable reservationTable;
    private PeopleTable peopleTable;

    private PeopleDao databasePeople;
    private RoomDao databaseRooms;
    private ReservationDao databaseReservations;

    private int currentPeople;
    private int occupiedRooms;
    private int allRooms;

    private ServiceLayer(RoomTable roomTable, ReservationTable reservationTable, PeopleTable peopleTable) {
        this.roomTable = roomTable;
        this.reservationTable = reservationTable;
        this.peopleTable = peopleTable;
        initDataAccess();
        initTables();

        updatePeopleAndRooms();
        allRooms = roomTable.getRowCount();

        refreshTableRowSorter(TableType.PEOPLE_TABLE);
    }

    public static void instanciate(RoomTable roomTable, ReservationTable reservationTable, PeopleTable peopleTable) {
        if (instance == null) {
            instance = new ServiceLayer(roomTable, reservationTable, peopleTable);
        }
    }

    public static ServiceLayer getInstance() {
        if (instance == null) {
            throw new RuntimeException("Instanciation neeeded first");
        }
        return instance;
    }

    private void initDataAccess() {
        DataSource source = createDataSource();
        createDAOs(source);
    }

    private DataSource createDataSource() {
        String dbPath = System.getProperty("user.home") + "/hotel-reservations";
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName(dbPath);
        dataSource.setCreateDatabase("create");
        return dataSource;
    }

    private void createDAOs(DataSource source) {
        databasePeople = new PeopleDao(source);
        databaseRooms = new RoomDao(source);
        databaseReservations = new ReservationDao(source);
    }

    private void initTables() {
        List<PersonModel> people = databasePeople.fetchAll();
        List<RoomModel> rooms = databaseRooms.fetchAll();
        List<ReservationModel> reservations = databaseReservations.fetchAll();

        peopleTable.setModel(new PersonTableViewModel(people));
        roomTable.setModel(new RoomTableViewModel(rooms));
        reservationTable.setModel(new ReservationTableViewModel(reservations));
    }

    public void createNewPerson(PersonModel person) {
        databasePeople.addPerson(person);
        PersonTableViewModel model = (PersonTableViewModel) peopleTable.getModel();
        model.addRow(person);
    }

    public void updateReservationStatus(ReservationStatus status) {
        int row = reservationTable.getSelectedRow();

        String id = reservationTable.getModel().getValueAt(row, 0).toString();
        databaseReservations.updateStatus(status, Long.parseLong(id));

        ReservationTableViewModel model = (ReservationTableViewModel) reservationTable.getModel();
        model.getRow(row).setStatus(status);
        model.fireTableRowsUpdated(row, row);
    }

    public void updatePerson(PersonModel person) {
        databasePeople.update(person);

        PersonTableViewModel model = (PersonTableViewModel) peopleTable.getModel();
        int row = model.getRowById(person.getId());
        model.getRow(row).setName(person.getName());
        model.getRow(row).setSurname(person.getSurname());
        model.getRow(row).setIdDoc(person.getIdDoc());
        model.getRow(row).setPhoneNumber(person.getPhoneNumber());
        model.getRow(row).setEmail(person.getEmail());
        model.fireTableRowsUpdated(row, row);
    }

    public void updatePerson(PersonModel person, int selectedRow) {
        databasePeople.update(person);

        PersonTableViewModel model = (PersonTableViewModel) peopleTable.getModel();
        model.getRow(selectedRow).setName(person.getName());
        model.getRow(selectedRow).setSurname(person.getSurname());
        model.getRow(selectedRow).setIdDoc(person.getIdDoc());
        model.getRow(selectedRow).setPhoneNumber(person.getPhoneNumber());
        model.getRow(selectedRow).setEmail(person.getEmail());
        model.getRow(selectedRow).setRoom(person.getRoom());
        model.fireTableRowsUpdated(selectedRow, selectedRow);
    }

    public void updateRoom(RoomModel room) {
        databaseRooms.update(room);

        RoomTableViewModel model = (RoomTableViewModel) roomTable.getModel();
        int row = model.getRowByRoomNumber(room.getNumber());
        model.getRow(row).setTypeOfRoom(room.getTypeOfRoom());
        model.getRow(row).setBeds(room.getBeds());
        model.getRow(row).setPrice(room.getPrice());
        model.getRow(row).setFree(room.isFree());
        model.fireTableRowsUpdated(row, row);
    }

    public void updateRoomAvailability(RoomModel room) {
        databaseRooms.updateAvailability(!room.isFree(), room.getNumber());
        room.setFree(!room.isFree());
    }

    public void createNewReservation(ReservationModel reservation) {
        databaseReservations.addReservation(reservation);
        ReservationTableViewModel model = (ReservationTableViewModel) reservationTable.getModel();
        model.addRow(reservation);
    }

    public ReservationModel getSelectedRowDataFromReservationTable() {
        var model = (ReservationTableViewModel) reservationTable.getModel();
        return model.getRow(reservationTable.convertRowIndexToModel(reservationTable.getSelectedRow()));
    }

    public int getSelectedRowCount(TableType type) {
        switch (type) {
            case ROOMS_TABLE:
                return roomTable.getSelectedRowCount();
            case RESERVATIONS_TABLE:
                return reservationTable.getSelectedRowCount();
            case PEOPLE_TABLE:
                return peopleTable.getSelectedRowCount();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void refreshTableRowSorter(TableType type) {
        BaseTable selectedTable;
        RowFilter selectedFilter;

        switch (type) {
            case ROOMS_TABLE:
                selectedTable = roomTable;
                selectedFilter = FilterLayer.getInstance().getRoomsFilter();
                break;
            case RESERVATIONS_TABLE:
                selectedTable = reservationTable;
                selectedFilter = FilterLayer.getInstance().getReservationsFilter();
                break;
            case PEOPLE_TABLE:
                selectedTable = peopleTable;
                selectedFilter = FilterLayer.getInstance().getPeopleFilter();
                CustomTableRowSorter sorter = new CustomTableRowSorter(selectedTable.getModel());
                sorter.setRowFilter(selectedFilter);
                sorter.toggleSortOrder(4);
                selectedTable.setRowSorter(sorter);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(selectedTable.getModel());
        sorter.setRowFilter(selectedFilter);
        selectedTable.setRowSorter(sorter);
    }

    public RoomTable getRoomTable() {
        return roomTable;
    }

    public ReservationTable getReservationTable() {
        return reservationTable;
    }

    public PeopleTable getPeopleTable() {
        return peopleTable;
    }

    public void updatePeopleAndRooms() {
        currentPeople = (int) ((PersonTableViewModel) peopleTable.getModel()).getContext().stream()
                .filter(person -> !person.getRoom().equals("X") && !person.getRoom().equals("-"))
                .count();

        occupiedRooms = (int) ((RoomTableViewModel) roomTable.getModel()).getContext().stream()
                .filter(room -> !room.isFree())
                .count();
    }

    public int getSumRooms(Date date1, Date date2) {
        return databaseReservations.roomsBetweenDates(date1, date2);
    }

    public int getAllCountRooms() {
        return databaseRooms.getRowsCount();
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public int getOccupiedRooms() {
        return occupiedRooms;
    }

    public int getAllRooms() {
        return allRooms;
    }

    public void refreshRoomTable() {
        var a = (RoomTableViewModel) roomTable.getModel();
        a.fireTableDataChanged();
    }
}

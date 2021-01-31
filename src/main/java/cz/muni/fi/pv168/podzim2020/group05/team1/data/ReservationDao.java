package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.ReservationModel;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {
    private DataSource source;

    ReservationDao(DataSource source) {
        this.source = source;
        initTable();
    }

    public List<ReservationModel> fetchAll() {
        try (var connection = source.getConnection();
             var st = connection
                     .prepareStatement("SELECT ID, DATE_FROM, DATE_TO, CUSTOMER, PHONE, EMAIL, STATUS, ROOMS FROM " +
                             "RESERVATIONS")) {

            List<ReservationModel> reservations = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    PersonModel person = new PersonModel(
                            rs.getString("CUSTOMER").split("\\s+")[0],
                            rs.getString("CUSTOMER").split("\\s+")[1],
                            rs.getString("PHONE"),
                            rs.getString("EMAIL")
                    );
                    ReservationModel reservation = new ReservationModel(
                            rs.getDate("DATE_FROM"),
                            rs.getDate("DATE_TO"),
                            person,
                            ReservationStatus.valueOf(rs.getString("STATUS")),
                            rs.getInt("ROOMS")
                    );

                    reservation.setId(rs.getLong("ID"));
                    reservations.add(reservation);
                }

                return reservations;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load reservations from database.", ex);
        }
    }


    public void addReservation(ReservationModel reservation) {
        if (reservation.getId() != 0) {
            throw new IllegalArgumentException("Reservation already has ID: " + reservation);
        }
        try (var connection = source.getConnection();
             var st = connection
                     .prepareStatement(
                             "INSERT INTO RESERVATIONS (DATE_FROM, DATE_TO, CUSTOMER, PHONE, EMAIL, STATUS, ROOMS) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS)) {
            st.setDate(1, new Date(reservation.getDateFrom().getTime()));
            st.setDate(2, new Date(reservation.getDateTo().getTime()));
            st.setString(3, reservation.getCustomer().toString());
            st.setString(4, reservation.getPhone());
            st.setString(5, reservation.getEmail());
            st.setString(6, reservation.getStatus().name());
            st.setInt(7, reservation.getRoomsCount());
            st.executeUpdate();

            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    reservation.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException("Failed to fetch generated key: no key returned for reservation: " + reservation);
                    // nepotrebujem test
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not add reservation to database: " + reservation, e);
        }
    }

    public void updateStatus(ReservationStatus status, long id) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE RESERVATIONS SET STATUS = ? WHERE ID = ?")) {

            st.setString(1, status.toString());
            st.setLong(2, id);

            int rowUpdated = st.executeUpdate();
            if (rowUpdated == 0) {
                throw new RuntimeException("Failed to update status of non-existing reservation: " + status + " " + id);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update status of reservation: " + status + " " + id, ex);
        }
    }

    public int roomsBetweenDates(Date date1, Date date2) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "SELECT SUM(ROOMS) FROM RESERVATIONS WHERE (((? <= DATE_FROM AND DATE_FROM <= ?) OR (DATE_FROM " +
                             "<= ? AND ? <= DATE_TO)) AND (STATUS = 'RESERVED' OR STATUS = 'CHECKED_IN'))"
             ))
         {
             st.setDate(1, date1);
             st.setDate(2, date2);
             st.setDate(3, date1);
             st.setDate(4, date1);

             var rs = st.executeQuery();
             rs.next();
             return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to return dates between in RESERVATIONS table: " + date1 + " " + date2, e);
        }
    }

    private void initTable() {
        if (!tableExits("APP", "RESERVATIONS")) {
            createTable();
        }
    }

    private boolean tableExits(String schemaName, String tableName) {
        try (var connection = source.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to detect if the table " + schemaName + "." + tableName + " exist");
            // nepotrebujem test
        }
    }

    public ReservationModel findById(long id) {
        try(var connection = source.getConnection();
            var st = connection.prepareStatement("SELECT * FROM RESERVATIONS WHERE ID = ?")) {
            st.setLong(1, id);
            try(var rs = st.executeQuery()) {
                if (rs.next()) {
                    var person = new PersonModel(
                            rs.getString("CUSTOMER").split("\\s+")[0],
                            rs.getString("CUSTOMER").split("\\s+")[1],
                            rs.getString("PHONE"),
                            rs.getString("EMAIL")
                    );
                    ReservationStatus status = ReservationStatus.valueOf(rs.getString("STATUS"));

                    var reservation = new ReservationModel(
                            rs.getDate("DATE_FROM"),
                            rs.getDate("DATE_TO"),
                            person,
                            status,
                            rs.getInt("ROOMS")
                    );
                    reservation.setId(rs.getLong("ID"));
                    return reservation;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load reservation ID " + id, ex);
        }
    }

    private void createTable() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE APP.RESERVATIONS (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 100000)," +
                    "\"DATE_FROM\" DATE NOT NULL," +
                    "\"DATE_TO\" DATE NOT NULL," +
                    "\"CUSTOMER\" VARCHAR(50) NOT NULL," +
                    "\"PHONE\" VARCHAR(50) NOT NULL," +
                    "\"EMAIL\" VARCHAR(50) NOT NULL," +
                    "\"STATUS\" VARCHAR(50) NOT NULL," +
                    "\"ROOMS\" INTEGER NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException("Unable to create Reservations Table: " + ex);
        }
    }

    public void dropTable() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.RESERVATIONS");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to drop RESERVATIONS table ", ex);
        }
    }
}

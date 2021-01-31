package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.ReservationStatus;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.ReservationModel;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class ReservationDaoTest {

    private static EmbeddedDataSource dataSource;
    private ReservationDao reservationDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservations-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createReservationDao() throws SQLException {
        reservationDao = new ReservationDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.RESERVATIONS");
        }
    }

    @AfterEach
    void cleanUp() {
        reservationDao.dropTable();
    }

    @Test
    void addReservation() {
        var it = createReservationModel("2021-02-17", "2021-02-22", "Matelko", "Testovic",
                "4204206969", "mat@test.sk", ReservationStatus.RESERVED);

        reservationDao.addReservation(it);

        assertThat(it.getId()).isNotNull();
    }

    @Test
    void addReservationWithExistingID() {
        var reservation = createReservationModel("2021-02-18", "2021-02-22", "Jozin",
                "Zbazin", "5555555555", "jozin@zbaz.in", ReservationStatus.RESERVED);

        reservation.setId(420L);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> reservationDao.addReservation(reservation))
                .withMessage("Reservation already has ID: " + reservation);
    }

    @Test
    void addReservationWithException() {
        var sqlException = new SQLException();
        ReservationDao failingDao = createFailingDao(sqlException);

        var it = createReservationModel("2021-02-17", "2021-02-23", "Matel=jko", "Restovic",
                "4204206969", "mat@rest.sk", ReservationStatus.RESERVED);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> failingDao.addReservation(it))
                .withMessage("Could not add reservation to database: " + it)
                .withCause(sqlException);
    }

    @Test
    void fetchAllWithException() {
        var sqlException = new SQLException();
        ReservationDao failingDao = createFailingDao(sqlException);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(failingDao::fetchAll)
                .withMessage("Failed to load reservations from database.")
                .withCause(sqlException);
    }

    @Test
    void updateStatus() {
        var reservation = createReservationModel("2021-02-17", "2021-02-23", "Kral", "Java",
                "4204206969", "king@java.sk", ReservationStatus.RESERVED);
        reservationDao.addReservation(reservation);

        var id = reservation.getId();
        reservationDao.updateStatus(ReservationStatus.CANCELED, id);
        assertThat(reservationDao.findById(id).getStatus()).isEqualByComparingTo(ReservationStatus.CANCELED);
    }

    @Test
    void updateStatusWithException() {
        var sqlException = new SQLException();
        ReservationDao failingDao = createFailingDao(sqlException);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> failingDao.updateStatus(ReservationStatus.CANCELED, 100000L))
                .withMessage("Failed to update status of reservation: " + ReservationStatus.CANCELED + " " + 100000L)
                .withCause(sqlException);
    }

    @Test
    void updateStatusOfNonExisting() {
        assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(() -> reservationDao.updateStatus(ReservationStatus.CANCELED, 15L))
        .withMessage("Failed to update status of non-existing reservation: " + ReservationStatus.CANCELED + " " + 15L);
    }

    @Test
    void roomsBetween() {
        var r1 = createReservationModel("2021-02-16", "2021-02-18", "a", "a",
                "01234567890", "a@a.sk", ReservationStatus.RESERVED);
        var r2 = createReservationModel("2021-02-17", "2021-02-22", "b", "b",
                "01234567890", "b@b.sk", ReservationStatus.RESERVED);
        var r3 = createReservationModel("2021-03-16", "2021-03-18", "c", "c",
                "01234567890", "c@c.sk", ReservationStatus.RESERVED);

        reservationDao.addReservation(r1);
        reservationDao.addReservation(r2);
        reservationDao.addReservation(r3);

        var d1 = java.sql.Date.valueOf("2021-03-19");
        var d2 = java.sql.Date.valueOf("2021-03-22");
        assertThat(reservationDao.roomsBetweenDates(d1, d2)).isEqualTo(0);

        var d3 = java.sql.Date.valueOf("2021-02-14");
        var d4 = java.sql.Date.valueOf("2021-02-18");
        assertThat(reservationDao.roomsBetweenDates(d3, d4)).isEqualTo(2);
    }

    @Test
    void roomsBetweenWithException() {
        var sqlException = new SQLException();
        ReservationDao failingDao = createFailingDao(sqlException);

        var d1 = java.sql.Date.valueOf("2021-03-15");
        var d2 = java.sql.Date.valueOf("2021-03-22");

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> failingDao.roomsBetweenDates(d1, d2))
                .withMessage("Failed to return dates between in RESERVATIONS table: " + d1 + " " + d2)
                .withCause(sqlException);
    }

    @Test
    void fetchAllEmpty() {
        assertThat(reservationDao.fetchAll())
            .isEmpty();
    }

//    @Test
//    void fetchAll() {
//        var r1 = createReservationModel("2021-02-16", "2021-02-18", "a", "a",
//                "01234567890", "a@a.sk", ReservationStatus.RESERVED);
//        var r2 = createReservationModel("2021-02-17", "2021-02-22", "b", "b",
//                "01234567890", "b@b.sk", ReservationStatus.RESERVED);
//        var r3 = createReservationModel("2021-03-16", "2021-03-18", "c", "c",
//                "01234567890", "c@c.sk", ReservationStatus.RESERVED);
//
//        reservationDao.addReservation(r1);
//        reservationDao.addReservation(r2);
//        reservationDao.addReservation(r3);
//
//        var res = reservationDao.fetchAll();
//        assertThat(reservationDao.fetchAll())
//                .usingFieldByFieldElementComparator()
//                .containsExactlyInAnyOrder(r1, r2, r3);
//    }

    private ReservationModel createReservationModel(String ds1, String ds2, String name, String surname, String phone,
    String email, ReservationStatus status) {
        var date1 = java.sql.Date.valueOf(ds1);
        var date2 = java.sql.Date.valueOf(ds2);
        var pm = new PersonModel(name, surname, phone, email);

        return new ReservationModel(date1, date2, pm, status, 1);
    }

    private ReservationDao createFailingDao(Throwable exceptionToBeThrown) {
        try {
            var dataSource = mock(DataSource.class);
            when(dataSource.getConnection()).thenAnswer(i -> ReservationDaoTest.dataSource.getConnection());
            var reservationDao = new ReservationDao(dataSource);
            when(dataSource.getConnection()).thenThrow(exceptionToBeThrown);
            return reservationDao;
        } catch (SQLException ex) {
            throw new RuntimeException("Mock configuration failed", ex);
        }
    }

}

package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomDaoTest {

    private static EmbeddedDataSource dataSource;
    private RoomDao roomDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservations-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createReservationDao() throws SQLException {
        roomDao = new RoomDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.ROOMS");
        }
    }

    @AfterEach
    void cleanUp() {
        roomDao.dropTable();
    }

    @Test
    void addRoom() {
        var room = new RoomModel(11, TypeOfRoom.Taka_Nie_Fajna, 2, true, 206);
        roomDao.addRoom(room);

        assertThat(room.getNumber())
                .isNotNull();
        assertThat(roomDao.fetchAll())
                .usingFieldByFieldElementComparator()
                .containsExactly(room);
    }

    @Test
    void fetchAllEmpty() {
        assertThat(roomDao.fetchAll())
                .isEmpty();
    }

    @Test
    void fetchAll() {
        var r1 = new RoomModel(10, TypeOfRoom.Taka_Fajna, 2, true, 30);
        var r2 = new RoomModel(20, TypeOfRoom.Taka_Nie_Fajna, 2, false, 15);
        var r3 = new RoomModel(30, TypeOfRoom.Taka_Nie_Fajna, 2, false, 10);

        roomDao.addRoom(r1);
        roomDao.addRoom(r2);
        roomDao.addRoom(r3);

        assertThat(roomDao.fetchAll())
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(r1, r2, r3);
    }

    @Test
    void getRowsCount() {
        assertThat(roomDao.getRowsCount()).isEqualTo(0);

        var r1 = new RoomModel(10, TypeOfRoom.Taka_Fajna, 2, true, 30);
        var r2 = new RoomModel(20, TypeOfRoom.Taka_Nie_Fajna, 2, false, 15);
        roomDao.addRoom(r1);
        roomDao.addRoom(r2);

        assertThat(roomDao.getRowsCount()).isEqualTo(2);

        var r3 = new RoomModel(30, TypeOfRoom.Taka_Nie_Fajna, 2, false, 10);
        roomDao.addRoom(r3);

        assertThat(roomDao.getRowsCount()).isEqualTo(3);
    }

    @Test
    void update() {
        var r1 = new RoomModel(10, TypeOfRoom.Taka_Fajna, 2, true, 30);
        var r2 = new RoomModel(20, TypeOfRoom.Taka_Nie_Fajna, 2, false, 15);

        roomDao.addRoom(r1);
        roomDao.addRoom(r2);

        r1.setFree(false);
        r1.setBeds(4);
        r1.setPrice(80);
        r1.setTypeOfRoom(TypeOfRoom.Taka_Nie_Fajna);

        roomDao.update(r1);

        assertThat(findByNumber(r1.getNumber()))
                .isEqualToComparingFieldByField(r1);
        assertThat(findByNumber(r2.getNumber()))
                .isEqualToComparingFieldByField(r2);
    }

    private RoomModel findByNumber(int number) {
        return roomDao.fetchAll().stream()
                .filter(e -> e.getNumber() == number)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No room with number " + number + " found"));
    }
}

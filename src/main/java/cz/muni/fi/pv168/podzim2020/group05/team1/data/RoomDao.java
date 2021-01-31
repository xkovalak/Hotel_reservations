package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private DataSource source;

    RoomDao(DataSource source) {
        this.source = source;
        initTable();
    }

    public List<RoomModel> fetchAll() {
        try (var connection = source.getConnection();
             var st = connection
                     .prepareStatement("SELECT NUMBER, TYPE, BEDS, FREE, PRICE FROM ROOMS")) {

            List<RoomModel> rooms = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    RoomModel room = new RoomModel(
                            rs.getInt("NUMBER"),
                            TypeOfRoom.getEnum(rs.getString("TYPE")),
                            rs.getInt("BEDS"),
                            rs.getBoolean("FREE"),
                            rs.getDouble("PRICE")
                    );
                    rooms.add(room);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return rooms;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException("Failed to load rooms from database");
        }
    }

    private void initTable() {
        if (!tableExits("APP", "ROOMS")) {
            createTable();
            createTestData();
        }
    }

    public void update(RoomModel room) {
        try(var connection = source.getConnection();
            var st = connection.prepareStatement(
                    "UPDATE ROOMS SET TYPE = ?, BEDS = ?, FREE = ?, PRICE = ? " +
                            "WHERE NUMBER = ?")) {
            st.setString(1, room.getTypeOfRoom().toString());
            st.setInt(2, room.getBeds());
            st.setBoolean(3, room.isFree());
            st.setDouble(4, room.getPrice());
            st.setLong(5, room.getNumber());

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update non-existing room: " + room);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update room: " + room);
        }
    }

    public void updateAvailability(boolean isFree, int id) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE ROOMS SET FREE = ? WHERE NUMBER = ?")) {

            st.setBoolean(1, isFree);
            st.setInt(2, id);

            int rowUpdated = st.executeUpdate();
            if (rowUpdated == 0) {
                throw new RuntimeException("Failed to update availability of non-existing room: " + isFree +
                        " " + id);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update availability of room: " + isFree + " " + id);
        }
    }

    private void createTestData() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("INSERT INTO APP.ROOMS (\"NUMBER\", \"TYPE\", \"BEDS\", \"FREE\", \"PRICE\") VALUES " +
                    "(200, 'Taka Fajna', 2, 'true', 15)," +
                    "(201, 'Taka Fajna', 2, 'true', 20)," +
                    "(202, 'Taka Fajna', 2, 'true', 45)," +
                    "(203, 'Taka Nie Fajna', 2, 'true', 12)," +
                    "(303, 'Taka Nie Fajna', 2, 'true', 25)," +
                    "(304, 'Taka Nie Fajna', 2, 'true', 30)");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to populate ROOMS table with test cz.muni.fi.pv168.podzim2020.group05.team1.data", ex);
        }
    }


    private boolean tableExits(String schemaName, String tableName) {
        try (var connection = source.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to detect if the table " + schemaName + "." + tableName + " exist");
        }
    }

    private void createTable() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE APP.ROOMS (" +
                    "NUMBER INTEGER PRIMARY KEY," +
                    "\"TYPE\" VARCHAR(50) NOT NULL," +
                    "\"BEDS\" INTEGER NOT NULL," +
                    "\"FREE\" BOOLEAN NOT NULL," +
                    "\"PRICE\" DOUBLE NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Unable to create Room Table");
        }
    }

    public int getRowsCount() {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "SELECT COUNT(*) FROM ROOMS")) {

            var rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to return rows count of ROOMS table.");
        }
    }

    public void addRoom(RoomModel room) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO ROOMS (\"NUMBER\", \"TYPE\", \"BEDS\", \"FREE\", \"PRICE\") VALUES (?, ?, ?, ?, ?)")) {
            st.setInt(1, room.getNumber());
            st.setString(2, room.getTypeOfRoom().toString());
            st.setInt(3, room.getBeds());
            st.setBoolean(4, room.isFree());
            st.setDouble(5, room.getPrice());

            st.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add room into ROOMS table: " + room);
        }

    }

    public void dropTable() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.ROOMS");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to drop ROOMS table", ex);
        }
    }
}

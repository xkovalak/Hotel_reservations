package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeopleDao {
    private DataSource source;

    PeopleDao(DataSource source) {
        this.source = source;
        initTable();
    }

    public List<PersonModel> fetchAll() {
        try (var connection = source.getConnection();
             var st = connection
                     .prepareStatement("SELECT ID, NAME, SURNAME, IDDOC," +
                             " PHONE_NUMBER, EMAIL, ROOM, RESERVATION_ID FROM PEOPLE")) {

            List<PersonModel> people = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    PersonModel person = new PersonModel(
                            rs.getString("NAME"),
                            rs.getString("SURNAME"),
                            rs.getString("IDDOC"),
                            rs.getString("PHONE_NUMBER"),
                            rs.getString("EMAIL")
                    );
                    person.setId(rs.getLong("ID"));
                    person.setRoom(rs.getString("ROOM"));
                    person.setReservation_id(rs.getLong("RESERVATION_ID"));
                    people.add(person);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return people;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load people from database");
        }
    }

    public void addPerson(PersonModel person) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO PEOPLE (NAME, SURNAME, IDDOC, PHONE_NUMBER, EMAIL, ROOM, RESERVATION_ID) " +
                             " VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, person.getName());
            st.setString(2, person.getSurname());
            st.setString(3, person.getIdDoc());
            st.setString(4, person.getPhoneNumber());
            st.setString(5, person.getEmail());
            st.setString(6, person.getRoom());
            st.setLong(7, person.getReservation_id());
            st.executeUpdate();

            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    person.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException("PeopleDao<addPerson> error: failed to set ID of person: " + person);
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Could not add person into PeopleDao: " + person, ex);
        }
    }

    public void update(PersonModel person) {
        try (var connection = source.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE PEOPLE SET NAME = ?, SURNAME = ?, IDDOC = ?, PHONE_NUMBER = ?, EMAIL = ?, " +
                             "ROOM = ?, RESERVATION_ID = ? WHERE ID = ?")) {
            st.setString(1, person.getName());
            st.setString(2, person.getSurname());
            st.setString(3, person.getIdDoc());
            st.setString(4, person.getPhoneNumber());
            st.setString(5, person.getEmail());
            st.setString(6, person.getRoom());
            st.setLong(7, person.getReservation_id());
            st.setLong(8, person.getId());

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update non-existing person: " + person);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update person: " + person);
        }
    }

    private void initTable() {
        if (!tableExits("APP", "PEOPLE")) {
            createTable();
            createTestData();
        }
    }

    private void createTestData() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("INSERT INTO APP.PEOPLE (\"NAME\", \"SURNAME\", \"IDDOC\", \"PHONE_NUMBER\", " +
                    "\"ROOM\", \"RESERVATION_ID\") VALUES " +
                    "('Joseph', 'Chillhan', 'EK111111', '+420123456789', '-', NULL)," +
                    "('Pitr', 'Hasil', 'EK222222', '+420123456789', '-', NULL)," +
                    "('Pitr', 'Hlineny', 'EK333333', '+420123456789', '-', NULL)");

        } catch (SQLException ex) {
            throw new RuntimeException("Failed to populate PEOPLE table with test cz.muni.fi.pv168.podzim2020.group05.team1.data", ex);
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

            st.executeUpdate("CREATE TABLE APP.PEOPLE (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1)," +
                    "\"NAME\" VARCHAR(50) NOT NULL," +
                    "\"SURNAME\" VARCHAR(50) NOT NULL," +
                    "\"IDDOC\" VARCHAR(30)," +
                    "\"PHONE_NUMBER\" VARCHAR(15) NOT NULL," +
                    "\"EMAIL\" VARCHAR(50)," +
                    "\"ROOM\" VARCHAR(10)," +
                    "\"RESERVATION_ID\" VARCHAR(10)" +
                    ")");
        } catch (SQLException ex) {
            throw new RuntimeException("Unable to create People Table");
        }
    }

    public void dropTable() {
        try (var connection = source.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.PEOPLE");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to drop PEOPLE table", ex);
        }
    }
}

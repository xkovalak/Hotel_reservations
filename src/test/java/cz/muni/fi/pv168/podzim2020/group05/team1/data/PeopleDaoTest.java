package cz.muni.fi.pv168.podzim2020.group05.team1.data;

import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
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

public class PeopleDaoTest {

    private static EmbeddedDataSource dataSource;
    private PeopleDao peopleDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservations-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createReservationDao() throws SQLException {
        peopleDao = new PeopleDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.PEOPLE");
        }
    }

    @AfterEach
    void cleanUp() {
        peopleDao.dropTable();
    }

    @Test
    void addPerson() {
        var person = new PersonModel("Dano", "Drevo", "1234567890", "kako@sem.fuj");
        peopleDao.addPerson(person);

        assertThat(person.getId()).isNotNull();
        assertThat(peopleDao.fetchAll())
                .usingFieldByFieldElementComparator()
                .containsExactly(person);
    }

    @Test
    void addPersonWithException() {
        var sqlException = new SQLException();
        var failingDao = createFailingDao(sqlException);
        var person = new PersonModel("a", "b", "0123456789", "a@a.sk");

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> failingDao.addPerson(person))
                .withMessage("Could not add person into PeopleDao: " + person)
                .withCause(sqlException);
    }

    @Test
    void fetchAllEmpty() {
        assertThat(peopleDao.fetchAll())
                .isEmpty();
    }

    @Test
    void fetchAll() {
        var a = new PersonModel("a", "a", "0123456789", "a@a.sk");
        var b = new PersonModel("bb", "bb", "0123456789", "bb@bb.sk");
        var c = new PersonModel("ccc", "ccc", "0123456789", "ccc@ccc.sk");

        peopleDao.addPerson(a);
        peopleDao.addPerson(b);
        peopleDao.addPerson(c);

        assertThat(peopleDao.fetchAll())
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(a, b, c);
    }

    @Test
    void update() {
        var a = new PersonModel("a", "a", "0123456789", "a@a.sk");
        var b = new PersonModel("bb", "bb", "0123456789", "bb@bb.sk");

        peopleDao.addPerson(a);
        peopleDao.addPerson(b);

        b.setName("cc");
        b.setSurname("majsa");
        b.setRoom("15");
        b.setIdDoc("EK444");
        b.setPhoneNumber("9876543210");
        b.setEmail("zmeneny@email.sk");
        b.setReservation_id(420L);

        peopleDao.update(b);

        assertThat(findById(a.getId()))
                .isEqualToComparingFieldByField(a);
        assertThat(findById(b.getId()))
                .isEqualToComparingFieldByField(b);
    }

    @Test
    void updateNonExisting() {
        var cash = new PersonModel("Johnny", "Cash", "0123456789", "a@a.sk");
        cash.setId(18L);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> peopleDao.update(cash))
                .withMessage("Failed to update non-existing person: " + cash);
    }

    private PeopleDao createFailingDao(Throwable exceptionToBeThrown) {
        try {
            var dataSource = mock(DataSource.class);
            when(dataSource.getConnection()).thenAnswer(i -> PeopleDaoTest.dataSource.getConnection());
            var peopleDao = new PeopleDao(dataSource);
            when(dataSource.getConnection()).thenThrow(exceptionToBeThrown);
            return peopleDao;
        } catch (SQLException ex) {
            throw new RuntimeException("Mock configuration failed", ex);
        }
    }

    private PersonModel findById(long id) {
        return peopleDao.fetchAll().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No person with id " + id + " found"));
    }

}

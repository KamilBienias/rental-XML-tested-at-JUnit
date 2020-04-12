import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        System.out.println("Creates new customer all the time");
        //given
        customer = new Customer("Jan", "Kowalski", "03082475867", new GregorianCalendar(2003, Calendar.AUGUST, 24));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Displays after each test-method");
    }

    @Test
    void getFirstName() {
        //when
        String firstName = customer.getFirstName();

        //then
        assertEquals("Jan", firstName);
    }

    @Test
    void getLastName() {
        //when
        String lastName = customer.getLastName();

        //then
        assertEquals("Kowalski", lastName);
    }

    @Test
    void getPesel() {
        //when
        String pesel = customer.getPesel();

        //then
        assertEquals("03082475867", pesel);
    }

    @Test
    void getDateOfBirth() {
        //when
        GregorianCalendar dateOfBirth = customer.getDateOfBirth();

        //then
        assertEquals(new GregorianCalendar(2003, Calendar.AUGUST, 24), dateOfBirth);
    }
}
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    private List<Customer> borrowers;

    @BeforeEach
    void setUp() {
        System.out.println("Creates new product all the time");
        //given
        borrowers = new ArrayList<>();
        borrowers.add(new Customer("Jan", "Kowalski", "03082475867", new GregorianCalendar(2003, Calendar.AUGUST, 24)));
        borrowers.add(new Customer("Henryk", "Nowak", "87051257698", new GregorianCalendar(1987, Calendar.MAY, 12)));

        product = new Product("rower", 2010, 35, borrowers);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Displays after each test-method");
    }

    @Test
    void getProductName() {
        //when
        String productName = product.getProductName();

        //then
        assertEquals("rower", productName);
    }

    @Test
    void getYearOfProduction() {
        //when
        int yearOfProduction = product.getYearOfProduction();

        //then
        assertEquals(2010, yearOfProduction);
    }

    @Test
    void getPrice() {
        //when
        int price = product.getPrice();

        //then
        assertEquals(35, price);
    }

    @Test
    void getBorrowers() {
        //when
        List<Customer> borrowers = product.getBorrowers();

        //then
        assertEquals(new Customer("Jan", "Kowalski", "03082475867", new GregorianCalendar(2003, Calendar.AUGUST, 24)), borrowers.get(0));
        assertEquals(new Customer("Henryk", "Nowak", "87051257698", new GregorianCalendar(1987, Calendar.MAY, 12)), borrowers.get(1));
    }
}
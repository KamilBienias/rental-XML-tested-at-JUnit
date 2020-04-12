import java.util.GregorianCalendar;
import java.util.Objects;

public class Customer {

    private String firstName;
    private String lastName;
    private Long pesel;
    private GregorianCalendar dateOfBirth;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public GregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(GregorianCalendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        String date = "not set";
        if (dateOfBirth!=null) {
            date = dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH) + "." +
                    (dateOfBirth.get(GregorianCalendar.MONTH) + 1) + "." + dateOfBirth.get(GregorianCalendar.YEAR);
        }
        return "Customer{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", dateOfBirth=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                dateOfBirth.equals(customer.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth);
    }
}

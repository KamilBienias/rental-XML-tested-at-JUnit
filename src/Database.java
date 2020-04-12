import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class Database {

    private Set<Customer> customers;
    private List<Product> products;

    public Database() {
        customers = new HashSet<>();
        products = new ArrayList<>();
    }

    @XmlElement
    public Set<Customer> getCustomers(){
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @XmlElement
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> movies) {
        this.products = movies;
    }

    @Override
    public String toString() {
        return "Database{" +
                "customers=" + customers +
                ", products=" + products +
                '}';
    }

}
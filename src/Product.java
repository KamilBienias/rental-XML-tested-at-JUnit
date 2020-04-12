import java.util.*;

public class Product {

    private String productName;
    private int yearOfProduction;
    private int price;
    private List<Customer> borrowers = new ArrayList<>();

    public Product() {
    }

    public Product(String productName, int yearOfProduction, int price, List<Customer> borrowers) {
        this.productName = productName;
        this.yearOfProduction = yearOfProduction;
        this.price = price;
        this.borrowers = borrowers;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Customer> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Customer> borrowers) {
        this.borrowers = borrowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return yearOfProduction == product.yearOfProduction &&
                price == product.price &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(borrowers, product.borrowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, yearOfProduction, price, borrowers);
    }

    @Override
    public String toString() {
        return "Product{" +
                " productName='" + productName + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                ", price=" + price +
                ", borrowers=" + borrowers +
                '}';
    }
}


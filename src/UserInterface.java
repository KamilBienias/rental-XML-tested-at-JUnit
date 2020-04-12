import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {

    private Database database;//polem obiektu UI jest obiekt z bazy danych

    private UserInterface(Database database) {
        this.database = database;
    }

    private void menu(){
        boolean quit = false;
        while(!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add a new customer");
            System.out.println("2. Add a new product to rental");
            System.out.println("3. Rent a product as a customer");
            System.out.println("4. Display rented products");
            System.out.println("5. Display all customers by last name");
            System.out.println("6. Display all products by product name");
            System.out.println("7. Remove a customer from the database");
            System.out.println("8. Remove a product from the database");
            System.out.println("9. Display products by price (asc and desc)");
            System.out.println("0. Quit");

            System.out.print("Please, enter the command number: ");
            Scanner scanner = new Scanner(System.in);
            boolean isThereError = true;//flaga, ktora mowi ze jest blad
            int commandNumber = -1;
            while (isThereError) {// dopoki jest blad to petla sie wykonuje
                try {
                    commandNumber = scanner.nextInt();//tu sprawdza czy liczba w ogole jest calkowita, a jesli nie to rzuca InputMismatchException
                    if (commandNumber > 9 || commandNumber < 0) {// tu spr czy liczba calkowita jest z menu, a jesli nie to rzuca IllegalArgumentException
                        throw new IllegalArgumentException();
                    }
                    isThereError = false;// gdy nie ma wyjatku, to opuszcza blok try-catch
                } catch (InputMismatchException letter) {
                    System.out.print("It is not an integer. Please, enter the command number: ");
                    scanner.nextLine();
                } catch (IllegalArgumentException outOfMenu) {
                    System.out.printf("Number %d is out of menu. ", commandNumber);
                    System.out.print("Please, enter the command number: ");
                    scanner.nextLine();
                }
            }

            switch (commandNumber) {
                case 1:
                    System.out.println("\nYou have chosen to add a new customer");
                    addCustomer();
                    break;

                case 2:
                    System.out.println("\nYou have chosen to add a new product to rental");
                    addProductToRental();
                    break;

                case 3:
                    System.out.println("\nYou have chosen to rent a product as a customer");
                    orderProduct();
                    break;

                case 4:
                    System.out.println("\nYou have chosen to display rented products");
                    displayRentedProducts();
                    break;

                case 5:
                    System.out.println("\nYou have chosen to display all customers");
                    displayCustomersByLastName();
                    break;

                case 6:
                    System.out.println("\nYou have chosen to display all products");
                    displayProductsByProductName();
                    break;

                case 7:
                    System.out.println("\nYou have chosen to remove a customer from the database");
                    removeCustomerFromDatabase();
                    break;

                case 8:
                    System.out.println("\nYou have chosen to remove a product from the database");
                    removeProductFromDatabase();
                    break;

                case 9:
                    System.out.println("\nYou have chosen to display products by price (asc and desc)");
                    displayProductsByPrice();
                    break;

                case 0:
                    System.out.println("\nYou have chosen to quit");
                    quit = true;
            }
        }
    }

    //wybieram klienta z HashSet - wewnatrz wywoluje displayCustomersByLastName()
    private Customer selectCustomer() {

        Scanner scanner = new Scanner(System.in);

        for (Customer customer: database.getCustomers()) {
            System.out.println(customer);
        }

        System.out.print("Select a pesel of customer: ");

        String pesel = "";
        boolean isPeselError = true;
        while (isPeselError) {
            try {
                pesel = scanner.next();
                Long.valueOf(pesel);//spr czy pesel da się zrzutować na liczbę long

                if(pesel.length() != 11){
                    throw new IllegalArgumentException();
                }
                isPeselError = false;
            } catch (InputMismatchException letter) {
                System.out.print("Pesel should have only digits. Pesel: ");
                scanner.nextLine();
            } catch (IllegalArgumentException wrongPesel){
                System.out.printf("Pesel %s has not 11 digits. ", pesel);
                System.out.print("Please, enter pesel: ");
                scanner.nextLine();
            }
        }

        Customer selectedCustomer = new Customer();
        for (Customer customer : database.getCustomers()) {
            if (customer.getPesel().equals(pesel)) {
                System.out.println("You have selected customer:\n" + customer);
                selectedCustomer = customer;
            }
        }
        return selectedCustomer;
    }

    //wybieram produkt z ArrayList - wewnatrz wywoluje displayProductsByProductName()
    private Product selectProduct() {

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < database.getProducts().size(); i++){
            System.out.println(i+1 + ". " + database.getProducts().get(i));
        }

        System.out.print("Select the product number (integer): ");

        boolean isNumberProductError = true;//flaga, ktora mowi ze jest blad
        int numberProduct = -1;
        while (isNumberProductError) {// dopoki jest blad to petla sie wykonuje
            try {
                numberProduct = scanner.nextInt();//tu sprawdza czy liczba w ogole jest calkowita, a jesli nie to rzuca InputMismatchException
                if (numberProduct > database.getProducts().size() || numberProduct < 1) {// tu spr czy liczba calkowita jest ze zbioro klientow, a jesli nie to rzuca IllegalArgumentException
                    throw new IllegalArgumentException();
                }
                isNumberProductError = false;// gdy nie ma wyjatku, to opuszcza blok try-catch
            } catch (InputMismatchException letter) {
                System.out.print("It is not an integer. Please, enter the number: ");
                scanner.nextLine();
            } catch (IllegalArgumentException outOfList) {
                System.out.printf("Number %d goes beyonds the number of products. ", numberProduct);
                System.out.print("Please, enter the number of product: ");
                scanner.nextLine();
            }
        }
        Product selectedProduct = database.getProducts().get(numberProduct - 1);//odjac 1, bo element o id 1 ma index 1-1=0, element o id 2 ma index 2-1=1, itd

        System.out.println("You have selected product:\n" + selectedProduct);

        return selectedProduct;
    }

    // 1.
    private void addCustomer(){
        Scanner scanner = new Scanner(System.in);

        Customer newCustomer = new Customer();

        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        newCustomer.setFirstName(firstName);

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        newCustomer.setLastName(lastName);

        System.out.print("Pesel (11 digits): ");
        String pesel = "";
        boolean isPeselError = true;
        while (isPeselError) {
            try {
                pesel = scanner.next();
                Long.valueOf(pesel);//spr czy pesel da się zrzutować na liczbę long

                if(pesel.length() != 11){
                    throw new IllegalArgumentException();
                }
                isPeselError = false;
            } catch (InputMismatchException letter) {
                System.out.print("Pesel should have only digits. Pesel: ");
                scanner.nextLine();
            } catch (IllegalArgumentException wrongPesel){
                System.out.printf("Pesel %s has not 11 digits. ", pesel);
                System.out.print("Please, enter pesel: ");
                scanner.nextLine();
            }
        }
        newCustomer.setPesel(pesel);

        GregorianCalendar today = new GregorianCalendar();// dzisiejsza data
        int thatYear = today.get(GregorianCalendar.YEAR);// pobieram rok z dzisiejszej daty

        System.out.print("The year of birth: ");
        int year = 0;
        boolean isYearError = true;
        while (isYearError) {
            try {
                year = scanner.nextInt();
                if(year < 1900 || year > thatYear){
                    throw new IllegalArgumentException();
                }
                isYearError = false;
            } catch (InputMismatchException letter) {
                System.out.print("It is not a year. The year of birth: ");
                scanner.nextLine();
            } catch (IllegalArgumentException wrongYear){
                System.out.printf("Year of birth %d is impossible. ", year);
                System.out.print("Please, enter the year of birth: ");
                scanner.nextLine();
            }
        }

        System.out.print("The month of birth: ");
        boolean isMonthError = true;
        int month = 0;
        while (isMonthError){
            try{
                month = scanner.nextInt();
                if(month < 1 || month > 12){
                    throw new IllegalArgumentException();
                }
                isMonthError = false;
            }catch (InputMismatchException letter){
                System.out.print("It is not a month. The month of birth: ");
                scanner.nextLine();
            }catch (IllegalArgumentException wrongMonth){
                System.out.printf("Month of birth %d is impossible. ", month);
                System.out.print("Please, enter the month of birth: ");
                scanner.nextLine();
            }
        }

        YearMonth date = YearMonth.of(year, month);// nowy obiekt, ktory jest data zlozona z roku i numeru miesiaca
        int daysInThatMonth = date.lengthOfMonth();// biore dlugosc miesiaca z tej daty

        System.out.print("The day of birth: ");
        boolean isDayError = true;
        int day = 0;
        while (isDayError){
            try{
                day = scanner.nextInt();
                if(day < 1 || day > daysInThatMonth){
                    throw new IllegalArgumentException();
                }
                isDayError = false;
            }catch (InputMismatchException letter){
                System.out.print("It is not a day. The day of birth: ");
                scanner.nextLine();
            }catch (IllegalArgumentException wrongDay) {
                System.out.printf("Day of birth %d is impossible. ", day);
                System.out.print("Please, enter the day of birth: ");
                scanner.nextLine();
            }
        }

        //ustawiam date urodzenia dla klienta
        newCustomer.setDateOfBirth(new GregorianCalendar(year, month - 1, day));

        //dodaje (jesli go nie ma) nowego klienta do bazy danych
        if(!database.getCustomers().contains(newCustomer)) {
            database.getCustomers().add(newCustomer);
            System.out.println("Customer added:\n" + newCustomer);
        } else {
            System.out.println("This customer already exists in the database so it can not be added");
            addCustomer();
        }
    }

    // 2.
    private void addProductToRental() {

        Product newProduct = new Product();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Product name: ");
        String productName = scanner.nextLine();
        newProduct.setProductName(productName);

        System.out.print("Year of production: ");
        int yearOfProduction = scanner.nextInt();
        newProduct.setYearOfProduction(yearOfProduction);

        System.out.print("Price: ");
        int price = scanner.nextInt();
        newProduct.setPrice(price);

        //dodaje nowy produkt do bazy danych
        database.getProducts().add(newProduct);

        System.out.println("Product added:\n " + newProduct);
    }

    // 3.
    private void orderProduct() {

        Customer selectedCustomer = selectCustomer();

        Product selectedProduct = selectProduct();

        //nie moge przypisac wybranego produktu do wybranego klienta, bo sie zapetla w xml
        //selectedCustomer.getBorrowedProducts().add(selectedProduct);

        selectedProduct.getBorrowers().add(selectedCustomer);//przypisuje klienta do produktu

        System.out.println("Customer:\n" + selectedCustomer + "\nhas borrowed a product:\n" + selectedProduct);
    }


    // 4.
    private void displayRentedProducts() {

        System.out.println("List of rented products:");

        List<Product> rentedProducts = new ArrayList<>();

        rentedProducts = database.getProducts().stream()
                .filter(product -> product.getBorrowers().size() > 0)// niewypozyczone size() == 0
                .collect(Collectors.toList());

        rentedProducts.forEach(product -> System.out.println(product));
    }

    // 5.
    private void displayCustomersByLastName() {
        System.out.println("List of customers by last name:");
        database.getCustomers().stream()
                .sorted(Comparator.comparing(customer -> customer.getLastName()))
                .forEach(customer -> System.out.println(customer));
    }

    // 6.
    private void displayProductsByProductName(){
        System.out.println("\nList of products by product name:");
        List<Product> productsByName = new ArrayList<>();
        productsByName = database.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.getProductName()))
                .collect(Collectors.toList());

        productsByName.stream()
                .forEach(product -> System.out.println(product));
    }

    // 7.
    private void removeCustomerFromDatabase() {

        Customer selectedCustomer = selectCustomer();

        database.getCustomers().remove(selectedCustomer);

        System.out.println("Customer\n " + selectedCustomer + "\nhas been removed from the database");
    }

    // 8.
    private void removeProductFromDatabase() {

        Product selectedProduct = selectProduct();

        System.out.println("Product\n " + selectedProduct + "\nhas been removed from the database");

        database.getProducts().remove(selectedProduct);
    }

    // 9.
    private void displayProductsByPrice() {

        List<Product> productsByPriceAscending = new ArrayList<>();
        productsByPriceAscending = database.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.getPrice()))//domyslnie sortuje rosnaco po cenie
                .collect(Collectors.toList());

        System.out.println("\nAscending:");
        productsByPriceAscending.forEach(product -> System.out.println(product));//wyswietla rosnaco po cenie

        System.out.println("\nDescending:");
        Collections.reverse(productsByPriceAscending);
        productsByPriceAscending.forEach(product -> System.out.println(product));//wyswietla malejaco po cenie

    }

    public static void main(String[] args) throws JAXBException {

        Database database = new Database();

        try {
            Unmarshaller unmarshaller = UnmarshallerExample.generateUnmarshaller();

            database = (Database) unmarshaller.unmarshal(new File("database.xml"));
        } catch (JAXBException e) {
            System.out.println("\nCreates new database");
        }

        UserInterface UI = new UserInterface(database);

        UI.menu();

        Marshaller marshaller = MarshallerExample.generateMarshaller();

        marshaller.marshal(database, new File("database.xml"));

        System.out.println("Saved to database.xml");

        /////////////////////////////////////
        //zapis do pliku customers.txt
        // proba otwarcia pliku do zapisywania. Jesli nie istnieje, to go utworzy,
        // a jesli istnieje to go nadpisze. Chyba, ze plik bedzie tylko do odczytu,
        // to rzuci wyjatek NullPointerException ze nie mozna nadpisac
        try(FileWriter fileWriter = new FileWriter("customers.txt")) {

            for (Customer customer : database.getCustomers()) {
                fileWriter.write(customer.getFirstName() + " ");
                fileWriter.write(customer.getLastName() + " ");
                fileWriter.write(customer.getDateOfBirth() + " ");
                fileWriter.write(customer.getPesel() + "\n");
            }
            System.out.println("Saved to customers.txt");

        }catch (IOException ex){
            System.out.println("File customers.txt access problem");// to sie wyswietli gdy plik bedzie tylko do odczytu
        }

        //zapis do pliku products.txt
        try(FileWriter fileWriter = new FileWriter("products.txt")) {

            for (Product product : database.getProducts()) {
                fileWriter.write(product.getProductName() + " ");
                fileWriter.write(product.getYearOfProduction() + " ");
                fileWriter.write(product.getPrice() + " ");
                fileWriter.write(product.getBorrowers() + "\n");
            }
            System.out.println("Saved to products.txt");

        }catch (IOException ex){
            System.out.println("File products.txt access problem");// to sie wyswietli gdy plik bedzie tylko do odczytu
        }
    }
}

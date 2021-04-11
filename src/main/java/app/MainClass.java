package app;

import db.AddressRepository;
import db.CustomerRepository;
import model.Address;
import model.Customer;

public class MainClass {
    public static void main(String[] args) {

        try (CustomerRepository repository = CustomerRepository.getINSTANCE()) {
            Customer c = new Customer("niklas.haiden@gmail.com", "Niklas", "Haiden");
            System.out.println("persist" + repository.persistCustomer(c));
            System.out.println("getById" + repository.getCustomerById(1));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Address a = null;
        try (AddressRepository repository = AddressRepository.getINSTANCE()){
            a = new Address(99999, "Vienna", "Sesame Street");
            System.out.println("persist" + repository.persistAddress(a));
            System.out.println("getById" + repository.getAddressById(1));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.ecommerce.admin.user;

import com.ecommerce.admin.customer.CustomerRepository;
import com.ecommerce.common.entity.Country;
import com.ecommerce.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer1() {
        Integer countryId= 234; // USA
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();

        customer.setCountry (country);
        customer.setFirstName("David");
        customer.setLastName("Fountain");
        customer.setPassword("password123");
        customer.setEmail("david.s.fountaine@gmail.com");
        customer.setPhoneNumber("312-462-7518");
        customer.setAddressLine1("1927 West Drive");
        customer.setCity ("Sacramento");
        customer.setState("California");
        customer.setPostalCode ("95867");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateCustomer2() {
        Integer countryId= 106; // India
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();

        customer.setCountry (country);
        customer.setFirstName("Sanya");
        customer.setLastName("Lad");
        customer.setPassword("password456");
        customer.setEmail("david.lad2020@gmail.com");
        customer.setPhoneNumber("02224928502");
        customer.setAddressLine1("173, A-, Shah & Nahar Indl.estate, Sunmil Road");
        customer.setAddressLine2("Dhanranji Mill Compound, Lower Parel (west)");
        customer.setCity ("Mumbai");
        customer.setState("Maharashtra");
        customer.setPostalCode ("400013");
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);
    }

    @Test
    public void testListCustomers(){
        Iterable<Customer> customers = repo.findAll();

        customers.forEach(System.out::println);

        assertThat(customers).hasSizeGreaterThan(1);
    }

    @Test
    public void testUpdateCustomer(){
        Integer customerId = 1;
        String lastName = "Stanfield";

        Customer customer = repo.findById(customerId).get();
        customer.setLastName(lastName);
        customer.setEnabled(true);

        Customer updatedCustomer = repo.save(customer);

        assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testGetCustomer(){
        Integer customerId = 2;
        Optional<Customer> optionalCustomer = repo.findById(customerId);

        assertThat(optionalCustomer).isPresent();

        Customer customer = optionalCustomer.get();
        System.out.println(customer);
    }

    @Test
    public void testDeleteCustomer(){
        Integer customerId = 2;
        repo.deleteById(customerId);

        Optional<Customer> optionalCustomer = repo.findById(customerId);
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    public void testFindByEmail(){
        String email = "david.s.fountaine@gmail.com";
        Customer customer = repo.findByEmail(email);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testFindByVerificationCode(){
        String code = "code_123";
        Customer customer = repo.findByVerificationCode(code);

        assertThat(customer).isNotNull();
        System.out.println(customer);
    }

    @Test
    public void testEnabledCustomer(){
        Integer customerId = 1;
        repo.enable(customerId);

        Customer customer = repo.findById(customerId).get();
        assertThat(customer.isEnabled()).isTrue();
    }
}

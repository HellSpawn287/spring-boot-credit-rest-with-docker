package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.mapper.CustomerMapper;
import hellspawn287.springbootcreditrestwithdocker.api.model.CustomerDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Customer;
import hellspawn287.springbootcreditrestwithdocker.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceTest2 {

    @Autowired
    CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() throws ResourceNotFoundException {
        System.out.println("Loading Customer Data...");
        System.out.println(customerRepository.findAll().size());

        //load data...
        Customer jim = new Customer();
        jim.setFirstname("John");
        jim.setLastname("Jameson");

        Customer jack = new Customer();
        jack.setFirstname("Jack");
        jack.setLastname("Nicholson");

        Customer peter = new Customer();
        peter.setFirstname("Peter");
        peter.setLastname("Parker");

        customerRepository.save(jim);
        customerRepository.save(jack);
        customerRepository.save(peter);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws ResourceNotFoundException {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
    }

    @Test
    public void patchCustomerUpdateLastName() throws ResourceNotFoundException {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first name/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }

}
package hellspawn287.springbootcreditrestwithdocker.api.mapper;

import hellspawn287.springbootcreditrestwithdocker.api.model.CustomerDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {
    private static final String f_NAME = "Peter";
    private static final String l_NAME = "Parker";
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
//      given
        Customer customer = new Customer();
        customer.setFirstname(f_NAME);
        customer.setLastname(l_NAME);
//      when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

//      then
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    public void customerDTOToCustomer() {
//        given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(f_NAME);
        customerDTO.setLastname(l_NAME);

//        when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

//        then
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }
}
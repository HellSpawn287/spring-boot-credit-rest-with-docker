package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.mapper.CustomerMapper;
import hellspawn287.springbootcreditrestwithdocker.api.model.CustomerDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Customer;
import hellspawn287.springbootcreditrestwithdocker.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
    private static final Long CUSTOMER_ID = 2L;
    private static final String f_NAME = "Peter";
    private static final String l_NAME = "Parker";

    @Mock
    CustomerRepository customerRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private CustomerServiceImpl customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl();
        customerService.setCustomerMapper(customerMapper);
        customerService.setCustomerRepository(customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setFirstname(f_NAME);
        customer.setLastname(l_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(CUSTOMER_ID);

        //then
        assertEquals(f_NAME, customerDTO.getFirstname());
        assertEquals(l_NAME, customerDTO.getLastname());
    }

    @Test
    public void CreateNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals("/api/customers" + "/1", savedDTO.getCustomerURL());
    }

    @Test
    public void saveCustomerByDTO() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(f_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals("/api/customers" + "/1", savedDTO.getCustomerURL());
    }

    @Test
    public void deleteCustomerById() throws Exception {
        Long id = 1L;

        customerService.deleteCustomerById(id);
        verify(customerRepository, Mockito.times(1)).deleteById(anyLong());
    }
}
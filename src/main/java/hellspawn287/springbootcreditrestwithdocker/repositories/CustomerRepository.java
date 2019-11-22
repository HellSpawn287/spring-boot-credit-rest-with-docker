package hellspawn287.springbootcreditrestwithdocker.repositories;

import hellspawn287.springbootcreditrestwithdocker.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    Customer getOne(Long aLong);
}
package hellspawn287.springbootcreditrestwithdocker.repositories;

import hellspawn287.springbootcreditrestwithdocker.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);
}

package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.mapper.ProductMapper;
import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Product;
import hellspawn287.springbootcreditrestwithdocker.repositories.ProductRepository;
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
public class ProductServiceTest2 {

    @Autowired
    ProductRepository productRepository;

    private ProductService productService;

    @Before
    public void setUp() throws ResourceNotFoundException {
        System.out.println("Loading Product Data...");
        System.out.println(productRepository.findAll().size());

        //load data...
        Product product_1 = new Product();
        product_1.setName("Product 1");
        product_1.setValue(11L);

        Product product_2 = new Product();
        product_2.setName("Product 2");
        product_2.setValue(222L);

        Product product_3 = new Product();
        product_3.setName("Product 3");
        product_3.setValue(3L);

        Product product_4 = new Product();
        product_4.setName("Product 4");
        product_4.setValue(40L);

        Product product_5 = new Product();
        product_5.setName("Product 5");
        product_5.setValue(50L);

        productRepository.save(product_1);
        productRepository.save(product_2);
        productRepository.save(product_3);
        productRepository.save(product_4);
        productRepository.save(product_5);

        productService = new ProductServiceImpl(ProductMapper.INSTANCE, productRepository);
    }

    @Test
    public void patchProductUpdateName() throws ResourceNotFoundException {
        String updatedName = "UpdatedName";
        long id = getProductIdValue();

        Product originalProduct = productRepository.getOne(id);
        assertNotNull(originalProduct);

        //save original name
        String originalName = originalProduct.getName();
        Long originalValue = originalProduct.getValue();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(updatedName);

        productService.patchProduct(id, productDTO);

        Product updatedProduct = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedProduct);
        assertEquals(updatedName, updatedProduct.getName());
        assertThat(originalName, not(equalTo(updatedProduct.getName())));
        assertThat(originalValue, equalTo(updatedProduct.getValue()));
    }

    @Test
    public void patchProductUpdateValue() throws ResourceNotFoundException {
        Long updatedValue = 333L;
        long id = getProductIdValue();

        Product originalProduct = productRepository.getOne(id);
        assertNotNull(originalProduct);

        //save original name/value
        String originalProductName = originalProduct.getName();
        Long originalProductValue = originalProduct.getValue();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setValue(updatedValue);

        productService.patchProduct(id, productDTO);

        Product updatedProduct = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedProduct);
        assertEquals(updatedValue, updatedProduct.getValue());
        assertThat(originalProductName, equalTo(updatedProduct.getName()));
        assertThat(originalProductValue, not(equalTo(updatedProduct.getValue())));
    }

    private Long getProductIdValue() {
        List<Product> products = productRepository.findAll();
        System.out.println("Products found: " + products.size());

        //return first id
        return products.get(0).getId();
    }
}
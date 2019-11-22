package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.mapper.ProductMapper;
import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Product;
import hellspawn287.springbootcreditrestwithdocker.repositories.ProductRepository;
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

public class ProductServiceImplTest {
    private static final Long CUSTOMER_ID = 2L;
    private static final String NAME = "Peter's Toolbox";
    private static final Long VALUE = 30L;

    @Mock
    ProductRepository productRepository;
    private ProductMapper productMapper = ProductMapper.INSTANCE;
    private ProductServiceImpl productService;

    @Before
    public void setUp() throws ResourceNotFoundException {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl();
        productService.setProductMapper(productMapper);
        productService.setProductRepository(productRepository);
    }

    @Test
    public void getAllProducts() {
        //given
        List<Product> products = Arrays.asList(new Product(), new Product(), new Product());

        when(productRepository.findAll()).thenReturn(products);
        //when
        List<ProductDTO> productDTOS = productService.getAllProducts();

        //then
        assertEquals(3, productDTOS.size());
    }

    @Test
    public void getProductById() {
        //given
        Product product = getProductWithID_Name_Value();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        //when
        ProductDTO productDTO = productService.getProductById(CUSTOMER_ID);

        //then
        assertEquals(NAME, productDTO.getName());
        assertEquals(VALUE, productDTO.getValue());
    }

    @Test
    public void CreateNewProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 303");
        productDTO.setValue(30L);

        Product savedProduct = getProductWithNameAndValueFromProductDTO(productDTO);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //when
        ProductDTO savedDTO = productService.createNewProduct(productDTO);

        //then
        assertEquals(productDTO.getName(), savedDTO.getName());
        assertEquals(productDTO.getValue(), savedDTO.getValue());
        assertEquals("/api/products" + "/1", savedDTO.getProductURL());
    }

    @Test
    public void saveProductByDTO() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME);

        Product savedProduct = getProductWithNameAndValueFromProductDTO(productDTO);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        ProductDTO savedDTO = productService.saveProductByDTO(1L, productDTO);

        // then
        assertEquals(productDTO.getName(), savedDTO.getName());
        assertEquals(productDTO.getValue(), savedDTO.getValue());
        assertEquals("/api/products" + "/1", savedDTO.getProductURL());
    }

    @Test
    public void deleteProductById() throws Exception {
        Long id = 1L;

        productService.deleteProductById(id);
        verify(productRepository, Mockito.times(1)).deleteById(anyLong());
    }

    private Product getProductWithID_Name_Value() {
        Product product = new Product();
        product.setId(CUSTOMER_ID);
        product.setName(NAME);
        product.setValue(VALUE);
        return product;
    }

    private Product getProductWithNameAndValueFromProductDTO(ProductDTO productDTO) {
        Product savedProduct = new Product();
        savedProduct.setName(productDTO.getName());
        savedProduct.setValue(productDTO.getValue());
        savedProduct.setId(1L);
        return savedProduct;
    }
}
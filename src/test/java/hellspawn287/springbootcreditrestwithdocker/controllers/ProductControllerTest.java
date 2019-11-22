package hellspawn287.springbootcreditrestwithdocker.controllers;

import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.service.ProductService;
import hellspawn287.springbootcreditrestwithdocker.service.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest extends AbstractRestControllerTest {
    private static final String NAME = "Peter";
    private static final Long VALUE = 44L;

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllProducts() throws Exception {
        //given
        ProductDTO product1 = new ProductDTO();
        product1.setName(NAME);
        product1.setValue(VALUE);
        product1.setProductURL("/api/products/1");

        ProductDTO product2 = new ProductDTO();
        product2.setName(NAME);
        product2.setValue(VALUE);
        product2.setProductURL("/api/products/2");

        //when
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        //then
        mockMvc.perform(
                get("/api/products/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    public void getProductByID() throws Exception {
        //given
        ProductDTO product1 = new ProductDTO();
        product1.setName(NAME);
        product1.setValue(VALUE);
        product1.setProductURL("/api/products/1");

        //when
        when(productService.getProductById(anyLong())).thenReturn(product1);

        mockMvc.perform(
                get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.value",
                        equalTo(Integer.valueOf(String.valueOf(VALUE)))))//TODO: Check
                .andExpect(jsonPath("$.product_url", equalTo("/api/products/1")));
    }

    @Test
    public void createNewProduct() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Fred");
        productDTO.setValue(666L);

        ProductDTO returnDTO = new ProductDTO();
        returnDTO.setName(productDTO.getName());
        returnDTO.setValue(productDTO.getValue());
        returnDTO.setProductURL("/api/products/1");

        when(productService.createNewProduct(productDTO)).thenReturn(returnDTO);

        // then
        mockMvc.perform(post(ProductController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Fred")))
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME);
        productDTO.setValue(VALUE);

        ProductDTO returnDTO = new ProductDTO();
        returnDTO.setName(productDTO.getName());
        returnDTO.setValue(productDTO.getValue());
        returnDTO.setProductURL(ProductController.BASE_URL + "/1");

        when(productService.saveProductByDTO(anyLong(), any(ProductDTO.class))).thenReturn(returnDTO);
        // when/then

        mockMvc.perform(
                put(ProductController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.value",
                        equalTo(Integer.valueOf(String.valueOf(VALUE))))) //TODO: Check
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME);

        ProductDTO returnDTO = new ProductDTO();
        returnDTO.setName(productDTO.getName());
        returnDTO.setValue(VALUE);
        returnDTO.setProductURL(ProductController.BASE_URL + "/1");

        when(productService.patchProduct(anyLong(), any(ProductDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(ProductController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.value",
                        equalTo(Integer.valueOf(String.valueOf(VALUE)))))//TODO: Check
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        mockMvc.perform(delete(ProductController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).deleteProductById(anyLong());
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(productService.getProductById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(ProductController.BASE_URL + "/99998")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
package hellspawn287.springbootcreditrestwithdocker.controllers;

import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.api.model.ProductListDTO;
import hellspawn287.springbootcreditrestwithdocker.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("My products")
@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {
    public static final String BASE_URL = "/api/products";
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Lists all the products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return new ProductListDTO(
                productService.getAllProducts());
    }

    @ApiOperation(value = "Get the product by ID")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductByID(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Create a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @ApiOperation(value = "Replace a product by new data")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.saveProductByDTO(id, productDTO);
    }

    @ApiOperation(value = "Update a product")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @ApiOperation(value = "Delete a product")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
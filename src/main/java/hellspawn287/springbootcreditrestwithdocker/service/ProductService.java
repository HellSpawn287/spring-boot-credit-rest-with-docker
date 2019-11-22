package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createNewProduct(ProductDTO productDTO);

    ProductDTO saveProductByDTO(Long id, ProductDTO productDTO);

    ProductDTO patchProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException;

    void deleteProductById(Long id);
}
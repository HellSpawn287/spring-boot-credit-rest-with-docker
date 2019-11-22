package hellspawn287.springbootcreditrestwithdocker.service;

import hellspawn287.springbootcreditrestwithdocker.api.mapper.ProductMapper;
import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.controllers.ProductController;
import hellspawn287.springbootcreditrestwithdocker.domain.Product;
import hellspawn287.springbootcreditrestwithdocker.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    productDTO.setProductURL(getProductUrl(product.getId()));
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        return saveAndReturnDTO(productMapper.productDTOToProduct(productDTO));
    }

    private ProductDTO saveAndReturnDTO(Product product) {
        Product savedProduct = productRepository.save(product);
        ProductDTO returnDTO = productMapper.productToProductDTO(savedProduct);
        returnDTO.setProductURL(getProductUrl(savedProduct.getId()));

        return returnDTO;
    }

    @Override
    public ProductDTO saveProductByDTO(Long id, ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setId(id);

        return saveAndReturnDTO(product);
    }

    @Override
    public ProductDTO patchProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException {
        return productRepository.findById(id).map(product -> {

            if (productDTO.getName() != null) {
                product.setName(productDTO.getName());
            }
            if (productDTO.getValue() != null) {
                product.setValue(productDTO.getValue());
            }
            ProductDTO returnDTO = productMapper.productToProductDTO(productRepository.save(product));
            returnDTO.setProductURL(getProductUrl(id));

            return returnDTO;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private String getProductUrl(Long id) {
        return ProductController.BASE_URL + "/" + id;
    }
}
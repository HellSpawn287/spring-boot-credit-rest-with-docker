package hellspawn287.springbootcreditrestwithdocker.api.mapper;

import hellspawn287.springbootcreditrestwithdocker.api.model.ProductDTO;
import hellspawn287.springbootcreditrestwithdocker.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);
}

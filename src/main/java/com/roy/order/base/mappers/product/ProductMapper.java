package com.roy.order.base.mappers.product;

import com.roy.order.base.domains.Product;
import com.roy.order.base.dtos.product.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO convert(Product source);

    List<ProductDTO> convert(List<Product> sources);

}

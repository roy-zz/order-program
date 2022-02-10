package com.roy.order.base.mappers.product;

import com.roy.order.base.domains.Product;
import com.roy.order.base.dtos.product.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-10T21:21:24+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (JetBrains s.r.o.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO convert(Product source) {
        if ( source == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setNumber( source.getNumber() );
        if ( source.getType() != null ) {
            productDTO.setType( source.getType().name() );
        }
        productDTO.setName( source.getName() );
        productDTO.setPrice( source.getPrice() );
        productDTO.setStockCount( source.getStockCount() );

        return productDTO;
    }

    @Override
    public List<ProductDTO> convert(List<Product> sources) {
        if ( sources == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( sources.size() );
        for ( Product product : sources ) {
            list.add( convert( product ) );
        }

        return list;
    }
}

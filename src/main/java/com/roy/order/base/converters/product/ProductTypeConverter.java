package com.roy.order.base.converters.product;

import com.roy.order.base.enums.product.ProductType;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductType attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @Override
    public ProductType convertToEntityAttribute(Integer dbData) {
        return Objects.isNull(dbData) ? null : ProductType.valueOf(dbData);
    }

}

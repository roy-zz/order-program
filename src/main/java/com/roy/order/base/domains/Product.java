package com.roy.order.base.domains;

import com.roy.order.base.converters.product.ProductTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.roy.order.base.enums.product.ProductType;

import javax.persistence.*;

// @formatter:off
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
(
    uniqueConstraints =
    {
        @UniqueConstraint(name = "uk_number", columnNames = {"number"})
    }
)
// @formatter:on
public class Product extends AbstractEntity {

    @Column(columnDefinition = "VARCHAR(32) DEFAULT NULL COMMENT '상품 번호'")
    private String number;

    @Column(columnDefinition = "MEDIUMINT(10) DEFAULT NULL COMMENT '종류'")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType type;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT NULL COMMENT '클래스 및 키트명'")
    private String name;

    @Column(columnDefinition = "INT(10) DEFAULT NULL COMMENT '판매 가격'")
    private Integer price;

    @Column(columnDefinition = "MEDIUMINT(10) DEFAULT NULL COMMENT '재고수'")
    private Integer stockCount;
}

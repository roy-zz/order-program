package com.roy.order.base.dtos.product;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String number;

    private String type;

    private String name;

    private Integer price;

    private Integer stockCount;

}

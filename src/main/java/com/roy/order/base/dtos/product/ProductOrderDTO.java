package com.roy.order.base.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {

    private ProductDTO productDTO;
    private int orderCount = 0;

}

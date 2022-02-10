package com.roy.order.base.dtos.payment;

import com.roy.order.base.dtos.product.ProductOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDTO {

    @Builder.Default
    private boolean isSucceed = false;

    @Builder.Default
    private List<ProductOrderDTO> totalProducts = new ArrayList<>();

    @Builder.Default
    private Integer productTotalPrice = 0;

    @Builder.Default
    private Integer deliveryCharge = 0;

}

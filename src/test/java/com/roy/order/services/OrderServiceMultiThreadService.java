package com.roy.order.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.roy.order.base.dtos.payment.PaymentResultDTO;
import com.roy.order.base.dtos.product.ProductDTO;
import com.roy.order.base.dtos.product.ProductOrderDTO;
import com.roy.order.base.enums.product.ProductType;
import com.roy.order.base.exceptions.SoldOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceMultiThreadService {

    @Autowired
    private OrderService orderService;

    public static List<PaymentResult> paymentResults = new ArrayList<>();

    @Async
    @Transactional
    public void asyncPayment() {

        List<ProductOrderDTO> basket = new ArrayList<>();
        basket.add(new ProductOrderDTO(new ProductDTO("9236", String.valueOf(ProductType.KIT), "하루의 시작과 끝, 욕실의 포근함을 선사하는 천연 비누", 9900, 22), 2));

        try {
            PaymentResultDTO result = orderService.paymentProductsInBasket(basket);
            paymentResults.add(new PaymentResult(result.isSucceed(), TransactionSynchronizationManager.getCurrentTransactionName(), null));
        } catch (SoldOutException exception) {
            paymentResults.add(new PaymentResult(false, TransactionSynchronizationManager.getCurrentTransactionName(), exception));
        }

    }

    @AllArgsConstructor
    class PaymentResult {
        boolean isSucceed;
        String threadName;
        Exception occurredException;
    }
}

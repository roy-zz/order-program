package com.roy.order.services;

import lombok.extern.slf4j.Slf4j;
import com.roy.order.base.exceptions.SoldOutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@DisplayName("주문 MultiThread 테스트")
public class OrderServiceMultiThreadTest {

    @Autowired
    private OrderServiceMultiThreadService orderServiceMultiThreadService;

    @Test
    @DisplayName("SoldOutException 테스트")
    void soldOutExceptionTest() throws Exception {

        String mainThreadName = TransactionSynchronizationManager.getCurrentTransactionName();

        for (int i = 0; i < 20; i++) {
            orderServiceMultiThreadService.asyncPayment();
        }

        Thread.sleep(2000);

        List<OrderServiceMultiThreadService.PaymentResult> testResult = OrderServiceMultiThreadService.paymentResults;

        // 테스트 전 재고 22
        // 쓰레드 당 주문 2개
        // index 10까지 결과 true 확인
        // index 11 ~ 20 SoldOutException 발생 확인
        for (int i = 0; i < 20; i++) {
            if (i <= 10) {
                Assertions.assertTrue(testResult.get(i).isSucceed);
                Assertions.assertNotEquals(testResult.get(i).threadName, mainThreadName);
                Assertions.assertNull(testResult.get(i).occurredException);
            }
            else {
                Assertions.assertFalse(testResult.get(i).isSucceed);
                Assertions.assertNotEquals(testResult.get(i).threadName, mainThreadName);
                Assertions.assertEquals(SoldOutException.class, testResult.get(i).occurredException.getClass());
            }
        }

    }
}

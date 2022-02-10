package com.roy.order.services;

import com.roy.order.base.configurations.constants.AppConstant;
import com.roy.order.base.domains.Product;
import com.roy.order.base.dtos.payment.PaymentResultDTO;
import com.roy.order.base.dtos.product.ProductDTO;
import com.roy.order.base.dtos.product.ProductOrderDTO;
import com.roy.order.base.enums.product.ProductType;
import com.roy.order.base.exceptions.InvalidInputException;
import com.roy.order.base.exceptions.SoldOutException;
import com.roy.order.base.mappers.product.ProductMapper;
import com.roy.order.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;


@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ExtendWith(MockitoExtension.class)
@DisplayName("주문 서비스 테스트")
class OrderServiceTest {

    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("모든 상품 조회 테스트")
    void getAllProductsTest() {}

    @Test
    @DisplayName("상품 선택 테스트")
    void setWantToOrderProductTest() {

        List<ProductDTO> allProducts = new ArrayList<>();
        allProducts.add(new ProductDTO("1", String.valueOf(ProductType.KLASS), "1번 상폼", 1000, 99999));
        allProducts.add(new ProductDTO("2", String.valueOf(ProductType.KLASS), "2번 상품", 2000, 99999));
        allProducts.add(new ProductDTO("3", String.valueOf(ProductType.KIT), "3번 상품", 3000, 3000));
        allProducts.add(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상품", 4000, 4000));

        // CASE - 1
        // 중복된 KLASS 선택
        // GIVEN
        List<ProductOrderDTO> basketForCase1 = new ArrayList<>();
        basketForCase1.add(new ProductOrderDTO(new ProductDTO("1", String.valueOf(ProductType.KLASS), "1번 상폼", 1000, 99999), 1));
        // WHEN && THEN
        Assertions.assertThrows
                (
                        InvalidInputException.class, () ->
                        {
                            this.orderService.setWantToOrderProduct(allProducts, basketForCase1, new ProductOrderDTO(), "1");
                        }
                );

        // CASE - 2
        // 미중복 KLASS 선택
        // GIVEN
        List<ProductOrderDTO> basketForCase2 = new ArrayList<>();
        basketForCase2.add(new ProductOrderDTO(new ProductDTO("1", String.valueOf(ProductType.KLASS), "1번 상폼", 1000, 99999), 1));
        // WHEN && THEN
        Assertions.assertDoesNotThrow(() -> this.orderService.setWantToOrderProduct(allProducts, basketForCase2, new ProductOrderDTO(), "2"));

        // CASE - 3
        // 중복 KIT 선택
        // GIVEN
        List<ProductOrderDTO> basketForCase3 = new ArrayList<>();
        basketForCase3.add(new ProductOrderDTO(new ProductDTO("3", String.valueOf(ProductType.KIT), "3번 상폼", 3000, 3000), 1));
        // WHEN && THEN
        Assertions.assertDoesNotThrow(() -> this.orderService.setWantToOrderProduct(allProducts, basketForCase3, new ProductOrderDTO(), "3"));

        // CASE - 4
        // 미존재 상품 선택
        List<ProductOrderDTO> basketForCase4 = new ArrayList<>();
        // WHEN && THEN
        Assertions.assertThrows
                (
                        InvalidInputException.class, () ->
                        {
                            this.orderService.setWantToOrderProduct(allProducts, basketForCase4, new ProductOrderDTO(), "5");
                        }
                );
    }

    @Test
    @DisplayName("상품 수량 선택 테스트")
    void setWantToOrderCountTest() {}

    @Test
    @DisplayName("결제 테스트")
    void paymentProductsInBasketTest() {

        // CASE - 1, CASE - 2
        // 중복된 KIT 존재, KIT 주문 비용 < 배송비 무료 기준
        // 예상 결과: 중복된 KIT 들 간의 수량이 합쳐진 후 결제가 진행된다, 기본 배송비가 추가된다.
        // GIVEN
        List<ProductOrderDTO> basketForCase1 = new ArrayList<>();
        basketForCase1.add(new ProductOrderDTO(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상폼", 4000, 100), 1));
        basketForCase1.add(new ProductOrderDTO(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상폼", 4000, 100), 3));

        List<Product> storedProductForCase1 = new ArrayList<>();
        storedProductForCase1.add(new Product("4", ProductType.KIT, "4번 상품", 4000, 100));

        PaymentResultDTO expectedResultForCase1 = PaymentResultDTO.builder()
                .isSucceed(true)
                .totalProducts(List.of(new ProductOrderDTO(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상폼", 4000, 100), 4)))
                .productTotalPrice(16000)
                .deliveryCharge(AppConstant.Product.BASIC_DELIVERY_CHARGE)
                .build();

        given(this.productRepository.findAllByNumberIn(anyList()))
                .willReturn(storedProductForCase1);

        // WHEN
        PaymentResultDTO actualResultForCase1 = this.orderService.paymentProductsInBasket(basketForCase1);

        // THEN
        Assertions.assertEquals(expectedResultForCase1, actualResultForCase1);

        reset(this.productRepository);

        // CASE - 3
        // KIT 주문 비용 + KLASS 주문 비용 SUM < 배송비 무료 기준
        // 예상 결과: 배송비가 추가되지 않는다.
        // GIVEN
        List<ProductOrderDTO> basketForCase3 = new ArrayList<>();
        basketForCase3.add(new ProductOrderDTO(new ProductDTO("1", String.valueOf(ProductType.KLASS), "1번 상폼", 1000, 99999), 1));
        basketForCase3.add(new ProductOrderDTO(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상폼", 4000, 100), 3));

        List<Product> storedProductForCase3 = new ArrayList<>();
        storedProductForCase3.add(new Product("1", ProductType.KLASS, "1번 상품", 1000, 99999));
        storedProductForCase3.add(new Product("4", ProductType.KIT, "4번 상품", 4000, 100));

        given(this.productRepository.findAllByNumberIn(anyList()))
                .willReturn(storedProductForCase3);

        // WHEN
        PaymentResultDTO actualResultForCase3 = this.orderService.paymentProductsInBasket(basketForCase3);

        // THEN
        Assertions.assertTrue(actualResultForCase3.isSucceed());
        Assertions.assertEquals(0, actualResultForCase3.getDeliveryCharge());

        reset(this.productRepository);

        // CASE - 4
        // 재고 수량 부족
        // 예상 결과: SoldOutException 이 발생한다.
        // GIVEN
        List<ProductOrderDTO> basketForCase4 = new ArrayList<>();
        basketForCase4.add(new ProductOrderDTO(new ProductDTO("4", String.valueOf(ProductType.KIT), "4번 상폼", 4000, 100), 101));

        List<Product> storedProductForCase4 = new ArrayList<>();
        storedProductForCase4.add(new Product("4", ProductType.KIT, "4번 상품", 4000, 100));

        given(this.productRepository.findAllByNumberIn(anyList()))
                .willReturn(storedProductForCase4);

        // WHEN && THEN
        Assertions.assertThrows
                (
                        SoldOutException.class, () ->
                        {
                            this.orderService.paymentProductsInBasket(basketForCase4);
                        }
                );
    }
}
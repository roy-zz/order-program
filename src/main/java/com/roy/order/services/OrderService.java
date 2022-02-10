package com.roy.order.services;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.roy.order.base.configurations.constants.AppConstant;
import com.roy.order.base.domains.Product;
import com.roy.order.base.dtos.payment.PaymentResultDTO;
import com.roy.order.base.dtos.product.ProductDTO;
import com.roy.order.base.dtos.product.ProductOrderDTO;
import com.roy.order.base.enums.product.ProductType;
import com.roy.order.base.exceptions.InternalServiceException;
import com.roy.order.base.exceptions.InvalidInputException;
import com.roy.order.base.exceptions.SoldOutException;
import com.roy.order.base.mappers.product.ProductMapper;
import com.roy.order.base.utils.Utils;
import com.roy.order.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return productMapper.convert(allProducts);
    }

    public ProductOrderDTO setWantToOrderProduct(List<ProductDTO> allProducts, List<ProductOrderDTO> baskets, ProductOrderDTO orderingProduct, String productNumberOfWantToOrder) {
        // @formatter:off
        baskets.stream().filter
                (
                        i -> i.getProductDTO().getNumber().equals(productNumberOfWantToOrder)
                                && i.getProductDTO().getType().equals(String.valueOf(ProductType.KLASS))
                ).findFirst()
                .ifPresent(i -> {
                    throw new InvalidInputException(ExceptionCode.DUPLICATED_KLASS_ORDER);
                });
        // @formatter:on

        Optional<ProductDTO> wantToOrderProduct = allProducts.stream().filter(i -> i.getNumber().equals(productNumberOfWantToOrder)).findFirst();

        if (wantToOrderProduct.isEmpty()) {
            throw new InvalidInputException(ExceptionCode.INVALID_PRODUCT_NUMBER_INPUT);
        }
        else {
            orderingProduct.setProductDTO(wantToOrderProduct.get());
            return orderingProduct;
        }
    }

    public ProductOrderDTO setWantToOrderCount(ProductOrderDTO orderingProduct, String productCountOfWantToOrder) {

        orderingProduct.setOrderCount(Utils.stringToIntegerAndCheckPositiveInteger(productCountOfWantToOrder));

        return orderingProduct;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public synchronized PaymentResultDTO paymentProductsInBasket(List<ProductOrderDTO> baskets) {

        PaymentResultDTO paymentResult = new PaymentResultDTO();

        // @formatter:off
        // duplicated kit processing
        Set<ProductDTO> nonDuplicatedProduct = baskets.stream().map(ProductOrderDTO::getProductDTO).collect(Collectors.toSet());
        for (ProductDTO dto : nonDuplicatedProduct) {
            paymentResult.getTotalProducts().add
                    (new ProductOrderDTO(
                                    dto,
                                    baskets.stream()
                                            .filter(i -> i.getProductDTO().getNumber().equals(dto.getNumber()))
                                            .mapToInt(ProductOrderDTO::getOrderCount)
                                            .sum()
                            )
                    );
        }
        // @formatter:on

        // @formatter:off
        paymentResult.setProductTotalPrice(
                paymentResult.getTotalProducts().stream()
                        .mapToInt(i -> i.getProductDTO().getPrice() * i.getOrderCount())
                        .sum());
        // @formatter:on

        // @formatter:off
        paymentResult.setDeliveryCharge(
                paymentResult.getTotalProducts().stream().anyMatch(i -> i.getProductDTO().getType().equals(String.valueOf(ProductType.KLASS)))
                        || paymentResult.getProductTotalPrice() > AppConstant.Product.PRODUCT_TOTAL_PRICE_FOR_FREE_DELIVERY_CHARGE
                        ? 0 : AppConstant.Product.BASIC_DELIVERY_CHARGE
        );
        // @formatter:on

        List<Product> storedProducts = productRepository.findAllByNumberIn(paymentResult.getTotalProducts().stream().map(ProductOrderDTO::getProductDTO).map(ProductDTO::getNumber).collect(Collectors.toList()));

        // @formatter:off
        for (Product product : storedProducts) {

            if (product.getType() != ProductType.KLASS) {
                int stockCountAfterPayment = product.getStockCount() -
                        paymentResult.getTotalProducts().stream()
                                .filter(i -> i.getProductDTO().getNumber().equals(product.getNumber()))
                                .findFirst()
                                .orElseThrow(() -> new InternalServiceException(ExceptionCode.INTERNAL_SERVICE_EXCEPTION))
                                .getOrderCount();

                if (stockCountAfterPayment < 0) {
                    throw new SoldOutException(ExceptionCode.NOT_ENOUGH_STOCK_COUNT);
                } else {
                    product.setStockCount(stockCountAfterPayment);
                }
            }
        }
        // @formatter:on

        List<Product> paymentAfterProducts = productRepository.saveAll(storedProducts);

        for (Product product : paymentAfterProducts) {
            if (product.getStockCount() < 0) {
                throw new SoldOutException(ExceptionCode.NOT_ENOUGH_STOCK_COUNT);
            }
        }

        paymentResult.setSucceed(true);

        return paymentResult;
    }
}

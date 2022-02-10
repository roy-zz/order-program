package com.roy.order.controllers;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.roy.order.base.configurations.constants.AppConstant;
import com.roy.order.base.exceptions.AbstractErrorException;
import com.roy.order.base.exceptions.AbstractInfoException;
import com.roy.order.base.exceptions.AbstractWarnException;
import com.roy.order.base.exceptions.InvalidInputException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Controller extends AbstractController {

    private final OrderController orderController;
    private final AdminController adminController;

    @Override
    public void run(String... args) {

        while (true) {

            try {

                log.info("\n입력(o[order]: 주문, q[quit]: 종료)");

                String line = scanner.nextLine();

                switch (line) {
                    case AppConstant.Command.ORDER:
                        this.orderController.run();
                        break;
                    case AppConstant.Command.ADMIN:
                        this.adminController.run();
                        break;
                    case AppConstant.Command.QUIT:
                        log.info("{}", AppConstant.InformationMessage.THANKS_FOR_ORDER);
                        System.exit(0);
                        break;
                    default:
                        throw new InvalidInputException(ExceptionCode.INVALID_INPUT);
                }
            } catch (AbstractInfoException exception) {
                log.info("{}", exception.getCode().getMessage());
            } catch (AbstractWarnException exception) {
                log.warn("{}", exception.getCode().getMessage());
            } catch (AbstractErrorException exception) {
                log.error("{}", exception.getCode().getMessage());
            } catch (Exception exception) {
                log.info("{}", AppConstant.InformationMessage.UNEXPECTED_EXCEPTION);
            }
        }

    }
}

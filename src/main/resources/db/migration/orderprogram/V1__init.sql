
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `number` VARCHAR(32) DEFAULT NULL COMMENT '상품 번호',
    `type` MEDIUMINT(10) DEFAULT NULL COMMENT '종류',
    `name` VARCHAR(255) DEFAULT NULL COMMENT '클래스 및 키트명',
    `price` INT(10) DEFAULT NULL COMMENT '판매 가격',
    `stock_count` MEDIUMINT(10) DEFAULT NULL COMMENT '재고수',
    UNIQUE KEY `uk_number` (`number`)
);

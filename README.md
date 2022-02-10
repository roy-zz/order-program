## 


## 주요 프로젝트 구조 방식
--- 
`controllers`: 사용자로부터 command line 으로 입력받는 역할을 담당한다.

`services`: 주요 로직을 처리한다.

`repositories`: DB I/O를 담당한다.

`base/*`: 공통적으로 사용될 모듈들로 구성되어있다.

## 데이터베이스
---
프로젝트 실행시 Flyway를 사용하여 상품 테이블 생성 및 기초 데이터를 삽입하도록 구현하였다.

사용된 sql은 main.resources.db.migration.orderprogram에서 확인가능하다.

```sql
CREATE TABLE `product` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `number` VARCHAR(32) DEFAULT NULL COMMENT '상품 번호',
    `type` MEDIUMINT(10) DEFAULT NULL COMMENT '종류',
    `name` VARCHAR(255) DEFAULT NULL COMMENT '클래스 및 키트명',
    `price` INT(10) DEFAULT NULL COMMENT '판매 가격',
    `stock_count` MEDIUMINT(10) DEFAULT NULL COMMENT '재고수',
    UNIQUE KEY `uk_number` (`number`)
);
```
`type` 컬럼은 실제 타입명이 아니라 int 타입으로 저장되도록 구현하였다. com.roy.order.enums.product.ProductType 에 따라 실제 타입명으로 변경된다.

`number` 컬럼은 기존 README.md 파일인 TASK.md 파일의 데이터 타입에 따라 VARCHAR로 구현하였다.

## 테스트

테스트 코드의 역할을 아래와 같다

- 단위 테스트

    - `test.OrderServiceTest`: OrderService.class 를 단위 테스트하는데 사용한다.

- SoldOutException 테스트 (다중 Thread 요청)

    - `test.OrderServiceMultiThreadService(이하 MTService)`: 실제로 MultiThread 로 작동할 로직을 담고 있다.
    결제를 진행하고 그 결과를 `paymentResults` 에 추가하도록 구현하였다.

    - `test.OrderServiceMultiThreadTest(이하 MTTest)`: MultiThread 테스트의 Main Thread로써 
    MultiThread 발생시키고 MultiThread 에 의한 결과를 검증한다.
    
    - 테스트 진행 방식은 아래와 같다.
    테스트 대상 데이터는 '9236'번 상품으로 진행하였고 해당 상품의 재고는 22개이다.

        a. MTTest 가 MTService 의 asyncPayment()를 총 20번 호출하여 서로 다른 Thread에 의해 결제가 20번 (한 번의 결제당 2개 주문) 진행되도록 하고 3초를 대기한다.
        
        b. a.단계에서 생성된 Thread 는 결제의 결과를 paymentResults 에 저장한다.
        
        c. 3초를 대기한 MTTest 의 MainThread 가 paymentResults 를 통하여 테스트 결과를 검증한다.
        
        검증하는 데이터는 아래와 같다.
        - 재고가 부족할 때 SoldOutException 이 발생하였는가
        - 결제 작업을 진행한 Thread 가 MainThread 가 아닌가
        
## 실행
intellij: `run` 버튼으로 실행

CLI: `gradle bootrun`
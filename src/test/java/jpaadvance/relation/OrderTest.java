package jpaadvance.relation;

import jpaadvance.entity.MTMFood;
import jpaadvance.entity.MTMUser;
import jpaadvance.entity.Order;
import jpaadvance.repository.OrderRepository;
import jpaadvance.repository.MTMFoodRepository;
import jpaadvance.repository.MTMUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class OrderTest {

    @Autowired
    MTMUserRepository mtmUserRepository;
    @Autowired
    MTMFoodRepository mtmFoodRepository;
    @Autowired
    OrderRepository orderRepository;
    @Test
    @Rollback(value = false)
    @DisplayName("중간 테이블 Order Entity 테스트")
    void test1() {

        MTMUser mtmUser = new MTMUser();
        mtmUser.setName("Robbie");

        MTMFood mtmFood = new MTMFood();
        mtmFood.setName("후라이드 치킨");
        mtmFood.setPrice(15000);

        // 주문 저장
        Order order = new Order();
        order.setUser(mtmUser); // 외래 키(연관 관계) 설정
        order.setFood(mtmFood); // 외래 키(연관 관계) 설정

        mtmUserRepository.save(mtmUser);
        mtmFoodRepository.save(mtmFood);
        orderRepository.save(order);
    }

    @Test
    @DisplayName("중간 테이블 Order Entity 조회")
    void test2() {
        // 1번 주문 조회
        Order order = orderRepository.findById(1L).orElseThrow(NullPointerException::new);

        // order 객체를 사용하여 고객 정보 조회
        MTMUser user = order.getUser();
        System.out.println("user.getName() = " + user.getName());

        // order 객체를 사용하여 음식 정보 조회
        MTMFood food = order.getFood();
        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());
    }

}
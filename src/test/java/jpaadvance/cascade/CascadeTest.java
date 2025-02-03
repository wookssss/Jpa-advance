package jpaadvance.cascade;

import jpaadvance.entity.MTOFood;
import jpaadvance.entity.MTOUser;
import jpaadvance.repository.MTOFoodRepository;
import jpaadvance.repository.MTOUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CascadeTest {

    @Autowired
    MTOUserRepository mtoUserRepository;
    @Autowired
    MTOFoodRepository mtoFoodRepository;

    @Test
    @DisplayName("Robbie 음식 주문")
    void test1() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        MTOUser user = new MTOUser();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        MTOFood food = new MTOFood();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food);

        MTOFood food2 = new MTOFood();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2);

        mtoUserRepository.save(user);
        mtoFoodRepository.save(food);
        mtoFoodRepository.save(food2);
    }
    @Test
    @DisplayName("영속성 전이 저장")
    void test2() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        MTOUser user = new MTOUser();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        MTOFood food = new MTOFood();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food);

        MTOFood food2 = new MTOFood();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2);

        mtoUserRepository.save(user);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("Robbie 탈퇴")
    void test3() {
        // 고객 Robbie 를 조회합니다.
        MTOUser user = mtoUserRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // Robbie 가 주문한 음식 조회
        for (MTOFood food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }

        // 주문한 음식 데이터 삭제
        mtoFoodRepository.deleteAll(user.getFoodList());


        // Robbie 탈퇴
        mtoUserRepository.delete(user);
    }
}
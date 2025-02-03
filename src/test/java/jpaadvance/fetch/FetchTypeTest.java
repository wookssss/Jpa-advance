package jpaadvance.fetch;

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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FetchTypeTest {

    @Autowired
    MTOUserRepository mtoUserRepository;
    @Autowired
    MTOFoodRepository mtoFoodRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void init() {
        List<MTOUser> userList = new ArrayList<>();
        MTOUser user1 = new MTOUser();
        user1.setName("Robbie");
        userList.add(user1);

        MTOUser user2 = new MTOUser();
        user2.setName("Robbert");
        userList.add(user2);
        mtoUserRepository.saveAll(userList);

        List<MTOFood> foodList = new ArrayList<>();
        MTOFood food1 = new MTOFood();
        food1.setName("고구마 피자");
        food1.setPrice(30000);
        food1.setMtoUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food1);

        MTOFood food2 = new MTOFood();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setMtoUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food2);

        MTOFood food3 = new MTOFood();
        food3.setName("후라이드 치킨");
        food3.setPrice(15000);
        food3.setMtoUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food3);

        MTOFood food4 = new MTOFood();
        food4.setName("후라이드 치킨");
        food4.setPrice(15000);
        food4.setMtoUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        MTOFood food5 = new MTOFood();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setMtoUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        mtoFoodRepository.saveAll(foodList);
    }

    @Test
    @DisplayName("아보카도 피자 조회")
    void test1() {
        MTOFood food = mtoFoodRepository.findById(2L).orElseThrow(NullPointerException::new);

        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());

        System.out.println("아보카도 피자를 주문한 회원 정보 조회");
        System.out.println("food.getUser().getName() = " + food.getMtoUser().getName());
    }

    @Test
    @Transactional
    @DisplayName("Robbie 고객 조회")
    void test2() {
        MTOUser user = mtoUserRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (MTOFood food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }
    @Test
    @DisplayName("Robbie 고객 조회 실패")
    void test3() {
        MTOUser user = mtoUserRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (MTOFood food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }
}
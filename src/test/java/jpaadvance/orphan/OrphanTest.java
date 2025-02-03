package jpaadvance.orphan;

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
public class OrphanTest {

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
        food4.setName("양념 치킨");
        food4.setPrice(20000);
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
    @Transactional
    @Rollback(value = false)
    @DisplayName("연관관계 제거")
    void test1() {
        // 고객 Robbie 를 조회합니다.
        MTOUser user = mtoUserRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // 연관된 음식 Entity 제거 : 후라이드 치킨
        MTOFood chicken = null;
        for (MTOFood food : user.getFoodList()) {
            if(food.getName().equals("후라이드 치킨")) {
                chicken = food;
            }
        }
        if(chicken != null) {
            user.getFoodList().remove(chicken);
        }

        // 연관관계 제거 확인
        for (MTOFood food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }
    }
}
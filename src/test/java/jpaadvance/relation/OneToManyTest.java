package jpaadvance.relation;

import jpaadvance.entity.OTMFood;
import jpaadvance.entity.OTMUser;
import jpaadvance.repository.OTMFoodRepository;
import jpaadvance.repository.OTMUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class OneToManyTest {

    @Autowired
    OTMUserRepository otmUserRepository;
    @Autowired
    OTMFoodRepository otmFoodRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("1대N 단방향 테스트")
    void test1() {
        OTMUser otmUser = new OTMUser();
        otmUser.setName("Robbie");

        OTMUser otmUser2 = new OTMUser();
        otmUser2.setName("Robbert");

        OTMFood otmFood = new OTMFood();
        otmFood.setName("후라이드 치킨");
        otmFood.setPrice(15000);
        otmFood.getUserList().add(otmUser); // 외래 키(연관 관계) 설정
        otmFood.getUserList().add(otmUser2); // 외래 키(연관 관계) 설정

        otmUserRepository.save(otmUser);
        otmUserRepository.save(otmUser2);
        otmFoodRepository.save(otmFood);

        // 추가적인 UPDATE 쿼리 발생을 확인할 수 있습니다.
    }

    @Test
    @DisplayName("1대N 조회 테스트")
    void test2() {
        OTMFood otmFood = otmFoodRepository.findById(1L).orElseThrow(NullPointerException::new);
        System.out.println("food.getName() = " + otmFood.getName());

        // 해당 음식을 주문한 고객 정보 조회
        List<OTMUser> userList = otmFood.getUserList();
        for (OTMUser otmUser : userList) {
            System.out.println("user.getName() = " + otmUser.getName());
        }
    }
}
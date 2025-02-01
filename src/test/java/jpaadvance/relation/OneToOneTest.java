package jpaadvance.relation;

import jpaadvance.entity.OTOFood;
import jpaadvance.entity.OTOUser;
import jpaadvance.repository.OTOFoodRepository;
import jpaadvance.repository.OTOUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
public class OneToOneTest {
    @Autowired
    OTOUserRepository OTOUserRepository;

    @Autowired
    OTOFoodRepository OTOFoodRepository;

    @Test
    @Rollback(value = false) // 테스트에서는 @Transactional 에 의해 자동 rollback 됨으로 false 설정해준다.
    @DisplayName("1대1 단방향 테스트")
    void test1() {
        OTOUser otoUser = new OTOUser();
        otoUser.setName("Robbie");

        // 외래 키의 주인인 OTOFood Entity OTOUser 필드에 OTOUser 객체를 추가해 줍니다.
        OTOFood otoFood = new OTOFood();
        otoFood.setName("후라이드 치킨");
        otoFood.setPrice(15000);
        otoFood.setOtoUser(otoUser); // 외래 키(연관 관계) 설정

        OTOUserRepository.save(otoUser);
        OTOFoodRepository.save(otoFood);
    }
    @Test
    @Rollback(value = false)
    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패")
    void test2() {
        OTOFood otoFood = new OTOFood();
        otoFood.setName("고구마 피자");
        otoFood.setPrice(30000);

        // 외래 키의 주인이 아닌 OTOUser 에서 OTOFood 를 저장해보겠습니다.
        OTOUser otoUser = new OTOUser();
        otoUser.setName("Robbie");
        otoUser.setOtoFood(otoFood);

        OTOUserRepository.save(otoUser);
        OTOFoodRepository.save(otoFood);

        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }

    @Test
    @Rollback(value = false)
    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {
        OTOFood otoFood = new OTOFood();
        otoFood.setName("고구마 피자");
        otoFood.setPrice(30000);

        // 외래 키의 주인이 아닌 OTOUser 에서 OTOFood 를 저장하기 위해 addFood() 메서드 추가
        // 외래 키(연관 관계) 설정 OTOFood.setOTOUser(this); 추가
        OTOUser otoUser = new OTOUser();
        otoUser.setName("Robbie");
        otoUser.addFood(otoFood);

        OTOUserRepository.save(otoUser);
        OTOFoodRepository.save(otoFood);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("1대1 양방향 테스트")
    void test4() {
        OTOUser otoUser = new OTOUser();
        otoUser.setName("Robbert");

        OTOFood otoFood = new OTOFood();
        otoFood.setName("고구마 피자");
        otoFood.setPrice(30000);
        otoFood.setOtoUser(otoUser); // 외래 키(연관 관계) 설정

        OTOUserRepository.save(otoUser);
        OTOFoodRepository.save(otoFood);
    }
    @Test
    @DisplayName("1대1 조회 : OTOFood 기준 user 정보 조회")
    void test5() {
        OTOFood otoFood = OTOFoodRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 음식 정보 조회
        System.out.println("OTOFood.getName() = " + otoFood.getName());

        // 음식을 주문한 고객 정보 조회
        System.out.println("OTOFood.getOTOUser().getName() = " + otoFood.getOtoUser().getName());
    }

    @Test
    @DisplayName("1대1 조회 : OTOUser 기준 food 정보 조회")
    void test6() {
        OTOUser otoUser = OTOUserRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 고객 정보 조회
        System.out.println("otoUser.getName() = " + otoUser.getName());

        // 해당 고객이 주문한 음식 정보 조회
        OTOFood otoFood = otoUser.getOtoFood();
        System.out.println("otoFood.getName() = " + otoFood.getName());
        System.out.println("otoFood.getPrice() = " + otoFood.getPrice());
    }
}
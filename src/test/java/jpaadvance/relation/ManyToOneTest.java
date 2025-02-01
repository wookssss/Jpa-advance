package jpaadvance.relation;

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

import java.util.List;

@Transactional
@SpringBootTest
public class ManyToOneTest {

    @Autowired
    MTOUserRepository mtoUserRepository;
    @Autowired
    MTOFoodRepository mtoFoodRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 단방향 테스트")
    void test1() {
        MTOUser mtoUser = new MTOUser();
        mtoUser.setName("Robbie");

        MTOFood mtoFood = new MTOFood();
        mtoFood.setName("후라이드 치킨");
        mtoFood.setPrice(15000);
        mtoFood.setMtoUser(mtoUser); // 외래 키(연관 관계) 설정

        MTOFood mtoFood2 = new MTOFood();
        mtoFood2.setName("양념 치킨");
        mtoFood2.setPrice(20000);
        mtoFood2.setMtoUser(mtoUser); // 외래 키(연관 관계) 설정

        mtoUserRepository.save(mtoUser);
        mtoFoodRepository.save(mtoFood);
        mtoFoodRepository.save(mtoFood2);
    }
    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패")
    void test2() {

        MTOFood mtoFood = new MTOFood();
        mtoFood.setName("후라이드 치킨");
        mtoFood.setPrice(15000);

        MTOFood mtoFood2 = new MTOFood();
        mtoFood2.setName("양념 치킨");
        mtoFood2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        MTOUser mtoUser = new MTOUser();
        mtoUser.setName("Robbie");
        mtoUser.getFoodList().add(mtoFood);
        mtoUser.getFoodList().add(mtoFood2);

        mtoUserRepository.save(mtoUser);
        mtoFoodRepository.save(mtoFood);
        mtoFoodRepository.save(mtoFood2);

        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {

        MTOFood mtoFood = new MTOFood();
        mtoFood.setName("후라이드 치킨");
        mtoFood.setPrice(15000);

        MTOFood mtoFood2 = new MTOFood();
        mtoFood2.setName("양념 치킨");
        mtoFood2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드 생성하고
        // 해당 메서드에 외래 키(연관 관계) 설정 food.setUser(this); 추가
        MTOUser mtoUser = new MTOUser();
        mtoUser.setName("Robbie");
        mtoUser.addFoodList(mtoFood);
        mtoUser.addFoodList(mtoFood2);

        mtoUserRepository.save(mtoUser);
        mtoFoodRepository.save(mtoFood);
        mtoFoodRepository.save(mtoFood2);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트")
    void test4() {
        MTOUser mtoUser = new MTOUser();
        mtoUser.setName("Robbert");

        MTOFood mtoFood = new MTOFood();
        mtoFood.setName("고구마 피자");
        mtoFood.setPrice(30000);
        mtoFood.setMtoUser(mtoUser); // 외래 키(연관 관계) 설정

        MTOFood mtoFood2 = new MTOFood();
        mtoFood2.setName("아보카도 피자");
        mtoFood2.setPrice(50000);

        mtoFood2.setMtoUser(mtoUser); // 외래 키(연관 관계) 설정
        mtoUserRepository.save(mtoUser);
        mtoFoodRepository.save(mtoFood);
        mtoFoodRepository.save(mtoFood2);
    }
    @Test
    @DisplayName("N대1 조회 : Food 기준 user 정보 조회")
    void test5() {
        MTOFood mtoFood = mtoFoodRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 음식 정보 조회
        System.out.println("food.getName() = " + mtoFood.getName());

        // 음식을 주문한 고객 정보 조회
        System.out.println("food.getUser().getName() = " + mtoFood.getMtoUser().getName());
    }

    @Test
    @DisplayName("N대1 조회 : User 기준 food 정보 조회")
    void test6() {
        MTOUser user = mtoUserRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 고객 정보 조회
        System.out.println("user.getName() = " + user.getName());

        // 해당 고객이 주문한 음식 정보 조회
        List<MTOFood> foodList = user.getFoodList();
        for (MTOFood mtoFood : foodList) {
            System.out.println("food.getName() = " + mtoFood.getName());
            System.out.println("food.getPrice() = " + mtoFood.getPrice());
        }
    }
}
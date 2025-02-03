package jpaadvance.relation;

import jpaadvance.entity.MTMFood;
import jpaadvance.entity.MTMUser;
import jpaadvance.repository.MTMFoodRepository;
import jpaadvance.repository.MTMUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class ManyToManyTest {

    @Autowired
    MTMUserRepository mtmUserRepository;
    @Autowired
    MTMFoodRepository mtmFoodRepository;
    /* 중간 테이블을 따로 관리하지 않는 경우
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 단방향 테스트")
    void test1() {

        MTMUser mtmUser= new MTMUser();
        mtmUser.setName("Robbie");

        MTMUser mtmUser2 = new MTMUser();
        mtmUser2.setName("Robbert");

        MTMFood mtmFood = new MTMFood();
        mtmFood.setName("후라이드 치킨");
        mtmFood.setPrice(15000);
        mtmFood.getUserList().add(mtmUser);
        mtmFood.getUserList().add(mtmUser2);

        mtmUserRepository.save(mtmUser);
        mtmUserRepository.save(mtmUser2);
        mtmFoodRepository.save(mtmFood);

        // 자동으로 중간 테이블 orders 가 create 되고 insert 됨을 확인할 수 있습니다.
    }
    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패")
    void test2() {

        MTMFood food = new MTMFood();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        MTMFood food2 = new MTMFood();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        MTMUser user = new MTMUser();
        user.setName("Robbie");
        user.getFoodList().add(food);
        user.getFoodList().add(food2);

        mtmUserRepository.save(user);
        mtmFoodRepository.save(food);
        mtmFoodRepository.save(food2);

        // 확인해 보시면 orders 테이블에 food_id, user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {

        MTMFood food = new MTMFood();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        MTMFood food2 = new MTMFood();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드를 생성해서 사용합니다.
        // 외래 키(연관 관계) 설정을 위해 Food 에서 userList 를 호출해 user 객체 자신을 add 합니다.
        MTMUser user = new MTMUser();
        user.setName("Robbie");
        user.addFoodList(food);
        user.addFoodList(food2);


        mtmUserRepository.save(user);
        mtmFoodRepository.save(food);
        mtmFoodRepository.save(food2);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트")
    void test4() {

        MTMUser user = new MTMUser();
        user.setName("Robbie");

        MTMUser user2 = new MTMUser();
        user2.setName("Robbert");


        MTMFood food = new MTMFood();
        food.setName("아보카도 피자");
        food.setPrice(50000);
        food.getUserList().add(user); // 외래 키(연관 관계) 설정
        food.getUserList().add(user2); // 외래 키(연관 관계) 설정

        MTMFood food2 = new MTMFood();
        food2.setName("고구마 피자");
        food2.setPrice(30000);
        food2.getUserList().add(user); // 외래 키(연관 관계) 설정

        mtmUserRepository.save(user);
        mtmUserRepository.save(user2);
        mtmFoodRepository.save(food);
        mtmFoodRepository.save(food2);

        // User 를 통해 food 의 정보 조회
        System.out.println("user.getName() = " + user.getName());

        List<MTMFood> foodList = user.getFoodList();
        for (MTMFood f : foodList) {
            System.out.println("f.getName() = " + f.getName());
            System.out.println("f.getPrice() = " + f.getPrice());
        }

        // 외래 키의 주인이 아닌 User 객체에 Food 의 정보를 넣어주지 않아도 DB 저장에는 문제가 없지만
        // 이처럼 User 를 사용하여 food 의 정보를 조회할 수는 없습니다.
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대M 양방향 테스트 : 객체와 양방향의 장점 활용")
    void test5() {

        MTMUser user = new MTMUser();
        user.setName("Robbie");

        MTMUser user2 = new MTMUser();
        user2.setName("Robbert");


        // addUserList() 메서드를 생성해 user 정보를 추가하고
        // 해당 메서드에 객체 활용을 위해 user 객체에 food 정보를 추가하는 코드를 추가합니다. user.getFoodList().add(this);
        MTMFood food = new MTMFood();
        food.setName("아보카도 피자");
        food.setPrice(50000);
        food.addUserList(user);
        food.addUserList(user2);

        MTMFood food2 = new MTMFood();
        food2.setName("고구마 피자");
        food2.setPrice(30000);
        food2.addUserList(user);


        mtmUserRepository.save(user);
        mtmUserRepository.save(user2);
        mtmFoodRepository.save(food);
        mtmFoodRepository.save(food2);

        // User 를 통해 food 의 정보 조회
        System.out.println("user.getName() = " + user.getName());

        List<MTMFood> foodList = user.getFoodList();
        for (MTMFood f : foodList) {
            System.out.println("f.getName() = " + f.getName());
            System.out.println("f.getPrice() = " + f.getPrice());
        }
    }
    */
}
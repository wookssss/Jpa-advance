package jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "mto_users")
public class MTOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "mtoUser", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MTOFood> foodList = new ArrayList<>();

    public void addFoodList(MTOFood mtoFood) {
        this.foodList.add(mtoFood);
        mtoFood.setMtoUser(this); // 외래 키(연관 관계) 설정
    }
}

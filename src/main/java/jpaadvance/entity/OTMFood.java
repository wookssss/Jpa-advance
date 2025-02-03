package jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "otm_food")
public class OTMFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    @OneToMany
    @JoinColumn(name = "food_id") // users 테이블에 food_id 컬럼
    private List<OTMUser> userList = new ArrayList<>();
}
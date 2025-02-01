package jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "oto_users")
public class OTOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "otoUser")
    private OTOFood otoFood;

    public void addFood(OTOFood otoFood) {
        this.otoFood = otoFood;
        otoFood.setOtoUser(this);
    }
}

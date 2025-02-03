package jpaadvance.entity;

import jakarta.persistence.*;
import jpaadvance.entity.MTMFood;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private MTMFood food;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MTMUser user;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;
}
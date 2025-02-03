package jpaadvance.repository;

import jpaadvance.entity.MTMUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MTMUserRepository extends JpaRepository<MTMUser, Long> {
}

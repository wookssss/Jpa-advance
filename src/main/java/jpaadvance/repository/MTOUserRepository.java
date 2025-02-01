package jpaadvance.repository;

import jpaadvance.entity.MTOUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MTOUserRepository extends JpaRepository<MTOUser, Long> {
}

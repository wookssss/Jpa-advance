package jpaadvance.repository;

import jpaadvance.entity.OTMUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTMUserRepository extends JpaRepository<OTMUser, Long> {
}

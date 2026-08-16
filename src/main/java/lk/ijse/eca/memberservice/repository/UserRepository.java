package lk.ijse.eca.memberservice.repository;

import lk.ijse.eca.memberservice.entity.Role;
import lk.ijse.eca.memberservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
    Optional<User> findByEmail(String email);
}

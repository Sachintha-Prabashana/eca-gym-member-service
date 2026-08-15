package lk.ijse.eca.memberservice.repository;

import lk.ijse.eca.memberservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(Long userId);
    java.util.List<Member> findByTrainerId(Long trainerId);
}

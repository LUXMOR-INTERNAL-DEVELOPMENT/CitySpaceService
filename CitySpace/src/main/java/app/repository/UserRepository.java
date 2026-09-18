package app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Userimpl;

public interface UserRepository extends JpaRepository<Userimpl, Long> {

    Optional<Userimpl> findByAuthId(Long authId);

    Optional<Userimpl> findByUserId(String userId);
}
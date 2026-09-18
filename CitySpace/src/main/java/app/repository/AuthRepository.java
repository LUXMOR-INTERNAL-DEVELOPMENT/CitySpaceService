package app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Authimpl;

public interface AuthRepository extends JpaRepository<Authimpl, Long> {

    Optional<Authimpl> findByEmail(String email);

}
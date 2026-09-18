package app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Adminimpl;

public interface AdminRepository extends JpaRepository<Adminimpl, Long> {

    Optional<Adminimpl> findByAuthId(Long authId);

    Optional<Adminimpl> findByAdminId(String adminId);
}
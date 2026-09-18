package app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Vendorimpl;
import app.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendorimpl, Long> {

    Optional<Vendor> findByAuthId(Long authId);

    Optional<Vendor> findByVendorId(String vendorId);
}
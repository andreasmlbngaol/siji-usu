package com.sanalab.sijiusu.siji_admin.database.repository;

import com.sanalab.sijiusu.siji_admin.database.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByNip(String nip);
}

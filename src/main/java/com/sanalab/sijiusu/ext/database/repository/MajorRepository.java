package com.sanalab.sijiusu.ext.database.repository;

import com.sanalab.sijiusu.ext.database.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {

    boolean existsByMajorCode(String majorCode);
}

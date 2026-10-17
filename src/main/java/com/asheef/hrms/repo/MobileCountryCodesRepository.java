package com.asheef.hrms.repo;

import com.asheef.hrms.entity.MobileCountryCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobileCountryCodesRepository extends JpaRepository<MobileCountryCodes, Integer> {

    public Optional<MobileCountryCodes> findByCountryCode(String countryCode);
}

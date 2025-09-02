package com.tanfed.basicInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.TaxInfo;
@Repository
public interface TaxInfoRepo extends JpaRepository<TaxInfo, Long> {

}

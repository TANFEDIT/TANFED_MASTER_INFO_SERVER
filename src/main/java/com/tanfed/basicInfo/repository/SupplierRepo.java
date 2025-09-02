package com.tanfed.basicInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.SupplierInfo;
@Repository
public interface SupplierRepo extends JpaRepository<SupplierInfo, Long> {

	public SupplierInfo findBySupplierName(String supplierName);
		
}

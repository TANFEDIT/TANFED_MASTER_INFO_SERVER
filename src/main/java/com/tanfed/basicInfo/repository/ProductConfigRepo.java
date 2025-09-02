package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.ProductConfig;
@Repository
public interface ProductConfigRepo extends JpaRepository<ProductConfig, Long> {

	public List<ProductConfig> findByactivity(String activity);
	
	public List<ProductConfig> findByProductCategory(String productCategory);
}

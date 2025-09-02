package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.ProductMaster;
@Repository
public interface ProductMasterRepo extends JpaRepository<ProductMaster, Long> {

	@Query("SELECT e.productName FROM ProductMaster e")
	public List<String> findProductName();
	
	public ProductMaster findByProductName(String productName);
}

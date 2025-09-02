package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.DistrictTalukTable;
@Repository
public interface DistrictTalukRepo extends JpaRepository<DistrictTalukTable, Long> {

	@Query("SELECT e.district FROM DistrictTalukTable e")
	public List<String> findDistrict();
	
	public List<DistrictTalukTable> findTalukByDistrict(String district);
}

package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.DistrictBlockVillageTable;
@Repository
public interface DistrictBlockVillageRepo extends JpaRepository<DistrictBlockVillageTable, Long> {

	public List<DistrictBlockVillageTable> findBlockByDistrict(String district);
	
	public List<DistrictBlockVillageTable> findVillageByBlock(String block);
}

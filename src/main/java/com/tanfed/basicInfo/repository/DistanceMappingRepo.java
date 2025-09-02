package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.DistanceMapping;
@Repository
public interface DistanceMappingRepo extends JpaRepository<DistanceMapping, Long> {

	public List<DistanceMapping> findByOfficeNameAndGodownName(String officeName, String godownName);

	public List<DistanceMapping> findByOfficeName(String officeName);

}

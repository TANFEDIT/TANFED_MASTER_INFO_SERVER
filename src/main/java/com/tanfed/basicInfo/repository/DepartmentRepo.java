package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.DepartmentInfo;
@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentInfo, Long> {

	public List<DepartmentInfo> findByOfficeName(String officeName);
	
	public DepartmentInfo findByDepartment(String department);
}

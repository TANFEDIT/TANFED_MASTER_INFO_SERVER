package com.tanfed.basicInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.model.DistanceMapTableData;
@Repository
public interface DistanceMapTableDataRepo extends JpaRepository<DistanceMapTableData, Long> {

}

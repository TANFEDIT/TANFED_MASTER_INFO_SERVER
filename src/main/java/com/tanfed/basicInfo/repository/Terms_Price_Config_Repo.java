package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.Terms_Price_Config;
@Repository
public interface Terms_Price_Config_Repo extends JpaRepository<Terms_Price_Config, Long> {

	public List<Terms_Price_Config> findByActivity(String activity);
}

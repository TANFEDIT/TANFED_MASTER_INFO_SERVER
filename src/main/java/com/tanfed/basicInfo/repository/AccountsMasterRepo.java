package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.AccountsMaster;
@Repository
public interface AccountsMasterRepo extends JpaRepository<AccountsMaster, Long> {

	public List<AccountsMaster> findBySubHead(String subHead);
	
}

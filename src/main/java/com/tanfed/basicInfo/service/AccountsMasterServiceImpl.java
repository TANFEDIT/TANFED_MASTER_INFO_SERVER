package com.tanfed.basicInfo.service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.entity.AccountsMaster;
import com.tanfed.basicInfo.repository.AccountsMasterRepo;

@Service
public class AccountsMasterServiceImpl implements AccountsMasterService {

	@Autowired
	private AccountsMasterRepo accountsMasterRepo;

	@Override
	public ResponseEntity<String> saveAccountsMaster(List<AccountsMaster> obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.forEach(item -> item.setEmpId(Arrays.asList(empId)));

			accountsMasterRepo.saveAll(obj);
			return new ResponseEntity<String>("Created successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public String validateSubHead(String subHead, String mainHead) throws Exception {
		List<AccountsMaster> collect = accountsMasterRepo.findBySubHead(subHead).stream()
				.filter(i -> i.getMainHead().equals(mainHead)).collect(Collectors.toList());

		return !collect.isEmpty() ? "subHead Already Present" : "";
	}

	@Override
	public List<AccountsMaster> accountsMasterList() throws Exception {
		try {
			return accountsMasterRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editAccountsMaster(AccountsMaster obj, String jwt) throws Exception {
		try {
			AccountsMaster accountsMaster = accountsMasterRepo.findById(obj.getId()).get();
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			accountsMaster.getEmpId().add(empId);
			accountsMaster.setAssociatedWith(obj.getAssociatedWith());
			accountsMaster.setMainHead(obj.getMainHead());
			accountsMaster.setSubHead(obj.getSubHead());

			accountsMasterRepo.save(obj);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getMainHead() throws Exception {
		try {
			List<AccountsMaster> accountsMasterList = accountsMasterList();
			if (accountsMasterList == null) {
				throw new FileNotFoundException("No data found");
			}
			return accountsMasterList.stream().map(AccountsMaster::getMainHead).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getSubHeadByMainHead(String mainHead, String jwt) throws Exception {
		try {
			List<AccountsMaster> accountsMasterList = accountsMasterList();
			if (accountsMasterList == null) {
				throw new FileNotFoundException("No data found");
			}
			return accountsMasterList.stream().filter(item -> item.getMainHead().equals(mainHead))
					.map(AccountsMaster::getSubHead).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

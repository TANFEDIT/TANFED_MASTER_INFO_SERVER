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
import com.tanfed.basicInfo.entity.Terms_Price_Config;
import com.tanfed.basicInfo.repository.Terms_Price_Config_Repo;

@Service
public class Terms_Price_Config_ServiceImpl implements Terms_Price_Config_Service {

	@Autowired
	private Terms_Price_Config_Repo terms_Price_Config_Repo;

	@Override
	public ResponseEntity<String> saveTerms_Price_Config(Terms_Price_Config obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));
			terms_Price_Config_Repo.save(obj);
			return new ResponseEntity<String>("Created Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editTerms_Price_Config(Terms_Price_Config obj, String jwt) throws Exception {
		try {
			Terms_Price_Config terms_Price_Config = terms_Price_Config_Repo.findById(obj.getId()).get();
			if (terms_Price_Config == null) {
				throw new FileNotFoundException("No data found");
			}
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.getEmpId().add(empId);
			terms_Price_Config.setHeadName(obj.getHeadName());
			terms_Price_Config.setSupplyMode(obj.getSupplyMode());
			terms_Price_Config.setActivity(obj.getActivity());
			terms_Price_Config.setPaymentMode(obj.getPaymentMode());
			terms_Price_Config.setSalesCreditPeriod(obj.getSalesCreditPeriod());

			terms_Price_Config_Repo.save(terms_Price_Config);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<Terms_Price_Config> getTerms_Price_ConfigList() throws Exception {
		try {
			return terms_Price_Config_Repo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getHeadNameByActivity(String activity) throws Exception {
		try {
			List<Terms_Price_Config> byActivity = terms_Price_Config_Repo.findByActivity(activity);
			if (byActivity == null) {
				throw new Exception("no data found!");
			}
			return byActivity.stream().map(Terms_Price_Config::getHeadName).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getSupplyMode(String activity) throws Exception {
		try {
			return getTerms_Price_ConfigList().stream().filter(item -> item.getActivity().equals(activity))
					.map(Terms_Price_Config::getSupplyMode).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> gePaymentMode(String activity) throws Exception {
		try {
			return getTerms_Price_ConfigList().stream().filter(item -> item.getActivity().equals(activity))
					.map(Terms_Price_Config::getPaymentMode).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getPurchaseCreditPeriod(String activity) throws Exception {
		try {
			return getTerms_Price_ConfigList().stream().filter(item -> item.getActivity().equals(activity))
					.map(Terms_Price_Config::getPurchaseCreditPeriod).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

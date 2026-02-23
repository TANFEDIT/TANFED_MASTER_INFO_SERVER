package com.tanfed.basicInfo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.entity.TaxInfo;
import com.tanfed.basicInfo.repository.TaxInfoRepo;
import com.tanfed.basicInfo.response.GstRateData;

@Service
public class TaxInfoServiceImpl implements TaxInfoService {

	@Autowired
	private TaxInfoRepo taxInfoRepo;

	@Override
	public ResponseEntity<String> saveTaxInfo(List<TaxInfo> obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.forEach(item -> item.setEmpId(Arrays.asList(empId)));

			taxInfoRepo.saveAll(obj);

			return new ResponseEntity<>("Tax Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<TaxInfo> findTaxInfoList() throws Exception {
		try {
			return taxInfoRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editTaxInfo(TaxInfo obj, String jwt) throws Exception {
		try {
			TaxInfo taxInfo = taxInfoRepo.findById(obj.getId()).get();
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			taxInfo.getEmpId().add(empId);
			taxInfo.setGstNo(obj.getGstNo());
			taxInfo.setPanNo(obj.getPanNo());
			taxInfo.setTinNo(obj.getTinNo());
			taxInfo.setGstCategory(obj.getGstCategory());
			taxInfo.setGstRate(obj.getGstRate());
			taxInfo.setCgstRate(obj.getCgstRate());
			taxInfo.setIgstRate(obj.getIgstRate());
			taxInfo.setSgstRate(obj.getSgstRate());
			taxInfo.setRcmRate(obj.getRcmRate());

			taxInfoRepo.save(taxInfo);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public Set<String> findCategoryList() throws Exception {
		try {
			return taxInfoRepo.findAll().stream().map(TaxInfo::getGstCategory).collect(Collectors.toSet());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<Double> findGstRateByCategory(String gstCategory) throws Exception {
		try {
			return taxInfoRepo.findAll().stream().filter(item -> item.getGstCategory().equals(gstCategory))
					.map(TaxInfo::getGstRate).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public GstRateData findGstDataByGstRate(String gstCategory, Double gstRate) throws Exception {
		try {
			return taxInfoRepo.findAll().stream()
					.filter(item -> item.getGstCategory().equals(gstCategory) && item.getGstRate().equals(gstRate))
					.map(item -> new GstRateData(item.getCgstRate(), item.getSgstRate(), item.getIgstRate(),
							item.getRcmRate()))
					.collect(Collectors.toList()).get(0);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public String validateCategoryAndRate(String category, Double rate) throws Exception {
		try {
			return findGstDataByGstRate(category, rate) == null ? "New Data" : "Data Already Exists";
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

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
import com.tanfed.basicInfo.entity.ProductConfig;
import com.tanfed.basicInfo.repository.ProductConfigRepo;

@Service
public class ProductConfigServiceImpl implements ProductConfigService {

	@Autowired
	private ProductConfigRepo productConfigRepo;

	@Override
	public ResponseEntity<String> saveProductConfig(List<ProductConfig> obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.forEach(item -> item.setEmpId(Arrays.asList(empId)));
			productConfigRepo.saveAll(obj);
			return new ResponseEntity<String>("Created Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editProductConfig(ProductConfig obj, String jwt) throws Exception {
		try {
			ProductConfig productConfig = productConfigRepo.findById(obj.getId()).get();
			if (productConfig == null) {
				throw new FileNotFoundException("No data found");
			}
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			productConfig.getEmpId().add(empId);
			productConfig.setActivity(obj.getActivity());
			productConfig.setProductGroup(obj.getProductGroup());
			productConfig.setProductCategory(obj.getProductCategory());

			productConfigRepo.save(productConfig);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<ProductConfig> getProductConfigList() throws Exception {
		try {
			return productConfigRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getProductCategoryByActivity(String activity) throws Exception {
		try {
			List<ProductConfig> byactivity = productConfigRepo.findByactivity(activity);

			if (byactivity == null) {
				throw new Exception("No data found");
			}
			return byactivity.stream().map(ProductConfig::getProductCategory).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

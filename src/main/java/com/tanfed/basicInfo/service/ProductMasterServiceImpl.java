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
import com.tanfed.basicInfo.entity.ProductMaster;
import com.tanfed.basicInfo.repository.ProductConfigRepo;
import com.tanfed.basicInfo.repository.ProductMasterRepo;
import com.tanfed.basicInfo.response.DataForProductMaster;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

	@Autowired
	private ProductMasterRepo productMasterRepo;

	@Override
	public ResponseEntity<String> saveProduct(List<ProductMaster> obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.forEach(item -> item.setEmpId(Arrays.asList(empId)));
			productMasterRepo.saveAll(obj);

			return new ResponseEntity<String>("Created Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editProduct(ProductMaster obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.getEmpId().add(empId);
			productMasterRepo.save(obj);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getProductName() throws Exception {
		try {
			List<String> productName = productMasterRepo.findProductName();
			if (productName == null) {
				throw new FileNotFoundException("No data found");

			}
			return productName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ProductMaster getProductDataByProductName(String productName) throws Exception {
		try {
			ProductMaster byProductName = productMasterRepo.findByProductName(productName);
			if (byProductName == null) {
				throw new FileNotFoundException("No data found");

			}
			return byProductName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private ProductConfigRepo productConfigRepo;

	@Autowired
	private SupplierInfoService supplierInfoService;

	@Autowired
	private TaxInfoService taxInfoService;

	@Override
	public DataForProductMaster getDataForProductMaster(String formType, String activity, String productCategory,
			String supplierName, String gstCategory, Double gstRate, String productName) throws Exception {
		try {
			DataForProductMaster data = new DataForProductMaster();
			if (!activity.isEmpty() && activity != null) {
				data.setSupplierList(supplierInfoService.getSupplierName(activity));

				data.setProductCategoryList(productConfigRepo.findByactivity(activity).stream()
						.map(ProductConfig::getProductCategory).collect(Collectors.toSet()));

				data.setGstCategoryList(taxInfoService.findCategoryList());
				if (!productCategory.isEmpty() && productCategory != null) {
					data.setProductGroupList(productConfigRepo.findByactivity(activity).stream()
							.filter(item -> item.getProductCategory().equals(productCategory))
							.map(ProductConfig::getProductGroup).collect(Collectors.toSet()));
				}
				if (!formType.isEmpty() && formType != null && formType.equals("Create")) {
					if (!productName.isEmpty() && productName != null) {
						ProductMaster byProductName = productMasterRepo.findByProductName(productName);
						if (byProductName != null) {
							throw new Exception("Product Already Exist!");
						}
					}
				}
				if (!supplierName.isEmpty() && supplierName != null) {
					data.setSupplierGst(
							supplierInfoService.getSupplierInfoBySupplierName(supplierName).getSupplierGst());
				}
				if (!gstCategory.isEmpty() && gstCategory != null) {
					data.setGstRateList(taxInfoService.findGstRateByCategory(gstCategory));

					if (gstRate != null) {
						data.setGstData(taxInfoService.findGstDataByGstRate(gstCategory, gstRate));
					}
				}
			}

			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<ProductMaster> getProductData() throws Exception {
		try {
			return productMasterRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

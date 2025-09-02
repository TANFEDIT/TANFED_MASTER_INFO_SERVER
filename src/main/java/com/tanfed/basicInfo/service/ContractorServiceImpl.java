package com.tanfed.basicInfo.service;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.controller.DataController;
import com.tanfed.basicInfo.entity.*;
import com.tanfed.basicInfo.model.*;
import com.tanfed.basicInfo.repository.*;
import com.tanfed.basicInfo.response.*;

@Service
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorInfoRepo contractorInfoRepo;

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(ContractorServiceImpl.class);

	@Override
	public ResponseEntity<String> saveContractorInfo(ContractorDto obj, String jwt) throws Exception {
		try {
			ContractorTenderData tanderData = new ContractorTenderData(null, obj.getContractApproval(),
					obj.getHoIrRcno(), obj.getApprovalPeriod(), obj.getValidityFrom(), obj.getValidityTo(),
					obj.getHoLetterDate());

			List<ContractorTenderData> tenderDataList = new ArrayList<ContractorTenderData>();
			tenderDataList.add(tanderData);

			ContractorInfo data = new ContractorInfo();
			if (obj.getContractApproval().equals("New")) {

				List<ContractorInfo> byContractFirm = contractorInfoRepo.findByContractFirm(obj.getContractFirm());
				if (!byContractFirm.isEmpty()) {
					Stream<ContractorInfo> stream = byContractFirm.stream()
							.filter(item -> item.getOfficeName().equals(obj.getOfficeName()) && item.getDate() == null);
					if (!stream.toList().isEmpty()) {
						throw new FileAlreadyExistsException("Active contractor already exists!");
					}
				}

				String officeCode = userService.getOfficeList().stream().filter(item -> {
					return item.getOfficeName().equals(obj.getOfficeName());
				}).map(Office::getOfficeCode).collect(Collectors.toList()).get(0);

				String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
				data.setEmpId(Arrays.asList(empId));

				data.setOfficeName(obj.getOfficeName());
				data.setContractThrough(obj.getContractThrough());
				data.setContractFirm(obj.getContractFirm());
				data.setProprietorName(obj.getProprietorName());
				data.setDoor(obj.getDoor());
				data.setStreet(obj.getStreet());
				data.setDistrict(obj.getDistrict());
				data.setPincode(obj.getPincode());
				data.setTenderData(tenderDataList);
				data.setOfficeCode(officeCode);

				contractorInfoRepo.save(data);

				return new ResponseEntity<>("Contractor Info Created", HttpStatus.CREATED);
			} else {
				logger.info("{}", obj);
				ContractorInfo contractorInfo = contractorInfoRepo.findById(obj.getId()).get();
				contractorInfo.getTenderData().add(tanderData);
				contractorInfoRepo.save(contractorInfo);
				return new ResponseEntity<>("Contractor Extension added", HttpStatus.CREATED);
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ContractorInfo getContractorInfoByContractFirm(String officeName, String contractFirm) throws Exception {
		try {
			List<ContractorInfo> contractList = getContarctorInfoByOfficeName(officeName).stream()
					.filter(item -> item.getContractFirm().equals(contractFirm) && item.getDate() == null)
					.collect(Collectors.toList());
			return contractList.get(contractList.size() - 1);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<ContractorInfo> getContarctorInfoByOfficeName(String officeName) throws Exception {
		try {
			List<ContractorInfo> byOfficeName = contractorInfoRepo.findByOfficeName(officeName);

			if (byOfficeName == null) {
				throw new FileNotFoundException("No Contarctor Info found for office name" + officeName);
			}
			return byOfficeName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editContractorInfo(ContractorDto obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			ContractorInfo data = contractorInfoRepo.findById(obj.getId()).get();

			data.getEmpId().add(empId);
			data.setDoor(obj.getDoor());
			data.setStreet(obj.getStreet());
			data.setDistrict(obj.getDistrict());
			data.setPincode(obj.getPincode());

			contractorInfoRepo.save(data);

			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getContractFirmByOfficeName(String officeName) throws Exception {
		try {

			return getContarctorInfoByOfficeName(officeName).stream()
					.filter(item -> item.getEmdAmount() != null && item.getDate() == null)
					.map(ContractorInfo::getContractFirm).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private DataController dataController;

	@Autowired
	private TaxInfoService taxInfoService;

	@Autowired
	private GodownInfoService godownInfoService;

	@Override
	public DataForEmdEntry getDataForEmdEntry(String officeName, String contractFirm, String gstCategory,
			String gstRate) throws Exception {
		try {
			DataForEmdEntry data = new DataForEmdEntry();

			data.setOfficeList(dataController.getOfficeListHandler(""));

			data.setGstCategoryList(new HashSet<String>(taxInfoService.findCategoryList()));

			if (officeName != null && !officeName.isEmpty()) {
				data.setContractorList(
						getContarctorInfoByOfficeName(officeName).stream().filter(item -> item.getEmdAmount() == null)
								.map(ContractorInfo::getContractFirm).collect(Collectors.toList()));
				data.setGodownNameList(godownInfoService.getGodownNameByOfficeName(officeName));
				if (contractFirm != null && !contractFirm.isEmpty()) {
					ContractorInfo contarctorInfoByContractFirm = getContractorInfoByContractFirm(officeName,
							contractFirm);
					data.setContractThrough(contarctorInfoByContractFirm.getContractThrough());
					data.setTenderData(contarctorInfoByContractFirm.getTenderData());
					data.setId(contarctorInfoByContractFirm.getId());
				}
			}

			if (gstCategory != null && !gstCategory.isEmpty()) {
				data.setGstRateList(taxInfoService.findGstRateByCategory(gstCategory));

				if (gstRate != null && !gstRate.isEmpty()) {
					data.setGstData(taxInfoService.findGstDataByGstRate(gstCategory, Double.valueOf(gstRate)));
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> saveEmdDataForContractor(ContractorEmdEntryDto obj) throws Exception {
		try {
			ContractorInfo contractorInfo = contractorInfoRepo.findById(obj.getId()).get();
			logger.info("obj {}", obj);
			logger.info("ci {}", contractorInfo);
			contractorInfo.setEmdAmount(obj.getEmdAmount());
			contractorInfo.setSolvencyValue(obj.getSolvencyValue());
			contractorInfo.setEmdReceivedOn(obj.getEmdReceivedOn());
			contractorInfo.setAdditionalEmd(obj.getAdditionalEmd());
			contractorInfo.setGstNo(obj.getGstNo());
			contractorInfo.setGstReturnType(obj.getGstReturnType());
			contractorInfo.setGstData(obj.getGstAddedList());
			contractorInfo.setGodownName(obj.getGodownAddedList());
			ContractorChargesData charges = new ContractorChargesData(null, obj.getRateFrom(), obj.getRateTo(),
					LocalDate.now(), obj.getWagonClearance(), obj.getLoadingCharges(), obj.getUnloadingCharges(),
					obj.getHillRate(), obj.getZero_seven(), obj.getEight_twenty(), obj.getTwentyone_fifty(),
					obj.getFiftyone_seventyfive(), obj.getSeventysix_hundred(), obj.getHundredone_onetwentyfive(),
					obj.getOnetwosix_onefifty(), obj.getOnefiftyone_oneseventyfive(), obj.getOneseventysix_twohundred(),
					obj.getAbovetwohundredone());
			contractorInfo.setChargesData(new ArrayList<>(Arrays.asList(charges)));

			contractorInfoRepo.save(contractorInfo);

			return new ResponseEntity<String>("Emd Data Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForRateUpdate getDataForRateUpdate(String officeName, String contractFirm) throws Exception {
		try {
			DataForRateUpdate data = new DataForRateUpdate();
			data.setOfficeList(dataController.getOfficeListHandler(""));
			if (officeName != null && !officeName.isEmpty()) {
				data.setContractorList(getContarctorInfoByOfficeName(officeName).stream()
						.filter(item -> item.getEmdAmount() != null && item.getStatus().equals("Active")
								&& item.getDate() == null)
						.map(ContractorInfo::getContractFirm).collect(Collectors.toList()));
				if (contractFirm != null && !contractFirm.isEmpty()) {
					ContractorInfo contarctorInfoByContractFirm = getContractorInfoByContractFirm(officeName,
							contractFirm);
					data.setChargesData(contarctorInfoByContractFirm.getChargesData());
					data.setContractThrough(contarctorInfoByContractFirm.getContractThrough());
					data.setGstNo(contarctorInfoByContractFirm.getGstNo());
					data.setDataId(contarctorInfoByContractFirm.getId());
					data.setTenderData(contarctorInfoByContractFirm.getTenderData());
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> saveRateUpdate(ContractorChargesData obj, Long id) throws Exception {
		try {
			ContractorInfo contractorInfo = contractorInfoRepo.findById(id).get();
			obj.setUpdateDate(LocalDate.now());
			contractorInfo.getChargesData().add(obj);
			contractorInfoRepo.save(contractorInfo);
			return new ResponseEntity<String>("Rate Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForEmdRefund getDataForEmdRefund(String officeName, String contractFirm) throws Exception {
		try {
			DataForEmdRefund data = new DataForEmdRefund();
			data.setOfficeList(dataController.getOfficeListHandler(""));
			if (officeName != null && !officeName.isEmpty()) {
				data.setContractorList(getContarctorInfoByOfficeName(officeName).stream()
						.filter(item -> item.getEmdAmount() != null && item.getStatus().equals("Active")
								&& item.getDate() == null)
						.map(ContractorInfo::getContractFirm).collect(Collectors.toList()));
				if (contractFirm != null && !contractFirm.isEmpty()) {
					ContractorInfo contarctorInfoByContractFirm = getContractorInfoByContractFirm(officeName,
							contractFirm);
					data.setDataId(contarctorInfoByContractFirm.getId());
					data.setGstNo(contarctorInfoByContractFirm.getGstNo());
					data.setContractThrough(contarctorInfoByContractFirm.getContractThrough());
					data.setTenderData(contarctorInfoByContractFirm.getTenderData());
					data.setEmdAmount(contarctorInfoByContractFirm.getEmdAmount());
					data.setSolvencyValue(contarctorInfoByContractFirm.getSolvencyValue());
					data.setEmdReceivedOn(contarctorInfoByContractFirm.getEmdReceivedOn());
					data.setAdditionalEmd(contarctorInfoByContractFirm.getAdditionalEmd());
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> saveEmdRefund(ContractorInfo obj, Long id) throws Exception {
		try {
			ContractorInfo contractorInfo = contractorInfoRepo.findById(id).get();
			contractorInfo.setEmdRefundFor(obj.getEmdRefundFor());
			contractorInfo.setRefundAmount(obj.getRefundAmount());
			contractorInfo.setChequeVoucherJvNo(obj.getChequeVoucherJvNo());
			contractorInfo.setDate(obj.getDate());
			contractorInfo.setBlocklist(obj.getBlocklist());
			contractorInfo.setRemarks(obj.getRemarks());
			contractorInfo.setStatus("Inactive");
			contractorInfoRepo.save(contractorInfo);
			return new ResponseEntity<String>("Refund Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForContractorStatus getDataForContractorStatus(String officeName, Integer year) throws Exception {
		try {
			DataForContractorStatus data = new DataForContractorStatus();
			data.setOfficeList(dataController.getOfficeListHandler(""));

			if (officeName != null && !officeName.isEmpty()) {
				List<ContractorInfo> contarctorInfoByOfficeName = getContarctorInfoByOfficeName(officeName);

				List<ContractorInfo> filteredData = contarctorInfoByOfficeName.stream()
						.filter(item -> item.getTenderData().stream()
								.anyMatch(temp -> temp.getHoLetterDate().getYear() == year))
						.collect(Collectors.toList());

				List<ContractorStatusTable> tableData = new ArrayList<ContractorStatusTable>();
				if (!filteredData.isEmpty()) {
					filteredData.forEach(item -> {
						List<LocalDate> validityFromList = new ArrayList<>();
						List<LocalDate> validityToList = new ArrayList<>();
						List<String> hoIrRcnoList = new ArrayList<>();
						List<LocalDate> hoLetterDateList = new ArrayList<>();

						item.getTenderData().forEach(tender -> {
							validityFromList.add(tender.getValidityFrom());
							validityToList.add(tender.getValidityTo());
							hoIrRcnoList.add(tender.getHoIrRcno());
							hoLetterDateList.add(tender.getHoLetterDate());
						});
						List<String> additionalGodownList = item.getAdditionalGodownData().stream()
								.flatMap(itemData -> itemData.getAdditionalGodown().stream())
								.collect(Collectors.toList());
						tableData.add(new ContractorStatusTable(item.getContractThrough(), item.getEmdAmount(),
								item.getEmdReceivedOn(), item.getDate(), item.getContractFirm(), item.getGstNo(),
								validityFromList, validityToList, hoIrRcnoList, hoLetterDateList,
								item.getTenderData().get(item.getTenderData().size() - 1).getContractApproval(),
								item.getStatus(), item.getId(), item.getGodownName(), additionalGodownList));
					});
				}
				data.setTableData(tableData);
				data.setContractorList(getContractFirmByOfficeName(officeName));

				data.setInactiveContractorList(
						contarctorInfoByOfficeName.stream().filter(item -> item.getStatus().equals("Inactive"))
								.map(ContractorInfo::getContractFirm).collect(Collectors.toList()));

				List<String> godownNameList = godownInfoService.getGodownNameByOfficeName(officeName);

				Set<String> activeGodownNameList = contarctorInfoByOfficeName.stream()
						.filter(item -> item.getStatus().equals("Active"))
						.flatMap(item -> item.getGodownName().stream()).collect(Collectors.toSet());

				activeGodownNameList.addAll(contarctorInfoByOfficeName.stream()
						.filter(item -> "Active".equals(item.getStatus())).flatMap(item -> {
							if (item.getAdditionalGodownData() == null)
								return Stream.empty();
							return item.getAdditionalGodownData().stream()
									.flatMap(dataItem -> dataItem.getAdditionalGodown().stream())
									.filter(Objects::nonNull);
						}).filter(Objects::nonNull).collect(Collectors.toSet()));

				activeGodownNameList.forEach(item -> {
					if (godownNameList.contains(item)) {
						godownNameList.remove(item);
					}
				});

				data.setGodownList(godownNameList);
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> saveContractorStatus(ContractorStatusUpdateDto obj) throws Exception {
		try {
			ContractorInfo contractorInfo = getContractorInfoByContractFirm(obj.getOfficeName(), obj.getContractFirm());
			contractorInfo.getAdditionalGodownData().add(new ReassignedGodown(null, obj.getGodownAddedList(),
					obj.getRateDefinedAs(), obj.getBlockedContractor()));
			contractorInfoRepo.save(contractorInfo);
			return new ResponseEntity<String>("Refund Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> saveContractorStatus(Long id, String status) throws Exception {
		try {
			ContractorInfo contractorInfo = contractorInfoRepo.findById(id).get();
			contractorInfo.setStatus(status);
			contractorInfoRepo.save(contractorInfo);
			return new ResponseEntity<String>("Status Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ContractorInfo getContractFirmByGodownName(String officeName, String godownName) throws Exception {
		try {
			return getContarctorInfoByOfficeName(officeName).stream().filter(
					item -> item.getEmdAmount() != null && item.getStatus().equals("Active") && item.getDate() == null)
					.filter(item -> {
						boolean isGodownPresent = item.getGodownName().contains(godownName);
						boolean isAdditionalGodownPresent = item.getAdditionalGodownData().stream()
								.anyMatch(itemData -> {
									return itemData.getAdditionalGodown().contains(godownName);
								});
						return isGodownPresent || isAdditionalGodownPresent;
					}).collect(Collectors.toList()).get(0);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private OfficeInfoService officeInfoService;

	@Autowired
	private BuyerFirmService buyerFirmService;

	@Override
	public DataForDistanceMapping getDataForDistanceMapping(String type, String officeName, String godownName,
			String district, String toRegion, String category) throws Exception {
		try {
			DataForDistanceMapping data = new DataForDistanceMapping();
			data.setOfficeList(userService.getOfficeList().stream().map(item -> item.getOfficeName())
					.collect(Collectors.toList()));

			if (!officeName.isEmpty() && officeName != null) {
				data.setGodownList(godownInfoService.getGodownNameByOfficeName(officeName));
				mapTableDataForDM(data, officeName, district, godownName, type, toRegion, category);
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private void mapTableDataForDM(DataForDistanceMapping data, String officeName, String district, String godownName,
			String type, String toRegion, String category) throws Exception {
		List<DistanceMapping> byOfficeNameAndGodownName;
		if (!category.isEmpty() && category.equals("inter")) {
			if (!toRegion.isEmpty() && toRegion != null) {
				byOfficeNameAndGodownName = distanceMappingRepo.findByOfficeNameAndGodownName(officeName, godownName)
						.stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());

				if (!type.isEmpty() && type.equals("buffer")) {
					officeName = toRegion;
					mapBufferData(data, officeName, godownName, byOfficeNameAndGodownName);
				}
				if (!type.isEmpty() && type.equals("district")) {
					officeName = toRegion;
					List<DistanceMapping> collect = byOfficeNameAndGodownName.stream()
							.filter(item -> item.getType().equals("district")).collect(Collectors.toList());
					mapDistrictData(data, officeName, district, collect);
				}
			}
		} else if (!category.isEmpty() && category.equals("intra")) {
			byOfficeNameAndGodownName = distanceMappingRepo.findByOfficeNameAndGodownName(officeName, godownName)
					.stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());

			if (!type.isEmpty() && type.equals("buffer")) {
				logger.info("{}", byOfficeNameAndGodownName);
				mapBufferData(data, officeName, godownName, byOfficeNameAndGodownName.stream()
						.filter(item -> item.getType().equals(type)).collect(Collectors.toList()));
			}
			if (!type.isEmpty() && type.equals("district")) {
				List<DistanceMapping> collect = byOfficeNameAndGodownName.stream()
						.filter(item -> item.getType().equals(type)).collect(Collectors.toList());

				mapDistrictData(data, officeName, district, collect);
			}
		}
	}

	private void mapBufferData(DataForDistanceMapping data, String officeName, String godownName,
			List<DistanceMapping> byOfficeNameAndGodownName) throws Exception {

		if (!godownName.isEmpty() && godownName != null) {
			List<DistanceMapTableData> godownData = godownInfoService.getGodownInfoByOfficeName(officeName).stream()
					.filter(item -> !godownName.equals(item.getGodownName())).map(item -> new DistanceMapTableData(null,
							item.getIfmsId(), item.getGodownName(), null, null, null))
					.collect(Collectors.toList());

			if (!byOfficeNameAndGodownName.isEmpty()) {
				DistanceMapping distanceMapping = byOfficeNameAndGodownName.stream()
						.filter(item -> item.getType().equals("buffer")).collect(Collectors.toList()).get(0);

				data.setIdNo(distanceMapping.getId());

				List<DistanceMapTableData> distanceData = distanceMapping.getTableData().stream()
						.collect(Collectors.toList());

				if (distanceData.size() == godownData.size()) {
					data.setTableData(distanceData);
				} else {
					List<DistanceMapTableData> toRemove = new ArrayList<DistanceMapTableData>();

					godownData.forEach(item -> {
						distanceData.forEach(temp -> {
							if (item.getIfmsId().equals(temp.getIfmsId())) {
								toRemove.add(item);
							}
						});
					});
					godownData.removeAll(toRemove);
					data.setTableData(distanceData);
					data.getTableData().addAll(godownData);
				}
			} else {
				data.setIdNo(0L);
				data.setTableData(godownData);
			}
		}
	}

	private void mapDistrictData(DataForDistanceMapping data, String officeName, String district,
			List<DistanceMapping> byOfficeNameAndGodownName) throws Exception {

		data.setDistrictList(officeInfoService.getOfficeInfoByOfficeName(officeName).getDistrictList());

		if (!district.isEmpty() && district != null) {
			List<DistanceMapTableData> buyerData = buyerFirmService.getBuyerInfoByOfficeName(officeName).stream()
					.filter(item -> item.getDistrict().equals(district)).map(item -> new DistanceMapTableData(null,
							item.getIfmsIdNo(), item.getNameOfInstitution(), null, null, null))
					.collect(Collectors.toList());

			if (!byOfficeNameAndGodownName.isEmpty()) {
				List<DistanceMapping> distanceMappingList = byOfficeNameAndGodownName.stream()
						.filter(item -> item.getType().equals("district") && item.getDistrict().equals(district))
						.collect(Collectors.toList());

				if (distanceMappingList.isEmpty()) {
					logger.info("buyerData{}", buyerData);
					data.setTableData(buyerData);
					data.setIdNo(0L);
				} else {

					data.setIdNo(distanceMappingList.get(0).getId());

					List<DistanceMapTableData> distanceData = distanceMappingList.get(0).getTableData().stream()
							.collect(Collectors.toList());

					if (distanceData.size() == buyerData.size()) {
						data.setTableData(distanceData);
					} else {
						List<DistanceMapTableData> toRemove = new ArrayList<DistanceMapTableData>();

						buyerData.forEach(item -> {
							distanceData.forEach(temp -> {
								if (item.getIfmsId().equals(temp.getIfmsId())) {
									toRemove.add(item);
								}

							});
						});
						buyerData.removeAll(toRemove);
						data.setTableData(distanceData);
						data.getTableData().addAll(buyerData);
					}
				}

			} else {
				data.setIdNo(0L);
				data.setTableData(buyerData);
			}
		}
	}

	@Autowired
	private DistanceMappingRepo distanceMappingRepo;

	@Override
	public ResponseEntity<String> saveDistanceMapData(DistanceMapping obj, String idNo) throws Exception {
		try {
			logger.info("obj {}", obj);
			logger.info("idNo {}", idNo);
			if (Long.valueOf(idNo) != 0L) {
				DistanceMapping distanceMapping = distanceMappingRepo.findById(Long.valueOf(idNo)).get();
				distanceMapping.setTableData(obj.getTableData());
				distanceMappingRepo.save(distanceMapping);
			} else {
				distanceMappingRepo.save(obj);
			}
			return new ResponseEntity<>("Contractor Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<DistanceMappingAbstractTable> getDistanceMappingAbstractTableData(String region) throws Exception {
		try {
			List<DistanceMappingAbstractTable> tableData = new ArrayList<>();
			List<String> godownNameList = godownInfoService.getGodownNameByOfficeName(region);
			List<DistanceMapping> byOfficeName = distanceMappingRepo.findByOfficeName(region);

			for (String godownName : godownNameList) {
				Integer zero_seven = 0, eight_twenty = 0, twentyone_fifty = 0;
				Integer fiftyone_seventyfive = 0, seventysix_hundred = 0, hundredone_onetwentyfive = 0;
				Integer onetwosix_onefifty = 0, onefiftyone_oneseventyfive = 0;
				Integer oneseventysix_twohundred = 0, abovetwohundredone = 0;

				List<DistanceMapping> filteredList = byOfficeName.stream()
						.filter(item -> item.getGodownName().equals(godownName)).collect(Collectors.toList());

				for (DistanceMapping temp : filteredList) {
					for (DistanceMapTableData item : temp.getTableData()) {
						double km = item.getKm();
						if (km > 0 && km < 8)
							zero_seven++;
						else if (km < 21)
							eight_twenty++;
						else if (km < 51)
							twentyone_fifty++;
						else if (km < 76)
							fiftyone_seventyfive++;
						else if (km < 101)
							seventysix_hundred++;
						else if (km < 126)
							hundredone_onetwentyfive++;
						else if (km < 151)
							onetwosix_onefifty++;
						else if (km < 176)
							onefiftyone_oneseventyfive++;
						else if (km < 201)
							oneseventysix_twohundred++;
						else
							abovetwohundredone++;
					}
				}

				// Add only if data exists
				if (!filteredList.isEmpty()) {
					tableData.add(new DistanceMappingAbstractTable(godownName, zero_seven, eight_twenty,
							twentyone_fifty, fiftyone_seventyfive, seventysix_hundred, hundredone_onetwentyfive,
							onetwosix_onefifty, onefiftyone_oneseventyfive, oneseventysix_twohundred,
							abovetwohundredone));
				}
			}

			return tableData;
		} catch (Exception e) {
			throw new RuntimeException("Error processing distance mapping data", e);
		}

	}

//	@Override
//	public ResponseEntity<String> saveExistingDistanceMapData(DistanceMapping obj) throws Exception {
//		try {
//			DistanceMapping distanceMapping = distanceMappingRepo.findById(obj.getId()).get();
//			distanceMappingRepo.save(distanceMapping);
//			return new ResponseEntity<>("Contractor Info Created", HttpStatus.CREATED);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
//	}

}

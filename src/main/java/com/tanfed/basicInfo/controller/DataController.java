package com.tanfed.basicInfo.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.response.DistrictData;
import com.tanfed.basicInfo.service.DistrictService;
import com.tanfed.basicInfo.service.OfficeInfoService;
import com.tanfed.basicInfo.service.UserService;

@RestController
@RequestMapping("/api/data")
public class DataController {

	@Autowired
	private UserService userService;

	@GetMapping("/getofficeList")
	public List<String> getOfficeListHandler(@RequestParam String officeType) throws Exception {
		try {
			if (officeType.isEmpty()) {
				return userService.getOfficeList().stream().map(Office::getOfficeName).collect(Collectors.toList());
			} else {
				return userService.getOfficeList().stream().filter(item -> item.getOfficeType().equals(officeType))
						.map(Office::getOfficeName).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private DistrictService districtService;

	@Autowired
	private OfficeInfoService officeInfoService;

	@GetMapping("/fetchdistrictdata")
	public DistrictData getDistrictData(@RequestParam(required = false) String district,
			@RequestParam(required = false) String block, @RequestParam(required = false) String officeName)
			throws Exception {
		try {
			DistrictData response = new DistrictData();

			if (officeName.equals("Head Office")) {
				response.setDistrictList(getDistrictListHandler().stream().collect(Collectors.toList()));
			} else {
				response.setDistrictList(officeInfoService.getOfficeInfoByOfficeName(officeName).getDistrictList());
			}

			if (StringUtils.hasText(district)) {
				response.setTalukList(getTalukListByDistrictHandler(district));
				response.setBlockList(getBlockListByDistrict(district));

				if (StringUtils.hasText(block)) {
					response.setVillageList(getVillageByBlock(block));
				}
			}
			return response;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping("/fetchdistrict")
	public Set<String> getDistrictListHandler() throws Exception {
		return districtService.getDistrictList();
	}

	@GetMapping("/fetchtaluk")
	public List<String> getTalukListByDistrictHandler(@RequestParam String district) throws Exception {

		List<String> talukListByDistrict = districtService.getTalukListByDistrict(district);
		talukListByDistrict.addAll(districtService.getIfmsIdByDistrict(district));
		return talukListByDistrict;
	}

	@GetMapping("/fetchblock")
	public Set<String> getBlockListByDistrict(@RequestParam String district) throws Exception {
		return districtService.getBlockListByDistrict(district);
	}

	@GetMapping("/fetchvillage")
	public List<String> getVillageByBlock(@RequestParam String block) throws Exception {
		return districtService.getVillageByBlock(block);
	}

}

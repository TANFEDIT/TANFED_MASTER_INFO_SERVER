package com.tanfed.basicInfo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.entity.DistrictBlockVillageTable;
import com.tanfed.basicInfo.entity.DistrictTalukTable;
import com.tanfed.basicInfo.repository.DistrictBlockVillageRepo;
import com.tanfed.basicInfo.repository.DistrictTalukRepo;

@Service
public class DistrictService {

	@Autowired
	private DistrictTalukRepo districtTalukRepo;

	@Autowired
	private DistrictBlockVillageRepo districtBlockVillageRepo;

	public Set<String> getDistrictList() throws Exception {
		try {
			Set<String> districtSet = new HashSet<String>(districtTalukRepo.findDistrict());
			return districtSet;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public List<String> getTalukListByDistrict(String district) throws Exception {
		try {
			List<DistrictTalukTable> talukByDistrict = districtTalukRepo.findTalukByDistrict(district);
			if (talukByDistrict == null) {
				throw new Exception("No data found for district" + district);
			}
			return talukByDistrict.stream().map(DistrictTalukTable::getTaluk).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public List<String> getIfmsIdByDistrict(String district) throws Exception {
		try {
			List<DistrictTalukTable> talukByDistrict = districtTalukRepo.findTalukByDistrict(district);
			if (talukByDistrict == null) {
				throw new Exception("No data found for district" + district);
			}
			return new ArrayList<String>(
					talukByDistrict.stream().map(DistrictTalukTable::getIfmsId).collect(Collectors.toSet()));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public Set<String> getBlockListByDistrict(String district) throws Exception {
		try {
			List<DistrictBlockVillageTable> blockByDistrict = districtBlockVillageRepo.findBlockByDistrict(district);
			if (blockByDistrict == null) {
				throw new Exception("No data found for district" + district);
			}

			Set<String> blockSet = new HashSet<String>(
					blockByDistrict.stream().map(DistrictBlockVillageTable::getBlock).collect(Collectors.toList()));
			return blockSet;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public List<String> getVillageByBlock(String block) throws Exception {
		try {
			List<DistrictBlockVillageTable> villageByBlock = districtBlockVillageRepo.findVillageByBlock(block);
			if (villageByBlock == null) {
				throw new Exception("No data found for block" + block);
			}

			return villageByBlock.stream().map(DistrictBlockVillageTable::getVillage).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}

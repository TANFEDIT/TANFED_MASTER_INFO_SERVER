package com.tanfed.basicInfo.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {

	public static String[] district = { "Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore",
			"Dharmapuri", "Dindigul", "Erode", "Kallakurichi", "Kancheepuram", "Kanniyakumari", "Karur", "Krishnagiri",
			"Madurai", "Mayiladuthurai", "Nagapattinam", "Namakkal", "The Nilgiris", "Perambalur", "Pudukkottai",
			"Ramanathapuram", "Ranipet", "Salem", "Sivagangai", "Tenkasi", "Theni", "Tiruchirappalli", "Tirupathur",
			"Thiruvarur", "Thoothukkudi", "Tirunelveli", "Tiruppur", "Tiruvallur", "Tiruvannamalai", "Vellore",
			"Viluppuram", "Virudhunagar", "Chengalpattu" };

	public List<String> getDistrictList() throws Exception {
		try {
			return Arrays.asList(district);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}

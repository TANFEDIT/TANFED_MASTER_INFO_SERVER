package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.model.User;



@FeignClient(name = "USER-SERVICE", url = "${USER_SERVICE_URL:http://localhost:8080}")
public interface UserService {
	
	@GetMapping("/auth/getofficelist")
	public List<Office> getOfficeList();
	
	@GetMapping("/auth/blocklist")
	public List<String> getBlockedJwtList();
	
	@GetMapping("/api/user/fetchuser")
	public ResponseEntity<User> fetchUserHandler(@RequestHeader("Authorization") String jwt);
}

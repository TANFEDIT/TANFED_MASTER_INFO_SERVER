package com.tanfed.basicInfo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ACCOUNTS-SERVICE", url = "${ACCOUNTS_API_URL}")
public interface AccountsService {

    @PostMapping("/actuator/restart")
    public void restartService();
}

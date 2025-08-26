package com.neighbourly.auth.client;

import com.neighbourly.auth.dto.Response;
import com.neighbourly.auth.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "role-service", url = "${app.service.role.url}", configuration = com.neighbourly.auth.config.FeignHeaderForwardConfig.class)
public interface RoleClient {

    @GetMapping
    Response<List<RoleDto>> findByRoleName(@RequestParam String Name);
}

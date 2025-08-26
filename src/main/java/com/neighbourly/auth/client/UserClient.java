package com.neighbourly.auth.client;

import com.neighbourly.auth.dto.Response;
import com.neighbourly.auth.dto.UserDto;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "user-service", url = "${app.service.user.url}", configuration = com.neighbourly.auth.config.FeignHeaderForwardConfig.class)
public interface UserClient {

    @PostMapping
    Response<UserDto> createUser(UserDto userDto);

    @GetMapping
    Response<List<UserDto>> getUser(@RequestParam String  email);

    @GetMapping("/{userId}")
    Response<UserDto> getUser(@PathVariable Long userId);

    @PostMapping("/{userId}/roles/{roleId}")
    Response<UserDto> assignRoleToUser(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId);


}

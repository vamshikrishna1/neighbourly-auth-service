package com.neighbourly.auth.service;

import com.neighbourly.auth.client.RoleClient;
import com.neighbourly.auth.client.UserClient;
import com.neighbourly.auth.dto.*;
import com.neighbourly.auth.exception.BadRequestException;
import com.neighbourly.auth.store.CacheStore;
import com.neighbourly.auth.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import static com.neighbourly.auth.constants.AppConstants.OTP_EMAIL_SUBJECT;
import static com.neighbourly.auth.constants.AppConstants.OTP_EMAIL_TEMPLATE;
import static com.neighbourly.auth.constants.ROLE.VERIFIED_USER;
import static org.springframework.util.ObjectUtils.isEmpty;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final CacheStore otpStore;
    private final EmailService emailService;
    private final JsonUtil jsonUtil;
    private final UserClient userClient;
    private final RoleClient roleClient;
    private final TokenService tokenService;

    public void sendOtp(UserDto request) {

        Response<List<UserDto>> existingUser = userClient.getUser(request.getEmail());
        if(!isEmpty(existingUser) && !isEmpty(existingUser.getData())){
            throw new BadRequestException("USER-EXISTS", "User with email %s already exists".formatted(request.getEmail()), null);
        }

        String otp = generateOtp();
        otpStore.store("otp:code:"+request.getEmail(), otp);
        otpStore.store("otp:user:"+request.getEmail(), jsonUtil.writeValueAsString(request));
        emailService.sendEmail(request.getEmail(), OTP_EMAIL_SUBJECT, OTP_EMAIL_TEMPLATE.formatted(request.getFirstName(), otp));
    }

    private String generateOtp() {
        return String.valueOf(new SecureRandom().nextInt(99999) + 100000);
    }


    public Map<String, String> verifyOtp(VerifyOtpRequest request, String uuid) {
        String storedOtp = otpStore.get("otp:code:"+request.email());
        if( isEmpty(storedOtp) || !storedOtp.equals(request.otp())){
            throw new BadRequestException("INVALID-OTP", "Invalid or expired otp", uuid);
        }

        String cachedUserString = otpStore.get("otp:user:"+request.email());
        UserDto userDto = jsonUtil.read(cachedUserString, UserDto.class);
        Response<UserDto> userResponse = userClient.createUser(userDto);
        Response<List<RoleDto>> roleResponse = roleClient.findByRoleName(VERIFIED_USER.toString());
        roleResponse.getData().stream().findFirst().ifPresent(roleDto -> {
            userClient.assignRoleToUser(userResponse.getData().getUserId(), roleDto.getId());
        });

        otpStore.delete("otp:code:" + request.email());
        otpStore.delete("otp:user:" + request.email());
        String jwt = tokenService.generateToken(userResponse.getData().getUserId(), List.of(VERIFIED_USER.toString()));
        return Map.of(
                "accessToken", jwt
        );

    }


    public String login(LoginRequest request, String uuid) {
    Response<List<UserDto>> userDtoResponse = userClient.getUser(request.email());
        if(isEmpty(userDtoResponse) || isEmpty(userDtoResponse.getData())){
            throw new BadRequestException("USER-NOT-FOUND", "User with email %s not found".formatted(request.email()), uuid);
        }

        UserDto userDto = userDtoResponse.getData().stream().findFirst().get();

        if(!request.password().equals(userDto.getPassword())){
            throw new BadRequestException("INVALID-CREDENTIALS", "Invalid credentials", uuid);
        }

        String jwt = tokenService.generateToken(userDto.getUserId(), userDtoResponse.getData().stream()
                .flatMap(user -> user.getRoles().stream())
                .map(RoleDto::getRoleName)
                .toList());

        return jwt;


    }
}

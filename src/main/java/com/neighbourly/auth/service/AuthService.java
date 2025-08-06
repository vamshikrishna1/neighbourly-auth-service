package com.neighbourly.auth.service;

import com.neighbourly.auth.client.UserClient;
import com.neighbourly.auth.exception.BadRequestException;
import com.neighbourly.auth.mapper.UserMapper;
import com.neighbourly.auth.model.RegisterRequest;
import com.neighbourly.auth.model.UserDto;
import com.neighbourly.auth.model.VerifyOtpRequest;
import com.neighbourly.auth.store.CacheStore;
import com.neighbourly.auth.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final UserMapper userMapper;
    private final UserClient userClient;
    private final TokenService tokenService;

    public void sendOtp(RegisterRequest request) {
        String otp = generateOtp();
        otpStore.store("otp:code:"+request.getEmail(), otp);
        otpStore.store("otp:user:"+request.getEmail(), jsonUtil.writeValueAsString(request));
        emailService.sendEmail(request.getEmail(), OTP_EMAIL_SUBJECT, OTP_EMAIL_TEMPLATE.formatted(request.getFirstName(), otp));
    }

    private String generateOtp() {
        return String.valueOf(new SecureRandom().nextInt(99999) + 100000);
    }


    public ResponseEntity<Map<String, String>> verifyOtp(VerifyOtpRequest request, String uuid) {
        String storedOtp = otpStore.get("otp:code:"+request.email());
        if( isEmpty(storedOtp) || !storedOtp.equals(request.otp())){
            throw new BadRequestException("INVALID-OTP", "Invalid or expired otp", uuid);
        }

        String registerRequestString = otpStore.get("otp:user:"+request.email());
        RegisterRequest registerRequest = jsonUtil.read(registerRequestString, RegisterRequest.class);
        UserDto userDto = userMapper.toUserDto(registerRequest);
        userDto = userClient.createUser(userDto);
        otpStore.delete("otp:code:" + request.email());
        otpStore.delete("otp:user:" + request.email());
        String jwt = tokenService.generateToken(userDto.getUserId(), List.of(VERIFIED_USER.toString()));
        return ResponseEntity.ok(Map.of(
                "accessToken", jwt

        ));



    }




}

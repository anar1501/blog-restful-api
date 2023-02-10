package com.company.blogapi.controller;

import com.company.blogapi.dto.request.LoginRequestDto;
import com.company.blogapi.dto.request.RegisterConfirmRequestDto;
import com.company.blogapi.dto.request.RegisterRequestDto;
import com.company.blogapi.dto.request.ResetPasswordRequestDto;
import com.company.blogapi.dto.response.JWTAuthResponse;
import com.company.blogapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(UserService userService) {

    @ApiOperation(value = "REST API to Register or Signup user to Blog app")
    @PostMapping("/sign-in")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @ApiOperation(value = "REST API to Signin or Login user to Blog app")
    @PostMapping("/sign-up")
    public HttpStatus register(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.register(registerRequestDto);
        return HttpStatus.CREATED;
    }
    @PostMapping(value = "register-confirm")
    public HttpStatus registerConfirm(@RequestBody RegisterConfirmRequestDto requestDto) {
        userService.registerConfirm(requestDto);
        return HttpStatus.OK;
    }

    @GetMapping(value = "resend/{id}")
    public HttpStatus resendEmail(@PathVariable(value = "id") Long id) {
        userService.resendEmail(id);
        return HttpStatus.OK;
    }

    @PostMapping(value = "forget-password")
    public HttpStatus forgetPassword(@RequestParam(value = "email") String email) {
        userService.forgetPassword(email);
        return HttpStatus.OK;
    }

    @PutMapping(value = "reset-password")
    public HttpStatus resetPassword(@RequestBody ResetPasswordRequestDto requestDto) {
        userService.resetPassword(requestDto);
        return HttpStatus.OK;
    }
}

package com.company.blogapi.controller;

import com.company.blogapi.dto.request.LoginRequestDto;
import com.company.blogapi.dto.request.RegisterRequestDto;
import com.company.blogapi.dto.response.JWTAuthResponse;
import com.company.blogapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

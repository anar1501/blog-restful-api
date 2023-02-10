package com.company.blogapi.mapper;

import com.company.blogapi.dto.request.RegisterRequestDto;
import com.company.blogapi.model.User;
import com.company.blogapi.repository.RoleRepository;
import org.mapstruct.*;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;

import static com.company.blogapi.enums.RoleEnums.ROLE_USER;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), imports = {Objects.class, DateUtil.class})
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "password",expression = "java(toPassword(requestDto.getPassword()))")
    @Mapping(target = "expiredDate",expression = "java(DateUtil.prepareRegistrationExpirationDate())")
    @Mapping(target = "activationCode",expression = "java(DateUtil.getRandomNumberString())")
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "sixDigitCode",ignore = true)
    @Mapping(target = "forgetPasswordExpiredDate",ignore = true)
    @Mapping(target = "updateDate",ignore = true)
    public abstract User map(RegisterRequestDto requestDto);

    @Named(value = "toPassword")
    protected String toPassword(String password){
        return passwordEncoder.encode(password);
    }

    @AfterMapping
    void map(@MappingTarget User user){
        user.setRoles(Collections.singleton(roleRepository.findByName(ROLE_USER.getRoleName()).get()));
    }
}
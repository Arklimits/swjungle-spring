package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserInfoDTO {
    @NotNull
    @Size(min = 4, max = 10, message = "유저 이름 길이 오류.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유저 이름 구성 오류")
    private String username;
}

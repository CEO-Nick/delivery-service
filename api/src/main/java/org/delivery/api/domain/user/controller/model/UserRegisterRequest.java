package org.delivery.api.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest { // 회원 가입 시, 유저가 보내는 정보

    @NotBlank // 이름 필수
    private String name;

    @NotBlank
    @Email  // 이메일 형식으로 받아야 하니까
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;
}

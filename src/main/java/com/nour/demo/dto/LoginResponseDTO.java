package com.nour.demo.dto;
import com.nour.demo.config.JwtConfig;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

  
}
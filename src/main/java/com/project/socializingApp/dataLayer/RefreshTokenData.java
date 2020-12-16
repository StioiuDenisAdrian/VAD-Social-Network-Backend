package com.project.socializingApp.dataLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenData {
    @NotBlank
    private String refreshToken;
    private String userName;
}

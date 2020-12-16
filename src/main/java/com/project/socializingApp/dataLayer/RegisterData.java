package com.project.socializingApp.dataLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterData {
    private String userName;
    private String email;
    private String password;
}

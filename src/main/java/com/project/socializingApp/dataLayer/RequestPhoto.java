package com.project.socializingApp.dataLayer;

import com.project.socializingApp.model.User;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestPhoto implements Serializable {
    private String username;
    private byte[] image;
    private String description;
}

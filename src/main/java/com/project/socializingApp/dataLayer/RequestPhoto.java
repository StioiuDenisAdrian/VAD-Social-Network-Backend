package com.project.socializingApp.dataLayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.socializingApp.model.User;
import lombok.*;

import java.io.Serializable;
import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestPhoto implements Serializable {
    private String username;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private byte[] image;
    private String description;
}

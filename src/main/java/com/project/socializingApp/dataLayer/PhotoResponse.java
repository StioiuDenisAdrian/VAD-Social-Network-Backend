package com.project.socializingApp.dataLayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.socializingApp.dataLayer.AuthResponse;
import com.project.socializingApp.model.User;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.Transient;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PhotoResponse {
    private Long id;

    private User user;

    private Integer likes;
    private Integer dislikes;
    private String description;

}


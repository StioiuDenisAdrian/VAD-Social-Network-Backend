package com.project.socializingApp.model;

import com.project.socializingApp.dataLayer.AuthResponse;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class PhotoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    private Integer likes;
    private Integer dislikes;
    private String description;

}

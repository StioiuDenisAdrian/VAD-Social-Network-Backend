package com.project.socializingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.socializingApp.dataLayer.AuthResponse;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.Transient;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class PhotoModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private User user;

    //@Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(columnDefinition="BLOB")
    @JsonIgnore
    private byte[] picture;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<User> likes;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<User> dislikes;
    private String description;

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoModel that = (PhotoModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Arrays.equals(picture, that.picture) &&
                Objects.equals(likes, that.likes) &&
                Objects.equals(dislikes, that.dislikes) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user, likes, dislikes, description);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }*/
}

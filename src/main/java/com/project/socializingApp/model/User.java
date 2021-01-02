package com.project.socializingApp.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

    //@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long userId;
        @NotBlank(message = "Username is required")
        private String userName;
        @NotBlank(message = "Password is required")
        private String password;
        @Email
        @NotEmpty(message = "Email is required")

        private String email;
        private Instant created;
        private boolean enabled;

        @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        private List<Friendship> friends;

        @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        private List<PhotoModel> photos;

        @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        private List<PhotoModel> likes;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
        private List<PhotoModel> dislikes;

        private String likeString;
        private String dislikeString;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
        private List<PhotoModel> recommendation;

        private Integer recomIndex;

        /*@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return enabled == user.enabled &&
                    Objects.equals(userId, user.userId) &&
                    Objects.equals(userName, user.userName) &&
                    Objects.equals(password, user.password) &&
                    Objects.equals(email, user.email) &&
                    Objects.equals(created, user.created) &&
                    Objects.equals(friends, user.friends) &&
                    Objects.equals(photos, user.photos) &&
                    Objects.equals(likes, user.likes) &&
                    Objects.equals(dislikes, user.dislikes) &&
                    Objects.equals(likeString, user.likeString) &&
                    Objects.equals(dislikeString, user.dislikeString) &&
                    Objects.equals(recommendation, user.recommendation) &&
                    Objects.equals(recomIndex, user.recomIndex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, userName, password, email, created, enabled, friends, photos, likes, dislikes, likeString, dislikeString, recommendation, recomIndex);
        }*/
    }

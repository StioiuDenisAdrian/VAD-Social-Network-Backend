package com.project.socializingApp.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

    @Data
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

        @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        private List<User> friends;

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
    }

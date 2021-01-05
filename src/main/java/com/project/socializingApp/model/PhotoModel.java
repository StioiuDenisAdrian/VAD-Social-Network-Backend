package com.project.socializingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
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

}

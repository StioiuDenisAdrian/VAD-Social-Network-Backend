package com.project.socializingApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Friendship {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long friendshipId;

    @OneToOne
    private User owner;

    @OneToOne
    private User target;

    // other info
    public boolean compareTo(Friendship friendship){
        return this.getOwner().equals(friendship.getOwner()) &&
                this.getTarget().equals(friendship.getOwner());
    }
}

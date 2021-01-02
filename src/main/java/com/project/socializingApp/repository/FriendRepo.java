package com.project.socializingApp.repository;

import com.project.socializingApp.model.Friendship;
import com.project.socializingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FriendRepo  extends JpaRepository<Friendship, Long> {
    void deleteByOwnerAndTarget(@Param("owner") User owner, @Param("target") User target);
}

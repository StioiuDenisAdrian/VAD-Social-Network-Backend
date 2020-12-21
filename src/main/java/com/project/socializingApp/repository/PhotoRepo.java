package com.project.socializingApp.repository;

import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepo extends JpaRepository<PhotoModel, Long> {
    //List<PhotoModel> findAllByUserUserIdContains(Long id);
    List<PhotoModel> findAllByOrderByIdDesc();
    List<PhotoModel> findAllByUser_UserId(Long id);
}

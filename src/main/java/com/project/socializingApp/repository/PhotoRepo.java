package com.project.socializingApp.repository;

import com.project.socializingApp.model.PhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PhotoRepo extends JpaRepository<PhotoModel, Long> {
    //Set<PhotoModel> findAllByUserUserIdContains(Long id);
    List<PhotoModel> findAllByOrderByIdDesc();
    List<PhotoModel> findAllByUser_UserId(Long id);
}

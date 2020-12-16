package com.project.socializingApp.service;

import com.project.socializingApp.dataLayer.RequestPhoto;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.repository.PhotoRepo;
import com.project.socializingApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private PhotoRepo photoRepo;
    private UserRepo userRepo;

    @Autowired
    public PhotoService(PhotoRepo photoRepo, UserRepo userRepo) {
        this.photoRepo = photoRepo;
        this.userRepo = userRepo;
    }

    public PhotoModel addPhoto(RequestPhoto requestPhoto){
        PhotoModel photoModel = new PhotoModel();
        Optional<User> user = userRepo.findByUserName(requestPhoto.getUsername());
        photoModel.setUser(user.get());
        photoModel.setPicture(requestPhoto.getImage());
        photoModel.setDescription(requestPhoto.getDescription());
        photoModel.setLikes(0);
        photoModel.setDislikes(0);
        photoRepo.save(photoModel);
        return photoModel;
    }
    public List<PhotoModel> list(){
        return photoRepo.findAll();
    }

    public List<PhotoModel> listByUsername(Long id){
        return photoRepo.findAllByUserUserIdContains(id);
    }

    public void updateLike(PhotoModel photoModel){
        photoModel.setLikes(photoModel.getLikes()+1);
        photoRepo.save(photoModel);
    }

    public void updateDislike(PhotoModel photoModel){
        photoModel.setDislikes(photoModel.getDislikes()+1);
        photoRepo.save(photoModel);
    }

    public PhotoModel delete(Long id){
        PhotoModel photoModel = photoRepo.getOne(id);
        if(photoModel != null){
            photoRepo.delete(photoModel);
        }
        return photoModel;
    }

}

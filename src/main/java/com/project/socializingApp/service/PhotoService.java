package com.project.socializingApp.service;

import com.project.socializingApp.dataLayer.RequestPhoto;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.repository.PhotoRepo;
import com.project.socializingApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    private PhotoRepo photoRepo;
    private UserRepo userRepo;

    @Autowired
    public PhotoService(PhotoRepo photoRepo, UserRepo userRepo) {
        this.photoRepo = photoRepo;
        this.userRepo = userRepo;
    }
    @Transactional
    public PhotoModel addPhoto(RequestPhoto requestPhoto){
        PhotoModel photoModel = new PhotoModel();
        User user = userRepo.findByUserName(requestPhoto.getUsername()).orElseThrow();
        photoModel.setUser(user);
        photoModel.setPicture(requestPhoto.getImage());
        photoModel.setDescription(requestPhoto.getDescription());
        photoModel.setLikes(new ArrayList<User>());
        photoModel.setDislikes(new ArrayList<User>());
        for (User user1: userRepo.findAll()) {
            if(!user1.equals(user)) {
                user1.getRecommendation().add(photoModel);
            }
            //userRepo.save(user1);
        }
        //photoRepo.save(photoModel);
        return photoModel;
    }
    public List<PhotoModel> list(){
        List<PhotoModel> photoModels = new ArrayList<>(photoRepo.findAll());
        for (PhotoModel p:photoModels) {
            p.setPicture(new byte[]{1});
        }
        return photoModels;
    }

    public List<PhotoModel> listByUsername(String username){
        return photoRepo.findAllByUser_UserId(userRepo.findByUserName(username).orElseThrow().getUserId());
    }
    @Transactional
    public PhotoModel updateLike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getLikes();
        if(users.contains(user)){
            users.remove(user);
        }else {
            users.add(user);
        }
        //photoRepo.save(photoModel);
        return photoModel;
    }
    @Transactional
    public PhotoModel updateDislikeAndLike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getLikes();
        List<User> users1 = photoModel.getDislikes();
        if(users.contains(user)){
            users.remove(user);
        }else {
            users.add(user);
        }
        if(users1.contains(user)){
            users1.remove(user);
        }else {
            users1.add(user);
        }
        //photoRepo.save(photoModel);
        return photoModel;
    }
    @Transactional
    public PhotoModel updateDislike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getDislikes();
        if(users.contains(user)){
            users.remove(user);
        }else {
            users.add(user);
        }
        //photoRepo.save(photoModel);
        return photoModel;
    }

    public boolean getLike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getLikes();
        if(users.contains(user)){
            return true;
        }else {
            return false;
        }
    }

    public boolean getDislike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getDislikes();
        if(users.contains(user)){
            return true;
        }else {
            return false;
        }
    }

    public PhotoModel getById(Long id){
        return photoRepo.getOne(id);
    }

    public PhotoModel delete(Long id){
        PhotoModel photoModel = photoRepo.getOne(id);
        if(photoModel != null){
            photoRepo.delete(photoModel);
        }
        return photoModel;
    }

    public byte[] getPhoto(Long id){
        return photoRepo.getOne(id).getPicture();
    }

    public Integer getLikes(Long id){
        return photoRepo.getOne(id).getLikes().size();
    }

    public Integer getDislikes(Long id){
        return photoRepo.getOne(id).getDislikes().size();
    }

    public String getUsername(Long id){
        return photoRepo.getOne(id).getUser().getUserName();
    }

}

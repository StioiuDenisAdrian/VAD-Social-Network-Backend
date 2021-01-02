package com.project.socializingApp.service;

import com.project.socializingApp.dataLayer.RequestPhoto;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.repository.PhotoRepo;
import com.project.socializingApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recom.RecommendationSystem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        photoModel.setLikes(new ArrayList<>());
        photoModel.setDislikes(new ArrayList<>());
        for (User user1: userRepo.findAll()) {
            if(!user1.equals(user)) {
                user1.getRecommendation().add(photoModel);
                refreshRecom(user1);
            }
            //userRepo.save(user1);
        }
        //photoRepo.save(photoModel);
        return photoModel;
    }
    public List<PhotoModel> set(){
        List<PhotoModel> photoModels = new ArrayList<>(photoRepo.findAll());
        for (PhotoModel p:photoModels) {
            p.setPicture(new byte[]{1});
        }
        return photoModels;
    }

    public List<PhotoModel> setByUsername(String username){
        return photoRepo.findAllByUser_UserId(userRepo.findByUserName(username).orElseThrow().getUserId());
    }
    @Transactional
    public PhotoModel updateLike(Long id, String username){
        PhotoModel photoModel = photoRepo.getOne(id);
        User user = userRepo.findByUserName(username).orElseThrow();
        List<User> users = photoModel.getLikes();
        if(users.contains(user)){
            users.remove(user);
            user.getLikes().remove(photoModel);
        }else {
            users.add(user);
            user.getLikes().add(photoModel);
        }
        refreshRecom(user);
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
            user.getLikes().remove(photoModel);
        }else {
            users.add(user);
            user.getLikes().add(photoModel);
        }
        if(users1.contains(user)){
            users1.remove(user);
            user.getDislikes().remove(photoModel);
        }else {
            users1.add(user);
            user.getDislikes().add(photoModel);
        }
        refreshRecom(user);
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
            user.getDislikes().remove(photoModel);
        }else {
            users.add(user);
            user.getDislikes().add(photoModel);
        }
        refreshRecom(user);
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

    private void refreshRecom(User user) {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        try {
            user.setRecommendation(recommendationSystem.recommendations(user.getRecomIndex()
                    ,user.getRecommendation()
                    ,user.getLikes()
                    ,user.getDislikes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

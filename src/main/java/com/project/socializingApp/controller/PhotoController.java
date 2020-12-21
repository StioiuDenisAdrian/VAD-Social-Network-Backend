package com.project.socializingApp.controller;

import com.project.socializingApp.dataLayer.RequestPhoto;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.service.PhotoService;
import static org.springframework.http.HttpStatus.*;

import com.sun.mail.iap.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/")
    @ResponseStatus(OK)
    public List<PhotoModel> getAll(){
        return photoService.list();
    }

    @GetMapping("/user/")
    @ResponseStatus(OK)
    public List<PhotoModel> getAll(@RequestParam String username){
        return photoService.listByUsername(username);
    }

    @PostMapping("/post")
    @ResponseStatus(OK)
    public PhotoModel add(@RequestBody RequestPhoto requestPhoto){
        System.out.println(requestPhoto.getDescription());
        return photoService.addPhoto(requestPhoto);
    }

    @DeleteMapping("/")
    @ResponseStatus(OK)
    public void delete(@RequestParam Long id){
        photoService.delete(id);
    }

    @GetMapping("/get")
    @ResponseStatus(OK)
    public byte[] getPhoto(@RequestParam Long id){
        return photoService.getPhoto(id);
    }

    @PutMapping("/like")
    @ResponseStatus(OK)
    public PhotoModel updateLike(@RequestBody Long id, @RequestParam String username){
        return photoService.updateLike(id, username);
    }

    @PutMapping("/dislike")
    @ResponseStatus(OK)
    public PhotoModel updateDislike(@RequestBody Long id, @RequestParam String username){
        return photoService.updateDislike(id, username);
    }


    @PostMapping("/like")
    @ResponseStatus(OK)
    public boolean isLike(@RequestBody Long id, @RequestParam String username){
        return photoService.getLike(id, username);
    }

    @PostMapping("/dislike")
    @ResponseStatus(OK)
    public boolean isDislike(@RequestBody Long id, @RequestParam String username){
        return photoService.getDislike(id, username);
    }

    @GetMapping("/getBy")
    @ResponseStatus(OK)
    public PhotoModel getById(@RequestParam Long id){
        return photoService.getById(id);
    }

    @GetMapping("/like")
    @ResponseStatus(OK)
    public Integer getNrLikes(@RequestParam Long id) {
        return photoService.getLikes(id);
    }

    @GetMapping("/dislike")
    @ResponseStatus(OK)
    public Integer getNrDislikes(@RequestParam Long id) {
        return photoService.getDislikes(id);
    }

    @GetMapping("/username")
    @ResponseStatus(OK)
    public String getUsername(@RequestParam Long id){
        return photoService.getUsername(id);
    }

    @PutMapping("/likedislike")
    @ResponseStatus(OK)
    public void updateLikeDislike(@RequestBody Long id, @RequestParam String username){
        photoService.updateDislikeAndLike(id, username);
    }

}

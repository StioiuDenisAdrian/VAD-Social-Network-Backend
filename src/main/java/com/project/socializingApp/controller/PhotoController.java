package com.project.socializingApp.controller;

import com.project.socializingApp.dataLayer.RequestPhoto;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.service.PhotoService;
import static org.springframework.http.HttpStatus.*;

import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<PhotoModel> getAll(@RequestParam Long id){
        return photoService.listByUsername(id);
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


}

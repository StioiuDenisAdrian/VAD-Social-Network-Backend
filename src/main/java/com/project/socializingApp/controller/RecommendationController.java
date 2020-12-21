package com.project.socializingApp.controller;

import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.service.UserDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recom")
public class RecommendationController {
    private UserDetService userDetService;
    @Autowired
    public RecommendationController(UserDetService userDetService) {
        this.userDetService = userDetService;
    }
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoModel> recomList(@RequestParam String username){
        return userDetService.recommend(username);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoModel> initRecomList(@RequestBody String username){
        return userDetService.initRecommend(username);
    }
}

package com.project.socializingApp.controller;

import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.model.User;
import com.project.socializingApp.service.UserDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<PhotoModel> recomSet(@RequestParam String username){
        return userDetService.recommend(username);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoModel> initRecomSet(@RequestBody String username){
        return userDetService.initRecommend(username);
    }

    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public Integer getRecomIndex(@RequestParam String username){
        return userDetService.getRecomIndex(username);
    }

    @PostMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public Integer setRecomIndex(@RequestParam String username, @RequestBody Integer value){
        return userDetService.setRecomIndex(username, value);
    }

    @GetMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getFollow(@RequestParam String username){
        return userDetService.getFollow(username).stream().map(User::getUserName).distinct().collect(Collectors.toList());
    }

    @PostMapping("/follow/")
    @ResponseStatus(HttpStatus.OK)
    public List<String> setFollow(@RequestParam String username,@RequestBody String follow){
        return userDetService.setFollow(username, follow).stream().map(User::getUserName).distinct().collect(Collectors.toList());
    }

    @GetMapping("/follow/{username}/{follow}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isFollow(@PathVariable("username") String username,@PathVariable("follow") String follow){
        return userDetService.isFollow(username, follow);
    }
}

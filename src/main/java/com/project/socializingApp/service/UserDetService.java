package com.project.socializingApp.service;

import com.project.socializingApp.model.Friendship;
import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.dataLayer.UserUpdateDetails;
import com.project.socializingApp.model.User;
import com.project.socializingApp.repository.FriendRepo;
import com.project.socializingApp.repository.PhotoRepo;
import com.project.socializingApp.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
@AllArgsConstructor
@Service
public class UserDetService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PhotoRepo photoRepo;
    private final FriendRepo friendRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUserName(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUserName(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

    public void updateUserDetails(User user)
    {
        userRepo.save(user);
    }

    public User getUserDetails(String userName)
    {
       return userRepo.findByUserName(userName).get();
    }

    public List<PhotoModel> recommend(String name){
        return userRepo.findByUserName(name).orElseThrow().getRecommendation();
    }
    @Transactional
    public List<PhotoModel> initRecommend(String name){
        User user = userRepo.findByUserName(name).orElseThrow();
        List<PhotoModel> set = photoRepo.findAll();
        //set.removeAll(photoRepo.findAllByUserUserIdContains(user.getUserId()));
        for (PhotoModel photoModel:set) {
            if(!user.getRecommendation().contains(photoModel)){
                user.getRecommendation().add(photoModel);
            }
        }
        //user.getRecommendation().addAll(set);
        //userRepo.save(user);
        return user.getRecommendation();
    }

    public Integer getRecomIndex(String username){
        return userRepo.findByUserName(username).orElseThrow().getRecomIndex();
    }

    @Transactional
    public Integer setRecomIndex(String username, Integer value){
        User user = userRepo.findByUserName(username).orElseThrow();
        user.setRecomIndex(value);
        return user.getRecomIndex();
    }

    public List<User> getFollow(String username){
        /*return userRepo.findByUserName(username).orElseThrow().getFriends().stream()
                .filter(e->e.getOwner().getUserName().equals(username))
                .map(Friendship::getTarget)
                .collect(Collectors.toList());*/
        return friendRepo.findAll().stream()
                .filter(e -> e.getOwner().getUserName().equals(username))
                .map(Friendship::getTarget)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<User> setFollow(String username, String follow){
        User user = userRepo.findByUserName(username).orElseThrow();
        User followed = userRepo.findByUserName(follow).orElseThrow();
        /*List<User> friends = user.getFriends().stream()
                .filter(e->e.getOwner().getUserName().equals(username))
                .map(Friendship::getTarget)
                .collect(Collectors.toList());*/
        List<User> friends = friendRepo.findAll().stream()
                .filter(e -> e.getOwner().getUserName().equals(username))
                .map(Friendship::getTarget)
                .collect(Collectors.toList());
        Friendship friendship = new Friendship();
        friendship.setOwner(user);
        friendship.setTarget(followed);
        if(friends.contains(followed)){
            friends.remove(followed);
            /*user.getFriends().remove(*/friendRepo.deleteByOwnerAndTarget(user,followed);
        }else{
            friends.add(followed);
            /*user.getFriends().add(*/friendRepo.save(friendship);
        }

        return friends;
    }

    public boolean isFollow(String username, String follow){
        /*User user = userRepo.findByUserName(username).orElseThrow();*/
        User followed = userRepo.findByUserName(follow).orElseThrow();
        /*List<User> friends = user.getFriends().stream()
                .filter(e->e.getOwner().getUserName().equals(username))
                .map(Friendship::getTarget)
                .collect(Collectors.toList());*/
        return friendRepo.findAll().stream()
                .filter(e -> e.getOwner().getUserName().equals(username))
                .anyMatch(e -> e.getTarget().getUserName().equals(follow));
       /* if(friends.contains(followed)){
            return true;
        }
        return false;*/
    }

    public List<String> listAllUsers(){
        return userRepo.findAll().stream()
                .filter(User::isEnabled)
                .map(User::getUserName)
                .collect(Collectors.toList());
    }
}

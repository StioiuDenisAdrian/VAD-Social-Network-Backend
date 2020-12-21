package com.project.socializingApp.service;

import com.project.socializingApp.model.PhotoModel;
import com.project.socializingApp.dataLayer.UserUpdateDetails;
import com.project.socializingApp.model.User;
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
import java.util.Optional;

import static java.util.Collections.singletonList;
@AllArgsConstructor
@Service
public class UserDetService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PhotoRepo photoRepo;

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
        List<PhotoModel> list = photoRepo.findAll();
        //list.removeAll(photoRepo.findAllByUserUserIdContains(user.getUserId()));
        for (PhotoModel photoModel:list) {
            if(!user.getRecommendation().contains(photoModel)){
                user.getRecommendation().add(photoModel);
            }
        }
        //user.getRecommendation().addAll(list);
        //userRepo.save(user);
        return user.getRecommendation();
    }
}

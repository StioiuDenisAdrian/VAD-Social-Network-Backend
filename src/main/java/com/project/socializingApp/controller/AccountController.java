package com.project.socializingApp.controller;

import com.project.socializingApp.dataLayer.UserUpdateDetails;
import com.project.socializingApp.model.User;
import com.project.socializingApp.service.UserDetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final UserDetService userService;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserUpdateDetails userUpdateDetails) {
        try {
            User user = userService.getUserDetails(userUpdateDetails.getUserName());

            if (!userUpdateDetails.getEmail().equals(user.getEmail())) {
                user.setEmail(userUpdateDetails.getEmail());
            }

            if (userUpdateDetails.getNewPassword() != null && !userUpdateDetails.getNewPassword().isEmpty()) {
                BCryptPasswordEncoder encoder = passwordEncoder();

                if (encoder.matches(userUpdateDetails.getOldPassword(), user.getPassword())) {
                    user.setPassword(encoder.encode(userUpdateDetails.getNewPassword()));
                } else {
                    return new ResponseEntity<>("Wrong password!", HttpStatus.BAD_REQUEST);
                }
            }

            userService.updateUserDetails(user);

            return ResponseEntity.ok().build();

        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> delete(@PathVariable String userName) {
        try {
            User user = userService.getUserDetails(userName);
            userService.deleteUserAccount(user);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
      
    @GetMapping("/usernames")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsers(){
        return userService.listAllUsers();

    }
}
package com.example.controllers;

import com.example.users.User;
import com.example.users.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersRepo usersRepo;
    @PostMapping
    public User createUser(@RequestBody User user){
        return usersRepo.save(user);

    }

    @GetMapping
    public List<User> listUsers(){
        return (List<User>) usersRepo.findAll();
    }
}

package com.example.springmongo.controller;

import com.example.springmongo.model.User;
import com.example.springmongo.repositories.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class UserController {
    UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;

    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUser(int id) {
        return userDao.findById(id).get();
    }

    public void addUser(User user) {

        userDao.save(user);
    }

    public void deleteUser(int id) {
        User user = getUser(id);
        userDao.delete(user);
    }

    public void putUser(User user, int id) {

        User real = getUser(id);
        real.setEmail(user.getEmail());
        real.setName(user.getName());



        userDao.save(real);
    }

    public void patchUser(int id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        User user = getUser(id);
        User userPatched = applyPatch(patch, user);


        userDao.save(userPatched);

    }

    private User applyPatch(JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }


}
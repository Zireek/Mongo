package com.example.springmongo.resources;

import com.example.springmongo.controller.UserController;
import com.example.springmongo.model.User;
import com.example.springmongo.service.SequenceGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UserResource.USER_RESOURCE)
public class UserResource {
    public final static String USER_RESOURCE = "/users";
    UserController userController;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserResource(UserController userController) {
        this.userController = userController;
    }

    @GetMapping
    public List<User> users(){
        return userController.getAllUsers();
    }

    @GetMapping("{id}")
    public User user(@PathVariable("id") int id){
        return userController.getUser(id);
    }

    @GetMapping("{id}/email")
    public Map<String,String> email(@PathVariable("id") int id){
        return Collections.singletonMap("email",userController.getUser(id).getEmail());
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        userController.addUser(user);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id){
        userController.deleteUser(id);
    }



    @PutMapping("{id}")
    public void putUser(@RequestBody User user, @PathVariable("id") int id){
        userController.putUser(user, id);
    }

    @PatchMapping("{id}")
    public void patchUser(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        userController.patchUser(id,patch);
    }
}


package pl.iwa.mstokfisz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.iwa.mstokfisz.model.Usr;
import pl.iwa.mstokfisz.model.request.LoginUserRequest;
import pl.iwa.mstokfisz.repository.UserRepository;
import pl.iwa.mstokfisz.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<Usr> listUser(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Usr getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public Usr saveUser(@RequestBody LoginUserRequest loginUserRequest){
        return userService.save(loginUserRequest);
    }
}

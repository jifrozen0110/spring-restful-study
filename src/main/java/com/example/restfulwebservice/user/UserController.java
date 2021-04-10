package com.example.restfulwebservice.user;

import com.example.restfulwebservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service=service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if (user==null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser=service.save(user);

        URI location=ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user=service.deleteById(id);

        if(user==null){
            throw new  UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable int id){
        Optional<User> userOptional= Optional.ofNullable(service.findOne(id));

        if(!userOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        userOptional.get().setName(user.getName());
        service.save(userOptional.get());

        return ResponseEntity.noContent().build();
    }
}

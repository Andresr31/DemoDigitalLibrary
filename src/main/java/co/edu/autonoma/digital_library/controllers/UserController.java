/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.autonoma.digital_library.controllers;

import co.edu.autonoma.digital_library.models.User;
import co.edu.autonoma.digital_library.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author candr
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
     @Autowired
    private UserRepository userRepository;
    
    
    @CrossOrigin
    @GetMapping
    public List<User> getAllUsers(){
       return this.userRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable long id){
        
        Optional<User> user = this.userRepository.findById(id);
        
        return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());  
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        
        if(this.userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        User savedUser = this.userRepository.save(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        
        if(!this.userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }   
        
        this.userRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User updatedUser){
        if(!this.userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        
        updatedUser.setId(id);
        
        User savedUser = this.userRepository.save(updatedUser);
        
        return ResponseEntity.ok(savedUser);
        
    }
    
}

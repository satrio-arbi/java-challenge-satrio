package ist.challenge.satrio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ist.challenge.satrio.entities.User;
import ist.challenge.satrio.repositories.UserRepository;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/register")
    public HttpStatus registerUser(@RequestBody User newUser) {
        List<User> users = userRepository.findAll();

        System.out.println("New user: " + newUser.toString());

        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());

            if (user.equals(newUser)) {
                System.out.println("Username sudah terpakai");
                return HttpStatus.CONFLICT;
            }
        }

        userRepository.save(newUser);
        return HttpStatus.OK;
    }

    @PostMapping("/users/login")
    public HttpStatus loginUser(@RequestBody User user) {
        List<User> users = userRepository.findAll();

        for (User other : users) {
            if (other.equals(user)) {
                userRepository.save(user);
                System.out.println("Sukses Login");
                return HttpStatus.OK;
            }
            if (other.getUsername().isBlank() || other.getPassword().isBlank()) {
                System.out.println("Username dan / atau password kosong");
            	return HttpStatus.BAD_REQUEST;
            }
        }
        
        return HttpStatus.BAD_REQUEST;
    }
    
    @PutMapping(value = "/users/update")
    public HttpStatus update(@RequestBody User updateUser){
    	
    	List<User> users = userRepository.findAll();
    	User u = new User();
        u = userRepository.findById(updateUser.getId()).get();

        System.out.println("Update user: " + updateUser.toString());

        for (User user : users) {
            System.out.println("Updated user: " + updateUser.toString());

            if (user.equals(updateUser)) {
                System.out.println("Username sudah terpakai");
                return HttpStatus.CONFLICT;
            }
        }
        
        if (u.getPassword() == updateUser.getPassword()) {
        	System.out.println("Password tidak boleh sama dengan password sebelumnya");
        	return HttpStatus.BAD_REQUEST;
        }

        userRepository.save(updateUser);
        return HttpStatus.OK;
    }

    @GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}

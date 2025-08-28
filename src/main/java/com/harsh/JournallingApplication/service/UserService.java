package com.harsh.JournallingApplication.service;
import com.harsh.JournallingApplication.entity.User;
import com.harsh.JournallingApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class  UserService {
    @Autowired
    private UserRepository userRepository;

    //implementing BCrypt password encoder
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public void saveEntry(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
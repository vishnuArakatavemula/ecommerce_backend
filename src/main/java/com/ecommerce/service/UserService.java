package com.ecommerce.service;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {

      if(userRepository.existsByEmail(user.getEmail())){
          throw new RuntimeException("Email Already exists");
      }


        return userRepository.save(user);
    }
}

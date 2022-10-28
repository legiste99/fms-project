package za.ac.cput.fms.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.security.domain.User;
import za.ac.cput.fms.security.factory.UserFactory;
import za.ac.cput.fms.security.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signUpUser(User user){
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }



}

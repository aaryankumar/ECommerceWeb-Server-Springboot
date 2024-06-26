package com.ecommerceProject.ecom.services.auth;

import com.ecommerceProject.ecom.dto.SignupRequest;
import com.ecommerceProject.ecom.dto.UserDto;
import com.ecommerceProject.ecom.entity.User;
import com.ecommerceProject.ecom.enums.UserRole;
import com.ecommerceProject.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest){

        User user=new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser=userRepository.save(user);
        UserDto userDto=new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }
    public  boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct //this annotation will call the createAdminAccount() function automatically after the constructor call
    public void createAdminAccount(){
        User adminAccount=userRepository.findByRole(UserRole.ADMIN);
        if (null==adminAccount){
            User user =new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

        }
    }

}

package com.ecommerceProject.ecom.controller;

import com.ecommerceProject.ecom.dto.AuthenticationRequest;
import com.ecommerceProject.ecom.dto.SignupRequest;
import com.ecommerceProject.ecom.dto.UserDto;
import com.ecommerceProject.ecom.entity.User;
import com.ecommerceProject.ecom.repository.UserRepository;
import com.ecommerceProject.ecom.services.auth.AuthService;
import com.ecommerceProject.ecom.utils.JwtUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String TOKEN_PREFIX="Bearer ";
    private static final String HEADER_STRING="Authorization";
    private final AuthService authService;


    @PostMapping("authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }

        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .toString()
            );

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }

    }
    //endpoint for this Api
    @PostMapping("sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if (authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exixts", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto=authService.createUser(signupRequest);
        return  new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}

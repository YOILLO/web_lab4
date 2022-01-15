package yar.web_lab4.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yar.web_lab4.DTO.JwtDTO;
import yar.web_lab4.DTO.UserDTO;
import yar.web_lab4.entities.User;
import yar.web_lab4.security.jwt.JwtUtils;
import yar.web_lab4.security.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    ResponseEntity<?> login(@Validated @RequestBody UserDTO userData){
        try{
            User user = (User) userService.loadUserByUsername(userData.getUsername());
            if (user == null){
                throw new IllegalArgumentException();
            } else if (!passwordEncoder.matches(userData.getPassword(), user.getPassword())){
                throw new IllegalAccessException();
            } else {
                String jwt = jwtUtils.generateJwtToken(userData.getUsername());
                return ResponseEntity.ok(new JwtDTO(userData.getUsername(), jwt));
            }
        } catch (IllegalArgumentException | IllegalAccessException exception){
            return ResponseEntity.badRequest().body("Неверное имя пользователя или пароль.");
        }
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Validated @RequestBody UserDTO userData){
        try{
            if (userService.loadUserByUsername(userData.getUsername()) != null){
                throw new IllegalArgumentException();
            }
            userService.addUser(new User(userData.getUsername(), passwordEncoder.encode(userData.getPassword())));
            return ResponseEntity.ok().body(userData.getUsername());
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body("Имя пользователя '" + userData.getUsername() + "' уже занято");
        }
    }
}

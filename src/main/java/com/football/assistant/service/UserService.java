package com.football.assistant.service;

import com.football.assistant.domain.Role;
import com.football.assistant.domain.User;
import com.football.assistant.repository.RoleRepository;
import com.football.assistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PostCommentService postCommentService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PostCommentService postCommentService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postCommentService = postCommentService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void saveUser(User user, String role) {
        if (user.getPassword() != null) user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        user.addRole(userRole);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        if (email.contains("@")) {
            return userRepository.findByEmail(email);
        }
        return userRepository.findByOauth2id(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(int id) {

        postCommentService.deleteAllCommentsByUserId(id);
        userRepository.deleteById(id);
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

}

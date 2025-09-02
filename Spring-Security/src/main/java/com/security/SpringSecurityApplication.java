package com.security;

import com.security.entities.Role;
import com.security.entities.User;
import com.security.repositories.RoleRepository;
import com.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        Role role1 = roleRepository.findByName("ROLE_ADMIN").orElse(null);
//        Role role2 = roleRepository.findByName("ROLE_GUEST").orElse(null);
//
//        if (role1 == null) {
//            //ROLE_ADMIN
//            role1 = new Role();
//            role1.setRoleId(UUID.randomUUID().toString());
//            role1.setName("ROLE_ADMIN");
//            roleRepository.save(role1);
//        }
//
//        if (role2 == null) {
//            //ROLE_GUEST
//            role2 = new Role();
//            role2.setRoleId(UUID.randomUUID().toString());
//            role2.setName("ROLE_GUEST");
//            roleRepository.save(role2);
//        }
//
//        User user = userRepository.findByUserName("ram").orElse(null);
//
//        if (user == null) {
//            user = new User();
//            user.setUserId(UUID.randomUUID().toString());
//            user.setUserName("RAM");
//            user.setPassword(passwordEncoder.encode("ram123"));
//            user.setRoles(List.of(role1, role2));
//            User savedUser = userRepository.save(user);
//            System.out.println("user is created");
//        }
//
//
//        User user1 = userRepository.findByUserName("shyam").orElse(null);
//
//        if (user1 == null) {
//            user1 = new User();
//            user1.setUserId(UUID.randomUUID().toString());
//            user1.setUserName("shyam");
//            user1.setPassword(passwordEncoder.encode("shyam123"));
//            user1.setRoles(List.of(role2));
//            User savedUser = userRepository.save(user1);
//            System.out.println("user is created");
//        }

    }
}

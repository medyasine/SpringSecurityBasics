package com.example.demo.mapper;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        String[] nameParts = user.getName().split(" ", 2);
        if (nameParts.length > 1) {
            userDTO.setFirstName(nameParts[0]);
            userDTO.setLastName(nameParts[1]);
        } else {
            userDTO.setFirstName(nameParts[0]);
            userDTO.setLastName("");
        }
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRolesIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        List<Role> roles = userDTO.getRolesIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        user.setRoles(roles);
        return user;
    }
}
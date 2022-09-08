package com.example.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.domain.User;
import com.example.api.domain.dtos.UserDTO;

@Service
public interface UserService {

     User findById(Integer id);

     User save(UserDTO user);

     List<User> findAll();

     void delete(Integer id);
        
     User update (UserDTO userDTO);
}

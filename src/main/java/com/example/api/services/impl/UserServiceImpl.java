package com.example.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.User;
import com.example.api.domain.dtos.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.UserService;
import com.example.api.services.exceptions.DataIntegratyVioletionException;
import com.example.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public User save(UserDTO userDto) {
        findByEmail(userDto);

        User user = mapper.map(userDto, User.class);
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDTO dto) {
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if (user.isPresent() && !user.get().getId().equals(dto.getId())) {
            throw new DataIntegratyVioletionException("E-mail já existe no sistema!");
        }
    }

    @Override
    public User update(UserDTO userDTO) {
        return save(userDTO);
    }
}

package com.example.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.api.domain.dtos.UserDTO;
import com.example.api.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                .body(mapper.map(userService.findById(id), UserDTO.class));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userService.save(user).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok()
                .body(userService.findAll().stream().map(user -> mapper.map(user, UserDTO.class))
                        .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @RequestBody UserDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok()
                .body(mapper.map(userService.update(dto), UserDTO.class));
    }
}

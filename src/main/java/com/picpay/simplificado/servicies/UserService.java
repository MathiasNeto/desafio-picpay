package com.picpay.simplificado.servicies;

import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.respositories.UserRepository;
import com.picpay.simplificado.servicies.exceptions.DatabaseException;
import com.picpay.simplificado.servicies.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserDTO createUser(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (userRepository.existsByCpf(user.getCpf()) || userRepository.existsByEmail(user.getEmail()))
            throw new DatabaseException("Dados duplicados");
        user = userRepository.save(user);
        return new UserDTO(user);


    }

    @Transactional
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(user);
    }

    @Transactional
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().
                stream().
                map(UserDTO::new).toList();
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = userRepository.getReferenceById(id);
            BeanUtils.copyProperties(dto, user);
            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User não encontrado no sistema");
        }
    }
}

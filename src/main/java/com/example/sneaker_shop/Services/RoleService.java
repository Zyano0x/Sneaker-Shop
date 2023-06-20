package com.example.sneaker_shop.Services;

import com.example.sneaker_shop.Entities.Role;
import com.example.sneaker_shop.Repositories.IRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> listAll() {
        return roleRepository.findAll();
    }
    public void save(Role user) {
        roleRepository.save(user);
    }
    public Role get(long id) {
        return roleRepository.findById(id).orElse(null);
    }
    public Role getbyName(String name) {
        return roleRepository.getRoleByName(name);
    }
    public void delete(long id) {
        roleRepository.deleteById(id);
    }
}

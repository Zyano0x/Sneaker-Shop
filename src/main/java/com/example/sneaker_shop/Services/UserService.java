package com.example.sneaker_shop.Services;

import com.example.sneaker_shop.Entities.Role;
import com.example.sneaker_shop.Entities.User;
import com.example.sneaker_shop.Repositories.IRoleRepository;
import com.example.sneaker_shop.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    int pageSize = 20;

    @Autowired
    private IUserRepository userRepository;

    public Page<User> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        System.out.println(keyword);
        if (keyword != null) {
            return userRepository.Search(pageable, keyword);
        }
        return userRepository.findWithOutDelete(pageable);

    }
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }

}

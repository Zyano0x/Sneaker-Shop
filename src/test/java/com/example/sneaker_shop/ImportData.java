package com.example.sneaker_shop;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Role;
import com.example.sneaker_shop.Entities.User;
import com.example.sneaker_shop.Repositories.ICategoryRepository;
import com.example.sneaker_shop.Repositories.IRoleRepository;
import com.example.sneaker_shop.Repositories.IShoesRepository;
import com.example.sneaker_shop.Repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@ComponentScan("com.example.sneaker_shop.Utils")
public class ImportData {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void data() {
        Category categoryLS = new Category();
        categoryLS.setName("Lifestyle");
        categoryRepository.save(categoryLS);
        Category categoryR = new Category();
        categoryR.setName("categoryR");
        categoryRepository.save(categoryR);
        Category categoryFB = new Category();
        categoryFB.setName("Football");
        categoryRepository.save(categoryFB);
        Category categoryJD = new Category();
        categoryJD.setName("Jordan");
        categoryRepository.save(categoryJD);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleUser.setDescription("User Role");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleAdmin.setDescription("Administrator Role");
        roleRepository.save(roleAdmin);

        User user = new User();
        user.setUsername("Admin");
        user.setEmail("nthdm00@gmail.com");
        user.isEnabled();
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("ADMIN");
        userRepository.addRoleToUser(userId, roleId);

    }
}

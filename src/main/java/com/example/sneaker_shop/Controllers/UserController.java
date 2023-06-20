package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Entities.User;
import com.example.sneaker_shop.Services.RoleService;
import com.example.sneaker_shop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@ComponentScan("com.example.sneaker_shop")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String viewHomePage(Model model) {
        return showAllUser(model, 1, "id", "asc", " ");
    }

    @GetMapping("/page/{pageNum}")
    public String showAllUser(Model model,
                               @PathVariable(name = "pageNum") int pageNum,
                               @Param("sortField") String sortField,
                               @Param("sortType") String sortType,
                               @Param("keyword") String keyword) {
        sortField = sortField == null?"id":sortField;
        sortType = sortType == null?"asc":sortType;
        Page<User> page = userService.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
        List<User> listUser = page.getContent();

        if (page.getTotalPages() == 0) {
            return "redirect:/users/page/1";
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("users", listUser);
        return "user/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);

        if (user == null) {
            return "error/404";

        } else {
            model.addAttribute("listRoles", roleService.listAll());
            model.addAttribute("user", user);
            return "user/edit";
        }
    }

    @PostMapping("/save")
    public String save(User user) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.getUserById(user.getId()).getPassword());
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        // Check if the "Enabled" checkbox is unchecked
        if (!user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userService.save(user);

        return "redirect:/users";
    }
}
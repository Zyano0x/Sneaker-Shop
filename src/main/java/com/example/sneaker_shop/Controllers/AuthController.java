package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Entities.User;
import com.example.sneaker_shop.Services.MailService;
import com.example.sneaker_shop.Services.RoleService;
import com.example.sneaker_shop.Services.UserService;
import com.example.sneaker_shop.Utils.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
@ComponentScan("com.example.sneaker_shop")
@ComponentScan("com.example.sneaker_shop.Utils")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(User user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.addRoles(roleService.getbyName("USER"));
        user.setVerificationCode(RandomString.make(30));

        userService.save(user);
        mailService.sendVerificationEmail(user, Utilities.getSiteURL(request));

        return "auth/register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code, Model model) {
        if (userService.verify(code)) {
            model.addAttribute("message", "Congratulations, your account has been verified.");
        } else {
            model.addAttribute("error", "Sorry, we could not verify account. It maybe already verified,\n"
                    + "        or verification code is incorrect.");
        }
        return "auth/verify_result";
    }
}

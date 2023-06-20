package com.example.sneaker_shop.Validators;

import com.example.sneaker_shop.Entities.User;
import com.example.sneaker_shop.Validators.annotation.ValidUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context){
        if(user==null){
            return true;
        }
        return user.getId()!=null;
    }
}

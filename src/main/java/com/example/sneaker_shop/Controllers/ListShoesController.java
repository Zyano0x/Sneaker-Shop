package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Services.CategoryService;
import com.example.sneaker_shop.Services.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ListShoes")
public class ListShoesController {
    @Autowired
    private ShoesService shoesService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String ListShoes(Model model){
        List<Shoes> shoes = shoesService.getAllShoes();
        model.addAttribute("shoes", shoes);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "ListShoes/index";
    }
}
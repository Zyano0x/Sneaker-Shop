package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String viewHomePage(Model model) {
        return showAllCategories(model, 1, "id", "asc", " ");
    }

    @GetMapping("/page/{pageNum}")
    public String showAllCategories(Model model,
                                    @PathVariable(name = "pageNum") int pageNum,
                                    @Param("sortField") String sortField,
                                    @Param("sortType") String sortType,
                                    @Param("keyword") String keyword) {
        sortField = sortField == null?"id":sortField;
        sortType = sortType == null?"asc":sortType;
        Page<Category> page = categoryService.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
        List<Category> listCategory = page.getContent();

        if (page.getTotalPages() == 0) {
            return "redirect:/categories/page/1";
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", listCategory);
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category",new Category());
        return "category/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryPage(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/save")
    public String addCategory(@Valid @ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Long id){
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}

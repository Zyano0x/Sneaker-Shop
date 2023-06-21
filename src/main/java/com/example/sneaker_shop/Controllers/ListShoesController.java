package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Services.CategoryService;
import com.example.sneaker_shop.Services.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/ListShoes")
public class ListShoesController {
    @Autowired
    private ShoesService shoesService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String viewHomePage(Model model) {
        return showAllShoes(model, 1, "id", "asc", "", null);
    }

    @GetMapping("/page/{pageNum}")
    public String showAllShoes(Model model,
                               @PathVariable(name = "pageNum") int pageNum,
                               @RequestParam(name = "sortField", required = false) String sortField,
                               @RequestParam(name = "sortType", required = false) String sortType,
                               @RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "category", required = false) Long categoryId) {
        sortField = sortField == null ? "id" : sortField;
        sortType = sortType == null ? "asc" : sortType;
        keyword = keyword == null ? "" : keyword;

        Page<Shoes> page;
        List<Shoes> listShoes;

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            List<Shoes> allShoes = shoesService.getAllShoes(); // Get all shoes from the service
            listShoes = filterShoesByCategory(allShoes, category, keyword);
            page = createPageFromList(listShoes, pageNum);
        } else {
            page = shoesService.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
            listShoes = page.getContent();
        }

        if (page.getTotalPages() == 0) {
            return "redirect:/ListShoes/page/1";
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("shoes", listShoes);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "ListShoes/index";
    }

    private List<Shoes> filterShoesByCategory(List<Shoes> shoes, Category category, String keyword) {
        List<Shoes> filteredShoes = new ArrayList<>();
        for (Shoes shoe : shoes) {
            if (shoe.getCategory().equals(category) && shoe.getName().contains(keyword)) {
                filteredShoes.add(shoe);
            }
        }
        return filteredShoes;
    }

    private Page<Shoes> createPageFromList(List<Shoes> listShoes, int pageNum) {
        int pageSize = 20;
        int totalItems = listShoes.size();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        List<Shoes> pageContent = listShoes.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, PageRequest.of(pageNum - 1, pageSize), totalItems);
    }
 /*   private List<Shoes> filterShoesByPrice(List<Shoes> shoes, double minPrice, double maxPrice) {
        List<Shoes> filteredShoes = new ArrayList<>();
        for (Shoes shoe : shoes) {
            if (shoe.getPrice() >= minPrice && shoe.getPrice() <= maxPrice) {
                filteredShoes.add(shoe);
            }
        }
        return filteredShoes;
    }*/
}

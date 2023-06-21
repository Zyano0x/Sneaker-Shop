package com.example.sneaker_shop.Controllers;

import com.example.sneaker_shop.Daos.Item;
import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Services.CartService;
import com.example.sneaker_shop.Services.ShoesService;
import com.example.sneaker_shop.Services.CategoryService;
import com.example.sneaker_shop.Utils.FileUpload;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/shoes")
public class ShoesController {
    @Autowired
    private ShoesService shoesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewHomePage(Model model) {
        return showAllShoes(model, 1, "id", "asc", " ");
    }

    @GetMapping("/page/{pageNum}")
    public String showAllShoes(Model model,
                               @PathVariable(name = "pageNum") int pageNum,
                               @Param("sortField") String sortField,
                               @Param("sortType") String sortType,
                               @Param("keyword") String keyword) {
        sortField = sortField == null?"id":sortField;
        sortType = sortType == null?"asc":sortType;
        Page<Shoes> page = shoesService.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
        List<Shoes> listShoes = page.getContent();

        if (page.getTotalPages() == 0) {
            return "redirect:/shoes/page/1";
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("shoes", listShoes);
        return "shoes/list";
    }

    @GetMapping("/add")
    public String addShoesForm(Model model){
        model.addAttribute("shoes",new Shoes());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "shoes/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditShoesPage(@PathVariable("id") Long id, Model model) {
        Shoes shoes = shoesService.getShoesById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("shoes", shoes);
        return "shoes/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("shoes") Shoes shoes, BindingResult result, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        // check lỗi
        if(result.hasErrors()){
            model.addAttribute("categories",categoryService.getAllCategories());
            return "shoes/add";
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        if (!multipartFile.isEmpty()) {
            shoes.setPhotos(fileName);
        }else {
            shoes.setPhotos(shoesService.getShoesById(shoes.getId()).getPhotos());
        }

        shoesService.addShoes(shoes);

        if (!multipartFile.getOriginalFilename().isBlank()) {
            String uploadDir = "photos/";
            FileUpload.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/shoes";
    }

    @GetMapping("/delete/{id}")
    public String deleteShoes(@PathVariable("id")Long id){
        Shoes shoes = shoesService.getShoesById(id);
        shoesService.deleteShoes(id);
        return "redirect:/shoes";
    }

    @GetMapping("/detail/{id}")
    public String detailShoes(@PathVariable("id")Long id, Model model, HttpSession session) {
        Shoes shoes = shoesService.getShoesById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("shoes", shoes);

        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "shoes/detail";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam Long shoesId,
                            @RequestParam(defaultValue = "1") int quantity) {
        Shoes shoes = shoesService.getShoesById(shoesId);
        var cart = cartService.getCart(session);
        cart.addItems(new Item(shoes, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/cart";
    }
}

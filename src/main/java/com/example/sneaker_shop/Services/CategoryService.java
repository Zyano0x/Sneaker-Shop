package com.example.sneaker_shop.Services;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    int pageSize = 20;

    @Autowired
    private ICategoryRepository categoryRepository;

    public Page<Category> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        System.out.println(keyword);
        if (keyword != null) {
            return categoryRepository.Search(pageable, keyword);
        }
        return categoryRepository.findWithOutDelete(pageable);

    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}

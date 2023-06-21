package com.example.sneaker_shop.Services;

import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Repositories.IShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoesService {
    int pageSize = 10;

    @Autowired
    private IShoesRepository shoesRepository;

    public  List<Shoes> getAllShoes() {
        return shoesRepository.findAll();
    }

    public Page<Shoes> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        System.out.println(keyword);
        if (keyword != null) {
            return shoesRepository.Search(pageable, keyword);
        }
        return shoesRepository.findWithOutDelete(pageable);

    }

    public Page<Shoes> listAllShoes(int pageNum, String sortField, String sortType, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, 12,
                sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        System.out.println(keyword);
        if (keyword != null) {
            return shoesRepository.Search(pageable, keyword);
        }
        return shoesRepository.findWithOutDelete(pageable);

    }

    public Shoes getShoesById(Long id){
        return  shoesRepository.findById(id).orElse(null);
    }
    public void addShoes(Shoes shoes){
        shoesRepository.save(shoes);
    }
    public void deleteShoes(Long id){
        shoesRepository.deleteById(id);
    }
    public void updateShoes(Shoes shoes){
        shoesRepository.save(shoes);
    }
    public List<Shoes> searchShoes(String keyword) {
        // Thực hiện logic tìm kiếm dựa trên từ khóa và trả về danh sách kết quả
        // Ví dụ:
        List<Shoes> searchResults = new ArrayList<>();
        List<Shoes> allShoes = getAllShoes();
        for (Shoes shoes : allShoes) {
            if (shoes.getName().contains(keyword)) {
                searchResults.add(shoes);
            }
        }
        return searchResults;
    }
}

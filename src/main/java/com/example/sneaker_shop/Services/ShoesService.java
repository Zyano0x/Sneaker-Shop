package com.example.sneaker_shop.Services;

import com.example.sneaker_shop.Entities.Shoes;
import com.example.sneaker_shop.Repositories.IShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoesService {
    int pageSize = 20;

    @Autowired
    private IShoesRepository shoesRepository;

    public Page<Shoes> getAllShoes(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return shoesRepository.findAll(pageable);
    }

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
}

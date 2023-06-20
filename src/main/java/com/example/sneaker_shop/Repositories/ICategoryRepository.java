package com.example.sneaker_shop.Repositories;

import com.example.sneaker_shop.Entities.Category;
import com.example.sneaker_shop.Entities.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT b FROM Category b WHERE CONCAT(b.name, ' ') LIKE %:keyword%")
    public Page<Category> Search(Pageable page, @Param("keyword")String keyword);

    @Query("SELECT b FROM Category b")
    Page<Category> findWithOutDelete(Pageable page);
}

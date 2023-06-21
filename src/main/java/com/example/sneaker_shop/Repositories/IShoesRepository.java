package com.example.sneaker_shop.Repositories;

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
public interface IShoesRepository extends JpaRepository<Shoes, Long> {
    @Query("SELECT b FROM Shoes b WHERE CONCAT(b.name, ' ', b.category.name, ' ', b.price) LIKE %:keyword% AND b.deleted = false")
    public Page<Shoes> Search(Pageable page, @Param("keyword")String keyword);

    @Query("SELECT b FROM Shoes b WHERE b.deleted = false")
    Page<Shoes> findWithOutDelete(Pageable page);
}

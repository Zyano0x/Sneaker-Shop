package com.example.sneaker_shop.Repositories;

import com.example.sneaker_shop.Entities.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemInvoiceRepository extends JpaRepository<ItemInvoice, Long> {
}

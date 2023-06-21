package com.example.sneaker_shop.Repositories;

import com.example.sneaker_shop.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}

package com.example.sneaker_shop.Daos;

import com.example.sneaker_shop.Entities.Shoes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Shoes shoes;
    private int quantity;
}

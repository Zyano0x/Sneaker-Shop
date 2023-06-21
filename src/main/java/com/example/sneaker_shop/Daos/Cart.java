package com.example.sneaker_shop.Daos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();

    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getShoes().getId(), item.getShoes().getId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() +
                            item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }

    public void removeItems(Long shoesId) {
        cartItems.removeIf(item -> Objects.equals(item.getShoes().getId(), shoesId));
    }

    public void updateItems(Long shoesId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item.getShoes().getId(), shoesId))
                .forEach(item -> {
                    item.setQuantity(quantity);
                });
    }
}